package com.dwarfeng.fileio.impl.handler;

import com.dwarfeng.dutil.basic.io.IOUtil;
import com.dwarfeng.fileio.impl.handler.ExportDataBuffer.DataBlock;
import com.dwarfeng.fileio.stack.bean.dto.*;
import com.dwarfeng.fileio.stack.bean.entity.ExportConf;
import com.dwarfeng.fileio.stack.bean.entity.ExportMetadata;
import com.dwarfeng.fileio.stack.bean.entity.ExportTask;
import com.dwarfeng.fileio.stack.bean.entity.ExportTemplateInfo;
import com.dwarfeng.fileio.stack.bean.key.TaskItemKey;
import com.dwarfeng.fileio.stack.handler.*;
import com.dwarfeng.fileio.stack.handler.ExportFileOperateHandler.Operator;
import com.dwarfeng.fileio.stack.service.ExportMetadataMaintainService;
import com.dwarfeng.fileio.stack.service.ExportTaskMaintainService;
import com.dwarfeng.fileio.stack.struct.ReadResult;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.Future;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class ExportHandlerImpl implements ExportHandler {

    private static final TemporaryFileHandler.TemporaryFileType TEMPORARY_FILE_TYPE =
            TemporaryFileHandler.TemporaryFileType.EXPORT_TASK_TEMPORARY_FILE;

    private static final Logger LOGGER = LoggerFactory.getLogger(ExportHandlerImpl.class);

    private final ApplicationContext ctx;

    private final ExportTaskMaintainService exportTaskMaintainService;
    private final ExportMetadataMaintainService exportMetadataMaintainService;

    private final ExportTaskOperateHandler exportTaskOperateHandler;
    private final ExportMetadataOperateHandler exportMetadataOperateHandler;
    private final ExportLocalCacheHandler exportLocalCacheHandler;
    private final TemporaryFileHandler temporaryFileHandler;
    private final ExportFileOperateHandler exportFileOperateHandler;
    private final ExportTemplateOperateHandler exportTemplateOperateHandler;

    private final ThreadPoolTaskExecutor executor;
    private final ThreadPoolTaskScheduler scheduler;

    @Value("${com.dwarfeng.fileio.task.export.beat_interval}")
    private long beatInterval;

    public ExportHandlerImpl(
            ApplicationContext ctx,
            ExportTaskMaintainService exportTaskMaintainService,
            ExportMetadataMaintainService exportMetadataMaintainService,
            ExportTaskOperateHandler exportTaskOperateHandler,
            ExportMetadataOperateHandler exportMetadataOperateHandler,
            ExportLocalCacheHandler exportLocalCacheHandler,
            TemporaryFileHandler temporaryFileHandler,
            ExportFileOperateHandler exportFileOperateHandler,
            ExportTemplateOperateHandler exportTemplateOperateHandler,
            ThreadPoolTaskExecutor executor,
            ThreadPoolTaskScheduler scheduler
    ) {
        this.ctx = ctx;
        this.exportTaskMaintainService = exportTaskMaintainService;
        this.exportMetadataMaintainService = exportMetadataMaintainService;
        this.exportTaskOperateHandler = exportTaskOperateHandler;
        this.exportMetadataOperateHandler = exportMetadataOperateHandler;
        this.exportLocalCacheHandler = exportLocalCacheHandler;
        this.temporaryFileHandler = temporaryFileHandler;
        this.exportFileOperateHandler = exportFileOperateHandler;
        this.exportTemplateOperateHandler = exportTemplateOperateHandler;
        this.executor = executor;
        this.scheduler = scheduler;
    }

    @Override
    public LongIdKey createTask(TaskCreateInfo createInfo) throws HandlerException {
        try {
            return exportTaskOperateHandler.create(createInfo);
        } catch (HandlerException e) {
            throw e;
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    @Override
    public void upsertTaskMetadata(MetadataUpsertInfo upsertInfo) throws HandlerException {
        try {
            exportMetadataOperateHandler.upsert(upsertInfo);
        } catch (HandlerException e) {
            throw e;
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    @Override
    public void executeTask(TaskExecuteInfo executeInfo) throws HandlerException {
        try {
            doExecuteTask(executeInfo);
        } catch (HandlerException e) {
            throw e;
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    @SuppressWarnings("DuplicatedCode")
    private void doExecuteTask(TaskExecuteInfo executeInfo) throws Exception {
        // 预定义参数。
        Future<?> beatTaskFuture = null;
        Set<String> createdTemporaryNames = Collections.synchronizedSet(new HashSet<>());

        // 展开参数。
        LongIdKey taskKey = executeInfo.getTaskKey();
        String message = executeInfo.getMessage();

        try {
            // 调用处理器启动任务。
            exportTaskOperateHandler.start(new TaskStartInfo(taskKey, message));

            // 启动心跳任务。
            beatTaskFuture = startBeatTask(taskKey);

            // 获取任务本身，及其关联的任务设置主键。
            ExportTask task = exportTaskMaintainService.get(taskKey);
            LongIdKey taskSettingKey = task.getSettingKey();

            // 获取任务主键相关的导出元数据。
            Map<String, ExportMetadata> exportMetadataMap = inspectExportMetadata(taskKey);

            // 根据任务设置主键获取导出信息。
            ExportInfo exportInfo = exportLocalCacheHandler.get(taskSettingKey);

            // 构造读取器上下文。
            Reader.Context readerContext = makeReaderContext(
                    exportInfo, task, exportMetadataMap, createdTemporaryNames
            );
            // 构造写入器上下文。
            Exporter.Context exporterContext = makeExporterContext(
                    exportInfo, task, exportMetadataMap, createdTemporaryNames
            );
            // 构造读取器执行器。
            Map<String, Reader.Executor> readerExecutorMap = makeReaderExecutorMap(exportInfo, readerContext);
            // 构造导出器执行器。
            Map<String, Exporter.Executor> exporterExecutorMap = makeExporterExecutorMap(exportInfo, exporterContext);
            // 构造导出数据缓冲。
            Set<String> readerExecutorKeySet = readerExecutorMap.keySet();
            Set<String> exporterExecutorKeySet = exporterExecutorMap.keySet();
            Map<String, ExportDataBuffer> exportDataBufferMap = makeExportDataBufferMap(
                    readerExecutorKeySet, exporterExecutorKeySet
            );

            // 定义 CompletableFuture 集合。
            Set<CompletableFuture<?>> completableFutureSet = new HashSet<>();

            // 为每个读取器执行器创建读取任务，生成 CompletableFuture，添加到集合中。
            for (Map.Entry<String, Reader.Executor> entry : readerExecutorMap.entrySet()) {
                String identifier = entry.getKey();
                Reader.Executor readerExecutor = entry.getValue();
                ReadExecutorTask readExecutorTask = makeReadTask(
                        exportInfo, task, identifier, readerExecutor, exportDataBufferMap.values()
                );
                CompletableFuture<Void> future = CompletableFuture.runAsync(readExecutorTask, executor);
                completableFutureSet.add(future);
            }

            // 为每个导出器执行器创建导出任务，生成 CompletableFuture，添加到集合中。
            for (Map.Entry<String, Exporter.Executor> entry : exporterExecutorMap.entrySet()) {
                String identifier = entry.getKey();
                Exporter.Executor exporterExecutor = entry.getValue();
                ExportExecutorTask exportExecutorTask = makeExportTask(
                        exportInfo, task, identifier, exporterExecutor, exportDataBufferMap.get(identifier)
                );
                CompletableFuture<Void> future = CompletableFuture.runAsync(exportExecutorTask, executor);
                completableFutureSet.add(future);
            }

            // 等待所有任务完成。
            try {
                CompletableFuture.allOf(completableFutureSet.toArray(new CompletableFuture[0])).join();
            } catch (CompletionException e) {
                throw (Exception) e.getCause();
            }

            // 将任务状态置为完成。
            exportTaskOperateHandler.finish(new TaskFinishInfo(taskKey, "由导出处理器设置为完成"));
        } catch (Exception e) {
            // 记录日志。
            String logMessage = "导出任务执行失败, 任务主键: " + taskKey + ", 异常信息如下: ";
            LOGGER.warn(logMessage, e);

            // 将任务状态置为失败。
            exportTaskOperateHandler.fail(new TaskFailInfo(taskKey, "由导出处理器设置为失败"));
        } finally {
            // 清理心跳任务。
            if (Objects.nonNull(beatTaskFuture)) {
                beatTaskFuture.cancel(true);
            }

            // 清除临时文件。
            for (String createdTemporaryName : createdTemporaryNames) {
                try {
                    temporaryFileHandler.delete(TEMPORARY_FILE_TYPE, taskKey, createdTemporaryName);
                } catch (Exception e) {
                    String logMessage = "临时文件删除失败, 文件类型: " + TEMPORARY_FILE_TYPE + ", 任务主键: " + taskKey +
                            ", 文件名: " + createdTemporaryName + ", 异常信息如下: ";
                    LOGGER.warn(logMessage, e);
                }
            }
        }
    }

    @Override
    public CompletableFuture<Void> executeTaskAsync(TaskExecuteInfo executeInfo) {
        return CompletableFuture.supplyAsync(
                () -> {
                    wrappedDoExecuteTask(executeInfo);
                    return null;
                },
                executor
        );
    }

    private void wrappedDoExecuteTask(TaskExecuteInfo executeInfo) throws CompletionException {
        try {
            doExecuteTask(executeInfo);
        } catch (Exception e) {
            throw new CompletionException(e);
        }
    }

    private Future<?> startBeatTask(LongIdKey taskKey) {
        BeatSchedulerTask beatSchedulerTask = ctx.getBean(BeatSchedulerTask.class, exportTaskOperateHandler, taskKey);
        return scheduler.scheduleAtFixedRate(
                beatSchedulerTask,
                new Date(System.currentTimeMillis() + beatInterval),
                beatInterval
        );
    }

    private Map<String, ExportMetadata> inspectExportMetadata(LongIdKey taskKey) throws Exception {
        List<ExportMetadata> exportMetadata = exportMetadataMaintainService.lookupAsList(
                ExportMetadataMaintainService.CHILD_FOR_TASK, new Object[]{taskKey}
        );
        return exportMetadata.stream().collect(Collectors.toMap(
                metadata -> metadata.getKey().getIdentifier(), Function.identity()
        ));
    }

    private Reader.Context makeReaderContext(
            ExportInfo exportInfo, ExportTask exportTask, Map<String, ExportMetadata> exportMetadataMap,
            Set<String> createdTemporaryNames
    ) {
        return ctx.getBean(
                InternalReaderContext.class,
                temporaryFileHandler,
                exportTemplateOperateHandler,
                exportFileOperateHandler,
                exportTask,
                exportInfo.getExportConfMap(),
                exportInfo.getExportTemplateInfoMap(),
                exportMetadataMap,
                createdTemporaryNames
        );
    }

    private Exporter.Context makeExporterContext(
            ExportInfo exportInfo, ExportTask exportTask, Map<String, ExportMetadata> exportMetadataMap,
            Set<String> createdTemporaryNames
    ) {
        return ctx.getBean(
                InternalExporterContext.class,
                temporaryFileHandler,
                exportTemplateOperateHandler,
                exportFileOperateHandler,
                exportTask,
                exportInfo.getExportConfMap(),
                exportInfo.getExportTemplateInfoMap(),
                exportMetadataMap,
                createdTemporaryNames
        );
    }

    private Map<String, Reader.Executor> makeReaderExecutorMap(
            ExportInfo exportInfo, Reader.Context readerContext
    ) throws Exception {
        Map<String, Reader.Executor> result = new HashMap<>();
        for (Map.Entry<String, Reader> entry : exportInfo.getReaderMap().entrySet()) {
            String identifier = entry.getKey();
            Reader reader = entry.getValue();
            Reader.Executor readerExecutor = reader.newExecutor();
            readerExecutor.init(readerContext);
            result.put(identifier, readerExecutor);
        }
        return result;
    }

    private Map<String, Exporter.Executor> makeExporterExecutorMap(
            ExportInfo exportInfo, Exporter.Context exportContext
    ) throws Exception {
        Map<String, Exporter.Executor> result = new HashMap<>();
        for (Map.Entry<String, Exporter> entry : exportInfo.getExporterMap().entrySet()) {
            String identifier = entry.getKey();
            Exporter exporter = entry.getValue();
            Exporter.Executor exporterExecutor = exporter.newExecutor();
            exporterExecutor.init(exportContext);
            result.put(identifier, exporterExecutor);
        }
        return result;
    }

    private Map<String, ExportDataBuffer> makeExportDataBufferMap(
            Set<String> readerExecutorKeySet, Set<String> exporterExecutorKeySet
    ) {
        Map<String, ExportDataBuffer> result = new HashMap<>();
        for (String exporterIdentifier : exporterExecutorKeySet) {
            result.put(exporterIdentifier, new ExportDataBuffer(readerExecutorKeySet));
        }
        return result;
    }

    private ReadExecutorTask makeReadTask(
            ExportInfo exportInfo, ExportTask exportTask, String identifier, Reader.Executor readerExecutor,
            Collection<ExportDataBuffer> buffers
    ) {
        return ctx.getBean(
                ReadExecutorTask.class,
                exportTaskOperateHandler,
                exportInfo.getExportTaskSetting().getKey(),
                exportTask.getKey(),
                identifier,
                readerExecutor,
                buffers
        );
    }

    private ExportExecutorTask makeExportTask(
            ExportInfo exportInfo, ExportTask exportTask, String identifier, Exporter.Executor exporterExecutor,
            ExportDataBuffer buffer
    ) {
        return ctx.getBean(
                ExportExecutorTask.class,
                exportInfo.getExportTaskSetting().getKey(),
                exportTask.getKey(),
                identifier,
                exporterExecutor,
                buffer
        );
    }

    @Override
    public File downloadFile(FileDownloadInfo downloadInfo) throws HandlerException {
        try {
            return exportFileOperateHandler.download(downloadInfo, Operator.SERVICE);
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    @Override
    public FileStream downloadFileStream(FileStreamDownloadInfo downloadInfo) throws HandlerException {
        try {
            return exportFileOperateHandler.downloadStream(downloadInfo, Operator.SERVICE);
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    /**
     * 心跳定时器任务。
     *
     * @author DwArFeng
     * @since 1.0.0
     */
    @Component
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public static class BeatSchedulerTask implements Runnable {

        private static final Logger LOGGER = LoggerFactory.getLogger(BeatSchedulerTask.class);

        private final ExportTaskOperateHandler exportTaskOperateHandler;

        private final LongIdKey taskKey;

        public BeatSchedulerTask(ExportTaskOperateHandler exportTaskOperateHandler, LongIdKey taskKey) {
            this.exportTaskOperateHandler = exportTaskOperateHandler;
            this.taskKey = taskKey;
        }

        @Override
        public void run() {
            try {
                exportTaskOperateHandler.beat(new TaskBeatInfo(taskKey));
            } catch (Exception e) {
                LOGGER.warn("心跳任务执行失败, 任务主键为 {}, 异常信息如下: ", taskKey, e);
            }
        }

        @Override
        public String toString() {
            return "BeatSchedulerTask{" +
                    "exportTaskOperateHandler=" + exportTaskOperateHandler +
                    ", taskKey=" + taskKey +
                    '}';
        }
    }

    /**
     * 读取器上下文的内部实现。
     *
     * @author DwArFeng
     * @since 1.0.0
     */
    @Component
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public static class InternalReaderContext implements Reader.Context {

        private final TemporaryFileHandler temporaryFileHandler;
        private final ExportTemplateOperateHandler exportTemplateOperateHandler;
        private final ExportFileOperateHandler exportFileOperateHandler;

        private final ExportTask exportTask;
        private final Map<String, ExportConf> exportConfMap;
        private final Map<String, ExportTemplateInfo> exportTemplateInfoMap;
        private final Map<String, ExportMetadata> exportMetadataMap;

        private final Set<String> createdTemporaryNames;

        @SuppressWarnings("DuplicatedCode")
        public InternalReaderContext(
                TemporaryFileHandler temporaryFileHandler,
                ExportTemplateOperateHandler exportTemplateOperateHandler,
                ExportFileOperateHandler exportFileOperateHandler,
                ExportTask exportTask,
                Map<String, ExportConf> exportConfMap,
                Map<String, ExportTemplateInfo> exportTemplateInfoMap,
                Map<String, ExportMetadata> exportMetadataMap,
                Set<String> createdTemporaryNames
        ) {
            this.temporaryFileHandler = temporaryFileHandler;
            this.exportTemplateOperateHandler = exportTemplateOperateHandler;
            this.exportFileOperateHandler = exportFileOperateHandler;
            this.exportTask = exportTask;
            this.exportConfMap = exportConfMap;
            this.exportTemplateInfoMap = exportTemplateInfoMap;
            this.exportMetadataMap = exportMetadataMap;
            this.createdTemporaryNames = createdTemporaryNames;
        }

        @Override
        public ExportConf getConf(String identifier) {
            return exportConfMap.get(identifier);
        }

        @Override
        public Template retrieveTemplate(TemplateRetrieveInfo info) throws Exception {
            ExportTemplateInfo exportTemplateInfo = exportTemplateInfoMap.get(info.getIdentifier());
            TemplateDownloadInfo downloadInfo = new TemplateDownloadInfo(exportTemplateInfo.getKey());
            return exportTemplateOperateHandler.download(downloadInfo);
        }

        @Override
        public TemplateStream retrieveTemplateStream(TemplateStreamRetrieveInfo info) throws Exception {
            ExportTemplateInfo exportTemplateInfo = exportTemplateInfoMap.get(info.getIdentifier());
            TemplateStreamDownloadInfo downloadInfo = new TemplateStreamDownloadInfo(exportTemplateInfo.getKey());
            return exportTemplateOperateHandler.downloadStream(downloadInfo);
        }

        @Override
        public ExportMetadata getMetadata(String identifier) {
            return exportMetadataMap.get(identifier);
        }

        @Override
        public File retrieveFile(FileRetrieveInfo info) throws Exception {
            FileDownloadInfo downloadInfo = new FileDownloadInfo(
                    new TaskItemKey(exportTask.getKey().getLongId(), info.getIdentifier())
            );
            return exportFileOperateHandler.download(downloadInfo, Operator.READER_CONTEXT);
        }

        @Override
        public FileStream retrieveFileStream(FileStreamRetrieveInfo info) throws Exception {
            FileStreamDownloadInfo downloadInfo = new FileStreamDownloadInfo(
                    new TaskItemKey(exportTask.getKey().getLongId(), info.getIdentifier())
            );
            return exportFileOperateHandler.downloadStream(downloadInfo, Operator.READER_CONTEXT);
        }

        @Override
        public void storeFile(FileStoreInfo info) throws Exception {
            FileUploadInfo uploadInfo = new FileUploadInfo(
                    new TaskItemKey(exportTask.getKey().getLongId(), info.getIdentifier()),
                    info.getOriginName(), info.getContent()
            );
            exportFileOperateHandler.upload(uploadInfo, Operator.READER_CONTEXT);
        }

        @Override
        public void storeFileStream(FileStreamStoreInfo info) throws Exception {
            FileStreamUploadInfo uploadInfo = new FileStreamUploadInfo(
                    new TaskItemKey(exportTask.getKey().getLongId(), info.getIdentifier()),
                    info.getOriginName(), info.getLength(), info.getContent()
            );
            exportFileOperateHandler.uploadStream(uploadInfo, Operator.READER_CONTEXT);
        }

        @Override
        public void removeFile(String identifier) throws Exception {
            FileRemoveInfo removeInfo = new FileRemoveInfo(
                    new TaskItemKey(exportTask.getKey().getLongId(), identifier)
            );
            exportFileOperateHandler.remove(removeInfo, Operator.READER_CONTEXT);
        }

        @Override
        public void createTemporary(String name) throws Exception {
            LongIdKey taskKey = exportTask.getKey();
            temporaryFileHandler.create(TEMPORARY_FILE_TYPE, taskKey, name);
            createdTemporaryNames.add(name);
        }

        @Override
        public InputStream openTemporaryInputStream(String name) throws Exception {
            LongIdKey taskKey = exportTask.getKey();
            return temporaryFileHandler.openInputStream(TEMPORARY_FILE_TYPE, taskKey, name);
        }

        @Override
        public OutputStream openTemporaryOutputStream(String name) throws Exception {
            LongIdKey taskKey = exportTask.getKey();
            return temporaryFileHandler.openOutputStream(TEMPORARY_FILE_TYPE, taskKey, name);
        }

        @SuppressWarnings("DuplicatedCode")
        @Override
        public void copyTemplateToTemporary(String templateIdentifier, String temporaryName) throws Exception {
            ExportTemplateInfo exportTemplateInfo = exportTemplateInfoMap.get(templateIdentifier);
            TemplateStreamDownloadInfo downloadInfo = new TemplateStreamDownloadInfo(exportTemplateInfo.getKey());
            TemplateStream templateStream = exportTemplateOperateHandler.downloadStream(downloadInfo);
            try (
                    InputStream inputStream = templateStream.getContent();
                    OutputStream outputStream = openTemporaryOutputStream(temporaryName)
            ) {
                IOUtil.trans(inputStream, outputStream, 4096);
            }
        }

        @Override
        public void copyFileToTemporary(String fileIdentifier, String temporaryName) throws Exception {
            FileStreamDownloadInfo downloadInfo = new FileStreamDownloadInfo(
                    new TaskItemKey(exportTask.getKey().getLongId(), fileIdentifier)
            );
            FileStream fileStream = exportFileOperateHandler.downloadStream(downloadInfo, Operator.READER_CONTEXT);
            try (
                    InputStream inputStream = fileStream.getContent();
                    OutputStream outputStream = openTemporaryOutputStream(temporaryName)
            ) {
                IOUtil.trans(inputStream, outputStream, 4096);
            }
        }

        @SuppressWarnings("DuplicatedCode")
        @Override
        public void copyTemporaryToFile(String temporaryName, String fileIdentifier) throws Exception {
            LongIdKey taskKey = exportTask.getKey();
            long length = temporaryFileHandler.length(TEMPORARY_FILE_TYPE, taskKey, temporaryName);
            try (
                    InputStream inputStream = openTemporaryInputStream(temporaryName)
            ) {
                FileStreamUploadInfo uploadInfo = new FileStreamUploadInfo(
                        new TaskItemKey(taskKey.getLongId(), fileIdentifier),
                        temporaryName, length, inputStream
                );
                exportFileOperateHandler.uploadStream(uploadInfo, Operator.READER_CONTEXT);
            }
        }

        @Override
        public void copyTemporaryToTemporary(String fromTemporaryName, String toTemporaryName) throws Exception {
            try (
                    InputStream inputStream = openTemporaryInputStream(fromTemporaryName);
                    OutputStream outputStream = openTemporaryOutputStream(toTemporaryName)
            ) {
                IOUtil.trans(inputStream, outputStream, 4096);
            }
        }

        @SuppressWarnings("DuplicatedCode")
        @Override
        public void copyTemporaryToFile(TemporaryToFileCopyInfo info) throws Exception {
            // 展开参数。
            String temporaryName = info.getTemporaryName();
            String fileIdentifier = info.getFileIdentifier();
            String fileOriginName = info.getFileOriginName();
            // 执行拷贝。
            LongIdKey taskKey = exportTask.getKey();
            long length = temporaryFileHandler.length(TEMPORARY_FILE_TYPE, taskKey, temporaryName);
            try (
                    InputStream inputStream = openTemporaryInputStream(temporaryName)
            ) {
                FileStreamUploadInfo uploadInfo = new FileStreamUploadInfo(
                        new TaskItemKey(taskKey.getLongId(), fileIdentifier),
                        fileOriginName, length, inputStream
                );
                exportFileOperateHandler.uploadStream(uploadInfo, Operator.READER_CONTEXT);
            }
        }

        @Override
        public String toString() {
            return "InternalReaderContext{" +
                    "temporaryFileHandler=" + temporaryFileHandler +
                    ", exportTemplateOperateHandler=" + exportTemplateOperateHandler +
                    ", exportFileOperateHandler=" + exportFileOperateHandler +
                    ", exportTask=" + exportTask +
                    ", exportConfMap=" + exportConfMap +
                    ", exportTemplateInfoMap=" + exportTemplateInfoMap +
                    ", exportMetadataMap=" + exportMetadataMap +
                    ", createdTemporaryNames=" + createdTemporaryNames +
                    '}';
        }
    }

    /**
     * 导出器上下文的内部实现。
     *
     * @author DwArFeng
     * @since 1.0.0
     */
    @Component
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public static class InternalExporterContext implements Exporter.Context {

        private final TemporaryFileHandler temporaryFileHandler;
        private final ExportTemplateOperateHandler exportTemplateOperateHandler;
        private final ExportFileOperateHandler exportFileOperateHandler;

        private final ExportTask exportTask;
        private final Map<String, ExportConf> exportConfMap;
        private final Map<String, ExportTemplateInfo> exportTemplateInfoMap;
        private final Map<String, ExportMetadata> exportMetadataMap;

        private final Set<String> createdTemporaryNames;

        @SuppressWarnings("DuplicatedCode")
        public InternalExporterContext(
                TemporaryFileHandler temporaryFileHandler,
                ExportTemplateOperateHandler exportTemplateOperateHandler,
                ExportFileOperateHandler exportFileOperateHandler,
                ExportTask exportTask,
                Map<String, ExportConf> exportConfMap,
                Map<String, ExportTemplateInfo> exportTemplateInfoMap,
                Map<String, ExportMetadata> exportMetadataMap,
                Set<String> createdTemporaryNames
        ) {
            this.temporaryFileHandler = temporaryFileHandler;
            this.exportTemplateOperateHandler = exportTemplateOperateHandler;
            this.exportFileOperateHandler = exportFileOperateHandler;
            this.exportTask = exportTask;
            this.exportConfMap = exportConfMap;
            this.exportTemplateInfoMap = exportTemplateInfoMap;
            this.exportMetadataMap = exportMetadataMap;
            this.createdTemporaryNames = createdTemporaryNames;
        }

        @Override
        public ExportConf getConf(String identifier) {
            return exportConfMap.get(identifier);
        }

        @Override
        public Template retrieveTemplate(TemplateRetrieveInfo info) throws Exception {
            ExportTemplateInfo exportTemplateInfo = exportTemplateInfoMap.get(info.getIdentifier());
            TemplateDownloadInfo downloadInfo = new TemplateDownloadInfo(exportTemplateInfo.getKey());
            return exportTemplateOperateHandler.download(downloadInfo);
        }

        @Override
        public TemplateStream retrieveTemplateStream(TemplateStreamRetrieveInfo info) throws Exception {
            ExportTemplateInfo exportTemplateInfo = exportTemplateInfoMap.get(info.getIdentifier());
            TemplateStreamDownloadInfo downloadInfo = new TemplateStreamDownloadInfo(exportTemplateInfo.getKey());
            return exportTemplateOperateHandler.downloadStream(downloadInfo);
        }

        @Override
        public ExportMetadata getMetadata(String identifier) {
            return exportMetadataMap.get(identifier);
        }

        @Override
        public File retrieveFile(FileRetrieveInfo info) throws Exception {
            FileDownloadInfo downloadInfo = new FileDownloadInfo(
                    new TaskItemKey(exportTask.getKey().getLongId(), info.getIdentifier())
            );
            return exportFileOperateHandler.download(downloadInfo, Operator.EXPORTER_CONTEXT);
        }

        @Override
        public FileStream retrieveFileStream(FileStreamRetrieveInfo info) throws Exception {
            FileStreamDownloadInfo downloadInfo = new FileStreamDownloadInfo(
                    new TaskItemKey(exportTask.getKey().getLongId(), info.getIdentifier())
            );
            return exportFileOperateHandler.downloadStream(downloadInfo, Operator.EXPORTER_CONTEXT);
        }

        @Override
        public void storeFile(FileStoreInfo info) throws Exception {
            FileUploadInfo uploadInfo = new FileUploadInfo(
                    new TaskItemKey(exportTask.getKey().getLongId(), info.getIdentifier()),
                    info.getOriginName(), info.getContent()
            );
            exportFileOperateHandler.upload(uploadInfo, Operator.EXPORTER_CONTEXT);
        }

        @Override
        public void storeFileStream(FileStreamStoreInfo info) throws Exception {
            FileStreamUploadInfo uploadInfo = new FileStreamUploadInfo(
                    new TaskItemKey(exportTask.getKey().getLongId(), info.getIdentifier()),
                    info.getOriginName(), info.getLength(), info.getContent()
            );
            exportFileOperateHandler.uploadStream(uploadInfo, Operator.EXPORTER_CONTEXT);
        }

        @Override
        public void removeFile(String identifier) throws Exception {
            FileRemoveInfo removeInfo = new FileRemoveInfo(
                    new TaskItemKey(exportTask.getKey().getLongId(), identifier)
            );
            exportFileOperateHandler.remove(removeInfo, Operator.EXPORTER_CONTEXT);
        }

        @Override
        public void createTemporary(String name) throws Exception {
            LongIdKey taskKey = exportTask.getKey();
            temporaryFileHandler.create(TEMPORARY_FILE_TYPE, taskKey, name);
            createdTemporaryNames.add(name);
        }

        @Override
        public InputStream openTemporaryInputStream(String name) throws Exception {
            LongIdKey taskKey = exportTask.getKey();
            return temporaryFileHandler.openInputStream(TEMPORARY_FILE_TYPE, taskKey, name);
        }

        @Override
        public OutputStream openTemporaryOutputStream(String name) throws Exception {
            LongIdKey taskKey = exportTask.getKey();
            return temporaryFileHandler.openOutputStream(TEMPORARY_FILE_TYPE, taskKey, name);
        }

        @SuppressWarnings("DuplicatedCode")
        @Override
        public void copyTemplateToTemporary(String templateIdentifier, String temporaryName) throws Exception {
            ExportTemplateInfo exportTemplateInfo = exportTemplateInfoMap.get(templateIdentifier);
            TemplateStreamDownloadInfo downloadInfo = new TemplateStreamDownloadInfo(exportTemplateInfo.getKey());
            TemplateStream templateStream = exportTemplateOperateHandler.downloadStream(downloadInfo);
            try (
                    InputStream inputStream = templateStream.getContent();
                    OutputStream outputStream = openTemporaryOutputStream(temporaryName)
            ) {
                IOUtil.trans(inputStream, outputStream, 4096);
            }
        }

        @Override
        public void copyFileToTemporary(String fileIdentifier, String temporaryName) throws Exception {
            FileStreamDownloadInfo downloadInfo = new FileStreamDownloadInfo(
                    new TaskItemKey(exportTask.getKey().getLongId(), fileIdentifier)
            );
            FileStream fileStream = exportFileOperateHandler.downloadStream(downloadInfo, Operator.EXPORTER_CONTEXT);
            try (
                    InputStream inputStream = fileStream.getContent();
                    OutputStream outputStream = openTemporaryOutputStream(temporaryName)
            ) {
                IOUtil.trans(inputStream, outputStream, 4096);
            }
        }

        @Override
        public void copyTemporaryToFile(String temporaryName, String fileIdentifier) throws Exception {
            LongIdKey taskKey = exportTask.getKey();
            long length = temporaryFileHandler.length(TEMPORARY_FILE_TYPE, taskKey, temporaryName);
            try (
                    InputStream inputStream = openTemporaryInputStream(temporaryName)
            ) {
                FileStreamUploadInfo uploadInfo = new FileStreamUploadInfo(
                        new TaskItemKey(taskKey.getLongId(), fileIdentifier),
                        temporaryName, length, inputStream
                );
                exportFileOperateHandler.uploadStream(uploadInfo, Operator.EXPORTER_CONTEXT);
            }
        }

        @Override
        public void copyTemporaryToTemporary(String fromTemporaryName, String toTemporaryName) throws Exception {
            try (
                    InputStream inputStream = openTemporaryInputStream(fromTemporaryName);
                    OutputStream outputStream = openTemporaryOutputStream(toTemporaryName)
            ) {
                IOUtil.trans(inputStream, outputStream, 4096);
            }
        }

        @SuppressWarnings("DuplicatedCode")
        @Override
        public void copyTemporaryToFile(TemporaryToFileCopyInfo info) throws Exception {
            // 展开参数。
            String temporaryName = info.getTemporaryName();
            String fileIdentifier = info.getFileIdentifier();
            String fileOriginName = info.getFileOriginName();
            // 执行拷贝。
            LongIdKey taskKey = exportTask.getKey();
            long length = temporaryFileHandler.length(TEMPORARY_FILE_TYPE, taskKey, temporaryName);
            try (
                    InputStream inputStream = openTemporaryInputStream(temporaryName)
            ) {
                FileStreamUploadInfo uploadInfo = new FileStreamUploadInfo(
                        new TaskItemKey(taskKey.getLongId(), fileIdentifier),
                        fileOriginName, length, inputStream
                );
                exportFileOperateHandler.uploadStream(uploadInfo, Operator.READER_CONTEXT);
            }
        }

        @Override
        public String toString() {
            return "InternalExporterContext{" +
                    "temporaryFileHandler=" + temporaryFileHandler +
                    ", exportTemplateOperateHandler=" + exportTemplateOperateHandler +
                    ", exportFileOperateHandler=" + exportFileOperateHandler +
                    ", exportTask=" + exportTask +
                    ", exportConfMap=" + exportConfMap +
                    ", exportTemplateInfoMap=" + exportTemplateInfoMap +
                    ", exportMetadataMap=" + exportMetadataMap +
                    ", createdTemporaryNames=" + createdTemporaryNames +
                    '}';
        }
    }

    /**
     * 读取执行器任务。
     *
     * @author DwArFeng
     * @since 1.0.0
     */
    @Component
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public static class ReadExecutorTask implements Runnable {

        private static final Logger LOGGER = LoggerFactory.getLogger(ReadExecutorTask.class);

        private static final int EXECUTE_STATUS_EXECUTED = 1;
        private static final int EXECUTE_STATUS_EXCEPTION = 2;

        private final ExportTaskOperateHandler exportTaskOperateHandler;

        private final LongIdKey taskSettingKey;
        private final LongIdKey taskKey;

        private final String identifier;

        private final Reader.Executor readerExecutor;
        private final Collection<ExportDataBuffer> buffers;

        public ReadExecutorTask(
                ExportTaskOperateHandler exportTaskOperateHandler,
                LongIdKey taskSettingKey,
                LongIdKey taskKey,
                String identifier,
                Reader.Executor readerExecutor,
                Collection<ExportDataBuffer> buffers
        ) {
            this.exportTaskOperateHandler = exportTaskOperateHandler;
            this.taskSettingKey = taskSettingKey;
            this.taskKey = taskKey;
            this.identifier = identifier;
            this.readerExecutor = readerExecutor;
            this.buffers = buffers;
        }

        @Override
        public void run() {
            // 获取主题空间。
            Set<String> topicSpace;
            try {
                // 执行方法。
                topicSpace = readerExecutor.topicSpace();
            } catch (Exception e) {
                // 将所有导出数据缓冲标识符对应的输入标记为结束。
                buffers.forEach(buffer -> buffer.markEndOfInput(identifier));
                // 记录日志。
                String message = "获取读取器执行器主题空间时发生异常, 任务设置键: " + taskSettingKey + ", 任务键: " +
                        taskKey + ", 读取器标识: " + identifier + ", 异常信息如下: ";
                LOGGER.warn(message, e);
                // 抛出异常。
                throw new CompletionException(e);
            }

            // 定义异常。
            Exception occurredException = null;

            // 定义执行标识。
            int beforeExecuteExecuteStatus;

            // 执行执行前调度。
            try {
                // 执行方法。
                readerExecutor.beforeExecute();
                // 置位状态标识。
                beforeExecuteExecuteStatus = EXECUTE_STATUS_EXECUTED;
            } catch (Exception e) {
                // 将所有导出数据缓冲标识符对应的输入标记为结束。
                buffers.forEach(buffer -> buffer.markEndOfInput(identifier));
                // 记录日志。
                String message = "读取器执行器执行前调度时发生异常, 任务设置键: " + taskSettingKey + ", 任务键: " +
                        taskKey + ", 读取器标识: " + identifier + ", 异常信息如下: ";
                LOGGER.warn(message, e);
                // 置位异常。
                occurredException = e;
                // 置位状态标识。
                beforeExecuteExecuteStatus = EXECUTE_STATUS_EXCEPTION;
            }

            // 如果执行前调度方法被执行，则执行执行调度。
            if (Objects.equals(beforeExecuteExecuteStatus, EXECUTE_STATUS_EXECUTED)) {
                try {
                    // 对主题空间的每个主题进行执行调度。
                    topicSpace = Optional.ofNullable(topicSpace).orElse(Collections.emptySet());
                    for (String topic : topicSpace) {
                        executeSingleTopic(topic);
                    }
                } catch (Exception e) {
                    // 将所有导出数据缓冲标识符对应的输入标记为结束。
                    buffers.forEach(buffer -> buffer.markEndOfInput(identifier));
                    // 记录日志。
                    String message = "读取器执行器执行调度时发生异常, 任务设置键: " + taskSettingKey + ", 任务键: " +
                            taskKey + ", 读取器标识: " + identifier + ", 异常信息如下: ";
                    LOGGER.warn(message, e);
                    // 置位异常。
                    if (Objects.isNull(occurredException)) {
                        occurredException = e;
                    }
                }
            }

            // 只要执行前调度被尝试执行（无论成功还是失败），都执行执行后执行调度。
            // 如果执行器在执行前调度中只完成了部分初始化，执行后执行调度也有机会清理已初始化的资源。
            try {
                readerExecutor.afterExecute();
            } catch (Exception e) {
                // 将所有导出数据缓冲标识符对应的输入标记为结束。
                buffers.forEach(buffer -> buffer.markEndOfInput(identifier));
                String message = "读取器执行器执行后执行调度时发生异常, 任务设置键: " + taskSettingKey + ", 任务键: " +
                        taskKey + ", 读取器标识: " + identifier + ", 异常信息如下: ";
                // 记录日志。
                LOGGER.warn(message, e);
                // 置位异常。
                if (Objects.isNull(occurredException)) {
                    occurredException = e;
                }
            }

            // 如果发生异常，则抛出。
            if (Objects.nonNull(occurredException)) {
                throw new CompletionException(occurredException);
            }
        }

        private void executeSingleTopic(String topic) throws Exception {
            // 循环读取，直到读取完毕。
            boolean hasNext;
            do {
                // 获取读取结果。
                ReadResult readResult = readerExecutor.execute(topic);
                // 展开参数。
                hasNext = readResult.isHasNext();
                List<Map<String, Object>> datas = readResult.getDatas();
                // 更新任务数据量。
                exportTaskOperateHandler.increaseDataCount(new TaskDataCountIncreaseInfo(taskKey, datas.size()));
                // 封装数据块。
                DataBlock dataBlock = new DataBlock(identifier, topic, readResult);
                // 将数据块放入缓冲。
                buffers.forEach(buffer -> buffer.put(dataBlock));
            } while (hasNext);
        }

        @Override
        public String toString() {
            return "ReadExecutorTask{" +
                    "taskSettingKey=" + taskSettingKey +
                    ", taskKey=" + taskKey +
                    ", identifier='" + identifier + '\'' +
                    ", readerExecutor=" + readerExecutor +
                    ", buffers=" + buffers +
                    '}';
        }
    }

    /**
     * 导出执行器任务。
     *
     * @author DwArFeng
     * @since 1.0.0
     */
    @Component
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public static class ExportExecutorTask implements Runnable {

        private static final Logger LOGGER = LoggerFactory.getLogger(ExportExecutorTask.class);

        private static final int EXECUTE_STATUS_EXECUTED = 1;
        private static final int EXECUTE_STATUS_EXCEPTION = 2;

        private final LongIdKey taskSettingKey;
        private final LongIdKey taskKey;

        private final String identifier;

        private final Exporter.Executor exporterExecutor;
        private final ExportDataBuffer buffer;

        public ExportExecutorTask(
                LongIdKey taskSettingKey,
                LongIdKey taskKey,
                String identifier,
                Exporter.Executor exporterExecutor,
                ExportDataBuffer buffer
        ) {
            this.taskSettingKey = taskSettingKey;
            this.taskKey = taskKey;
            this.identifier = identifier;
            this.exporterExecutor = exporterExecutor;
            this.buffer = buffer;
        }

        @Override
        public void run() {
            // 定义异常。
            Exception occurredException = null;

            // 定义执行标识。
            int beforeExecuteExecuteStatus;

            // 执行执行前调度。
            try {
                // 执行方法。
                exporterExecutor.beforeExecute();
                // 置位状态标识。
                beforeExecuteExecuteStatus = EXECUTE_STATUS_EXECUTED;
            } catch (Exception e) {
                // 将导出数据缓冲的输出标记为结束。
                buffer.markEndOfOutput();
                // 记录日志。
                String message = "导出器执行器执行前调度时发生异常, 任务设置键: " + taskSettingKey + ", 任务键: " +
                        taskKey + ", 导出器标识: " + identifier + ", 异常信息如下: ";
                LOGGER.warn(message, e);
                // 置位异常。
                occurredException = e;
                // 置位状态标识。
                beforeExecuteExecuteStatus = EXECUTE_STATUS_EXCEPTION;
            }

            // 如果执行前调度方法被执行，则执行执行调度。
            if (Objects.equals(beforeExecuteExecuteStatus, EXECUTE_STATUS_EXECUTED)) {
                try {
                    // 循环导出，直到导出完毕。
                    while (!buffer.isEndOfOutput()) {
                        ExportDataBuffer.DataBlock dataBlock = buffer.take();
                        if (Objects.isNull(dataBlock)) {
                            continue;
                        }
                        String readerIdentifier = dataBlock.getIdentifier();
                        String readerTopic = dataBlock.getTopic();
                        ReadResult readResult = dataBlock.getReadResult();
                        exporterExecutor.execute(readerIdentifier, readerTopic, readResult);
                    }
                } catch (Exception e) {
                    // 将导出数据缓冲的输出标记为结束。
                    buffer.markEndOfOutput();
                    // 记录日志。
                    String message = "导出器执行器执行调度时发生异常, 任务设置键: " + taskSettingKey + ", 任务键: " +
                            taskKey + ", 导出器标识: " + identifier + ", 异常信息如下: ";
                    LOGGER.warn(message, e);
                    // 置位异常。
                    if (Objects.isNull(occurredException)) {
                        occurredException = e;
                    }
                }
            }

            // 只要执行前调度被尝试执行（无论成功还是失败），都执行执行后执行调度。
            // 如果执行器在执行前调度中只完成了部分初始化，执行后执行调度也有机会清理已初始化的资源。
            try {
                exporterExecutor.afterExecute();
            } catch (Exception e) {
                // 将导出数据缓冲的输出标记为结束。
                buffer.markEndOfOutput();
                // 记录日志。
                String message = "导出器执行器执行后执行调度时发生异常, 任务设置键: " + taskSettingKey + ", 任务键: " +
                        taskKey + ", 导出器标识: " + identifier + ", 异常信息如下: ";
                LOGGER.warn(message, e);
                // 置位异常。
                if (Objects.isNull(occurredException)) {
                    occurredException = e;
                }
            }

            // 如果发生异常，则抛出。
            if (Objects.nonNull(occurredException)) {
                throw new CompletionException(occurredException);
            }
        }

        @Override
        public String toString() {
            return "ExportExecutorTask{" +
                    "taskSettingKey=" + taskSettingKey +
                    ", taskKey=" + taskKey +
                    ", identifier='" + identifier + '\'' +
                    ", exporterExecutor=" + exporterExecutor +
                    ", buffers=" + buffer +
                    '}';
        }
    }
}
