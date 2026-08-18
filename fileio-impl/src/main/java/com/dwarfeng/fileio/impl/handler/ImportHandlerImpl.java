package com.dwarfeng.fileio.impl.handler;

import com.dwarfeng.dutil.basic.io.IOUtil;
import com.dwarfeng.fileio.stack.bean.dto.*;
import com.dwarfeng.fileio.stack.bean.entity.ImportConf;
import com.dwarfeng.fileio.stack.bean.entity.ImportMetadata;
import com.dwarfeng.fileio.stack.bean.entity.ImportTask;
import com.dwarfeng.fileio.stack.bean.entity.ImportTemplateInfo;
import com.dwarfeng.fileio.stack.bean.key.TaskItemKey;
import com.dwarfeng.fileio.stack.handler.*;
import com.dwarfeng.fileio.stack.handler.ImportFileOperateHandler.Operator;
import com.dwarfeng.fileio.stack.service.ImportMetadataMaintainService;
import com.dwarfeng.fileio.stack.service.ImportTaskMaintainService;
import com.dwarfeng.fileio.stack.struct.ImportResult;
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
public class ImportHandlerImpl implements ImportHandler {

    private static final TemporaryFileHandler.TemporaryFileType TEMPORARY_FILE_TYPE =
            TemporaryFileHandler.TemporaryFileType.IMPORT_TASK_TEMPORARY_FILE;

    private static final Logger LOGGER = LoggerFactory.getLogger(ImportHandlerImpl.class);

    private final ApplicationContext ctx;

    private final ImportTaskMaintainService importTaskMaintainService;
    private final ImportMetadataMaintainService importMetadataMaintainService;

    private final ImportTaskOperateHandler importTaskOperateHandler;
    private final ImportMetadataOperateHandler importMetadataOperateHandler;
    private final ImportLocalCacheHandler importLocalCacheHandler;
    private final TemporaryFileHandler temporaryFileHandler;
    private final ImportFileOperateHandler importFileOperateHandler;
    private final ImportTemplateOperateHandler importTemplateOperateHandler;

    private final ThreadPoolTaskExecutor executor;
    private final ThreadPoolTaskScheduler scheduler;

    @Value("${com.dwarfeng.fileio.task.import.beat_interval}")
    private long beatInterval;

    public ImportHandlerImpl(
            ApplicationContext ctx,
            ImportTaskMaintainService importTaskMaintainService,
            ImportMetadataMaintainService importMetadataMaintainService,
            ImportTaskOperateHandler importTaskOperateHandler,
            ImportMetadataOperateHandler importMetadataOperateHandler,
            ImportLocalCacheHandler importLocalCacheHandler,
            TemporaryFileHandler temporaryFileHandler,
            ImportFileOperateHandler importFileOperateHandler,
            ImportTemplateOperateHandler importTemplateOperateHandler,
            ThreadPoolTaskExecutor executor,
            ThreadPoolTaskScheduler scheduler
    ) {
        this.ctx = ctx;
        this.importTaskMaintainService = importTaskMaintainService;
        this.importMetadataMaintainService = importMetadataMaintainService;
        this.importTaskOperateHandler = importTaskOperateHandler;
        this.importMetadataOperateHandler = importMetadataOperateHandler;
        this.importLocalCacheHandler = importLocalCacheHandler;
        this.temporaryFileHandler = temporaryFileHandler;
        this.importFileOperateHandler = importFileOperateHandler;
        this.importTemplateOperateHandler = importTemplateOperateHandler;
        this.executor = executor;
        this.scheduler = scheduler;
    }

    @Override
    public LongIdKey createTask(TaskCreateInfo createInfo) throws HandlerException {
        try {
            return importTaskOperateHandler.create(createInfo);
        } catch (HandlerException e) {
            throw e;
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    @Override
    public void upsertTaskMetadata(MetadataUpsertInfo upsertInfo) throws HandlerException {
        try {
            importMetadataOperateHandler.upsert(upsertInfo);
        } catch (HandlerException e) {
            throw e;
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    @Override
    public void uploadFile(FileUploadInfo uploadInfo) throws HandlerException {
        try {
            importFileOperateHandler.upload(uploadInfo, Operator.SERVICE);
        } catch (HandlerException e) {
            throw e;
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    @Override
    public void uploadFileStream(FileStreamUploadInfo uploadInfo) throws HandlerException {
        try {
            importFileOperateHandler.uploadStream(uploadInfo, Operator.SERVICE);
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
            importTaskOperateHandler.start(new TaskStartInfo(taskKey, message));

            // 启动心跳任务。
            beatTaskFuture = startBeatTask(taskKey);

            // 获取任务本身，及其关联的任务设置主键。
            ImportTask task = importTaskMaintainService.get(taskKey);
            LongIdKey taskSettingKey = task.getSettingKey();

            // 获取任务主键相关的导入元数据。
            Map<String, ImportMetadata> importMetadataMap = inspectImportMetadata(taskKey);

            // 根据任务设置主键获取导入信息。
            ImportInfo importInfo = importLocalCacheHandler.get(taskSettingKey);

            // 构造导入器上下文。
            Importer.Context importerContext = makeImporterContext(
                    importInfo, task, importMetadataMap, createdTemporaryNames
            );
            // 构造写入器上下文。
            Writer.Context writerContext = makeWriterContext(
                    importInfo, task, importMetadataMap, createdTemporaryNames
            );
            // 构造导入器执行器。
            Map<String, Importer.Executor> importerExecutorMap = makeImporterExecutorMap(importInfo, importerContext);
            // 构造写入器执行器。
            Map<String, Writer.Executor> writerExecutorMap = makeWriterExecutorMap(importInfo, writerContext);
            // 构造导入数据缓冲。
            Set<String> importerExecutorKeySet = importerExecutorMap.keySet();
            Set<String> writerExecutorKeySet = writerExecutorMap.keySet();
            Map<String, ImportDataBuffer> importDataBufferMap = makeImportDataBufferMap(
                    importerExecutorKeySet, writerExecutorKeySet
            );

            // 定义 CompletableFuture 集合。
            Set<CompletableFuture<?>> completableFutureSet = new HashSet<>();

            // 为每个导入器执行器创建导入任务，生成 CompletableFuture，添加到集合中。
            for (Map.Entry<String, Importer.Executor> entry : importerExecutorMap.entrySet()) {
                String identifier = entry.getKey();
                Importer.Executor importerExecutor = entry.getValue();
                ImportExecutorTask importExecutorTask = makeImportTask(
                        importInfo, task, identifier, importerExecutor, importDataBufferMap.values()
                );
                CompletableFuture<Void> future = CompletableFuture.runAsync(importExecutorTask, executor);
                completableFutureSet.add(future);
            }

            // 为每个写入器执行器创建写入任务，生成 CompletableFuture，添加到集合中。
            for (Map.Entry<String, Writer.Executor> entry : writerExecutorMap.entrySet()) {
                String identifier = entry.getKey();
                Writer.Executor writerExecutor = entry.getValue();
                WriteExecutorTask writeExecutorTask = makeWriteTask(
                        importInfo, task, identifier, writerExecutor, importDataBufferMap.get(identifier)
                );
                CompletableFuture<Void> future = CompletableFuture.runAsync(writeExecutorTask, executor);
                completableFutureSet.add(future);
            }

            // 等待所有任务完成。
            try {
                CompletableFuture.allOf(completableFutureSet.toArray(new CompletableFuture[0])).join();
            } catch (CompletionException e) {
                throw (Exception) e.getCause();
            }

            // 将任务状态置为完成。
            importTaskOperateHandler.finish(new TaskFinishInfo(taskKey, "由导入处理器设置为完成"));
        } catch (Exception e) {
            // 记录日志。
            String logMessage = "导入任务执行失败, 任务主键: " + taskKey + ", 异常信息如下: ";
            LOGGER.warn(logMessage, e);

            // 将任务状态置为失败。
            importTaskOperateHandler.fail(new TaskFailInfo(taskKey, "由导入处理器设置为失败"));
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
        BeatSchedulerTask beatSchedulerTask = ctx.getBean(BeatSchedulerTask.class, importTaskOperateHandler, taskKey);
        return scheduler.scheduleAtFixedRate(
                beatSchedulerTask,
                new Date(System.currentTimeMillis() + beatInterval),
                beatInterval
        );
    }

    private Map<String, ImportMetadata> inspectImportMetadata(LongIdKey taskKey) throws Exception {
        List<ImportMetadata> importMetadata = importMetadataMaintainService.lookupAsList(
                ImportMetadataMaintainService.CHILD_FOR_TASK, new Object[]{taskKey}
        );
        return importMetadata.stream().collect(Collectors.toMap(
                metadata -> metadata.getKey().getIdentifier(), Function.identity()
        ));
    }

    private Importer.Context makeImporterContext(
            ImportInfo importInfo, ImportTask importTask, Map<String, ImportMetadata> importMetadataMap,
            Set<String> createdTemporaryNames
    ) {
        return ctx.getBean(
                InternalImporterContext.class,
                temporaryFileHandler,
                importTemplateOperateHandler,
                importFileOperateHandler,
                importTask,
                importInfo.getImportConfMap(),
                importInfo.getImportTemplateInfoMap(),
                importMetadataMap,
                createdTemporaryNames
        );
    }

    private Writer.Context makeWriterContext(
            ImportInfo importInfo, ImportTask importTask, Map<String, ImportMetadata> importMetadataMap,
            Set<String> createdTemporaryNames
    ) {
        return ctx.getBean(
                InternalWriterContext.class,
                temporaryFileHandler,
                importTemplateOperateHandler,
                importFileOperateHandler,
                importTask,
                importInfo.getImportConfMap(),
                importInfo.getImportTemplateInfoMap(),
                importMetadataMap,
                createdTemporaryNames
        );
    }

    private Map<String, Importer.Executor> makeImporterExecutorMap(
            ImportInfo importInfo, Importer.Context importContext
    ) throws Exception {
        Map<String, Importer.Executor> result = new HashMap<>();
        for (Map.Entry<String, Importer> entry : importInfo.getImporterMap().entrySet()) {
            String identifier = entry.getKey();
            Importer importer = entry.getValue();
            Importer.Executor importerExecutor = importer.newExecutor();
            importerExecutor.init(importContext);
            result.put(identifier, importerExecutor);
        }
        return result;
    }

    private Map<String, Writer.Executor> makeWriterExecutorMap(
            ImportInfo importInfo, Writer.Context writerContext
    ) throws Exception {
        Map<String, Writer.Executor> result = new HashMap<>();
        for (Map.Entry<String, Writer> entry : importInfo.getWriterMap().entrySet()) {
            String identifier = entry.getKey();
            Writer writer = entry.getValue();
            Writer.Executor writerExecutor = writer.newExecutor();
            writerExecutor.init(writerContext);
            result.put(identifier, writerExecutor);
        }
        return result;
    }

    private Map<String, ImportDataBuffer> makeImportDataBufferMap(
            Set<String> importerExecutorKeySet, Set<String> writerExecutorKeySet
    ) {
        Map<String, ImportDataBuffer> result = new HashMap<>();
        for (String writerIdentifier : writerExecutorKeySet) {
            result.put(writerIdentifier, new ImportDataBuffer(importerExecutorKeySet));
        }
        return result;
    }

    private ImportExecutorTask makeImportTask(
            ImportInfo importInfo, ImportTask importTask, String identifier, Importer.Executor importerExecutor,
            Collection<ImportDataBuffer> buffers
    ) {
        return ctx.getBean(
                ImportExecutorTask.class,
                importTaskOperateHandler,
                importInfo.getImportTaskSetting().getKey(),
                importTask.getKey(),
                identifier,
                importerExecutor,
                buffers
        );
    }

    private WriteExecutorTask makeWriteTask(
            ImportInfo importInfo, ImportTask importTask, String identifier, Writer.Executor writerExecutor,
            ImportDataBuffer buffer
    ) {
        return ctx.getBean(
                WriteExecutorTask.class,
                importInfo.getImportTaskSetting().getKey(),
                importTask.getKey(),
                identifier,
                writerExecutor,
                buffer
        );
    }

    @Override
    public File downloadFile(FileDownloadInfo downloadInfo) throws HandlerException {
        try {
            return importFileOperateHandler.download(downloadInfo, Operator.SERVICE);
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    @Override
    public FileStream downloadFileStream(FileStreamDownloadInfo downloadInfo) throws HandlerException {
        try {
            return importFileOperateHandler.downloadStream(downloadInfo, Operator.SERVICE);
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

        private final ImportTaskOperateHandler importTaskOperateHandler;

        private final LongIdKey taskKey;

        public BeatSchedulerTask(ImportTaskOperateHandler importTaskOperateHandler, LongIdKey taskKey) {
            this.importTaskOperateHandler = importTaskOperateHandler;
            this.taskKey = taskKey;
        }

        @Override
        public void run() {
            try {
                importTaskOperateHandler.beat(new TaskBeatInfo(taskKey));
            } catch (Exception e) {
                LOGGER.warn("心跳任务执行失败, 任务主键为 {}, 异常信息如下: ", taskKey, e);
            }
        }

        @Override
        public String toString() {
            return "BeatSchedulerTask{" +
                    "importTaskOperateHandler=" + importTaskOperateHandler +
                    ", taskKey=" + taskKey +
                    '}';
        }
    }

    /**
     * 写入器上下文的内部实现。
     *
     * @author DwArFeng
     * @since 1.0.0
     */
    @Component
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public static class InternalWriterContext implements Writer.Context {

        private final TemporaryFileHandler temporaryFileHandler;
        private final ImportTemplateOperateHandler importTemplateOperateHandler;
        private final ImportFileOperateHandler importFileOperateHandler;

        private final ImportTask importTask;
        private final Map<String, ImportConf> importConfMap;
        private final Map<String, ImportTemplateInfo> importTemplateInfoMap;
        private final Map<String, ImportMetadata> importMetadataMap;

        private final Set<String> createdTemporaryNames;

        @SuppressWarnings("DuplicatedCode")
        public InternalWriterContext(
                TemporaryFileHandler temporaryFileHandler,
                ImportTemplateOperateHandler importTemplateOperateHandler,
                ImportFileOperateHandler importFileOperateHandler,
                ImportTask importTask,
                Map<String, ImportConf> importConfMap,
                Map<String, ImportTemplateInfo> importTemplateInfoMap,
                Map<String, ImportMetadata> importMetadataMap,
                Set<String> createdTemporaryNames
        ) {
            this.temporaryFileHandler = temporaryFileHandler;
            this.importTemplateOperateHandler = importTemplateOperateHandler;
            this.importFileOperateHandler = importFileOperateHandler;
            this.importTask = importTask;
            this.importConfMap = importConfMap;
            this.importTemplateInfoMap = importTemplateInfoMap;
            this.importMetadataMap = importMetadataMap;
            this.createdTemporaryNames = createdTemporaryNames;
        }

        @Override
        public ImportConf getConf(String identifier) {
            return importConfMap.get(identifier);
        }

        @Override
        public Template retrieveTemplate(TemplateRetrieveInfo info) throws Exception {
            ImportTemplateInfo importTemplateInfo = importTemplateInfoMap.get(info.getIdentifier());
            TemplateDownloadInfo downloadInfo = new TemplateDownloadInfo(importTemplateInfo.getKey());
            return importTemplateOperateHandler.download(downloadInfo);
        }

        @Override
        public TemplateStream retrieveTemplateStream(TemplateStreamRetrieveInfo info) throws Exception {
            ImportTemplateInfo importTemplateInfo = importTemplateInfoMap.get(info.getIdentifier());
            TemplateStreamDownloadInfo downloadInfo = new TemplateStreamDownloadInfo(importTemplateInfo.getKey());
            return importTemplateOperateHandler.downloadStream(downloadInfo);
        }

        @Override
        public ImportMetadata getMetadata(String identifier) {
            return importMetadataMap.get(identifier);
        }

        @Override
        public File retrieveFile(FileRetrieveInfo info) throws Exception {
            FileDownloadInfo downloadInfo = new FileDownloadInfo(
                    new TaskItemKey(importTask.getKey().getLongId(), info.getIdentifier())
            );
            return importFileOperateHandler.download(downloadInfo, Operator.WRITER_CONTEXT);
        }

        @Override
        public FileStream retrieveFileStream(FileStreamRetrieveInfo info) throws Exception {
            FileStreamDownloadInfo downloadInfo = new FileStreamDownloadInfo(
                    new TaskItemKey(importTask.getKey().getLongId(), info.getIdentifier())
            );
            return importFileOperateHandler.downloadStream(downloadInfo, Operator.WRITER_CONTEXT);
        }

        @Override
        public void storeFile(FileStoreInfo info) throws Exception {
            FileUploadInfo uploadInfo = new FileUploadInfo(
                    new TaskItemKey(importTask.getKey().getLongId(), info.getIdentifier()),
                    info.getOriginName(), info.getContent()
            );
            importFileOperateHandler.upload(uploadInfo, Operator.WRITER_CONTEXT);
        }

        @Override
        public void storeFileStream(FileStreamStoreInfo info) throws Exception {
            FileStreamUploadInfo uploadInfo = new FileStreamUploadInfo(
                    new TaskItemKey(importTask.getKey().getLongId(), info.getIdentifier()),
                    info.getOriginName(), info.getLength(), info.getContent()
            );
            importFileOperateHandler.uploadStream(uploadInfo, Operator.WRITER_CONTEXT);
        }

        @Override
        public void removeFile(String identifier) throws Exception {
            FileRemoveInfo removeInfo = new FileRemoveInfo(
                    new TaskItemKey(importTask.getKey().getLongId(), identifier)
            );
            importFileOperateHandler.remove(removeInfo, Operator.WRITER_CONTEXT);
        }

        @Override
        public void createTemporary(String name) throws Exception {
            LongIdKey taskKey = importTask.getKey();
            temporaryFileHandler.create(TEMPORARY_FILE_TYPE, taskKey, name);
            createdTemporaryNames.add(name);
        }

        @Override
        public InputStream openTemporaryInputStream(String name) throws Exception {
            LongIdKey taskKey = importTask.getKey();
            return temporaryFileHandler.openInputStream(TEMPORARY_FILE_TYPE, taskKey, name);
        }

        @Override
        public OutputStream openTemporaryOutputStream(String name) throws Exception {
            LongIdKey taskKey = importTask.getKey();
            return temporaryFileHandler.openOutputStream(TEMPORARY_FILE_TYPE, taskKey, name);
        }

        @SuppressWarnings("DuplicatedCode")
        @Override
        public void copyTemplateToTemporary(String templateIdentifier, String temporaryName) throws Exception {
            ImportTemplateInfo importTemplateInfo = importTemplateInfoMap.get(templateIdentifier);
            TemplateStreamDownloadInfo downloadInfo = new TemplateStreamDownloadInfo(importTemplateInfo.getKey());
            TemplateStream templateStream = importTemplateOperateHandler.downloadStream(downloadInfo);
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
                    new TaskItemKey(importTask.getKey().getLongId(), fileIdentifier)
            );
            FileStream fileStream = importFileOperateHandler.downloadStream(downloadInfo, Operator.WRITER_CONTEXT);
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
            LongIdKey taskKey = importTask.getKey();
            long length = temporaryFileHandler.length(TEMPORARY_FILE_TYPE, taskKey, temporaryName);
            try (
                    InputStream inputStream = openTemporaryInputStream(temporaryName)
            ) {
                FileStreamUploadInfo uploadInfo = new FileStreamUploadInfo(
                        new TaskItemKey(taskKey.getLongId(), fileIdentifier),
                        temporaryName, length, inputStream
                );
                importFileOperateHandler.uploadStream(uploadInfo, Operator.WRITER_CONTEXT);
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
            LongIdKey taskKey = importTask.getKey();
            long length = temporaryFileHandler.length(TEMPORARY_FILE_TYPE, taskKey, temporaryName);
            try (
                    InputStream inputStream = openTemporaryInputStream(temporaryName)
            ) {
                FileStreamUploadInfo uploadInfo = new FileStreamUploadInfo(
                        new TaskItemKey(taskKey.getLongId(), fileIdentifier),
                        fileOriginName, length, inputStream
                );
                importFileOperateHandler.uploadStream(uploadInfo, Operator.WRITER_CONTEXT);
            }
        }

        @Override
        public String toString() {
            return "InternalWriterContext{" +
                    "temporaryFileHandler=" + temporaryFileHandler +
                    ", importTemplateOperateHandler=" + importTemplateOperateHandler +
                    ", importFileOperateHandler=" + importFileOperateHandler +
                    ", importTask=" + importTask +
                    ", importConfMap=" + importConfMap +
                    ", importTemplateInfoMap=" + importTemplateInfoMap +
                    ", importMetadataMap=" + importMetadataMap +
                    ", createdTemporaryNames=" + createdTemporaryNames +
                    '}';
        }
    }

    /**
     * 导入器上下文的内部实现。
     *
     * @author DwArFeng
     * @since 1.0.0
     */
    @Component
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public static class InternalImporterContext implements Importer.Context {

        private final TemporaryFileHandler temporaryFileHandler;
        private final ImportTemplateOperateHandler importTemplateOperateHandler;
        private final ImportFileOperateHandler importFileOperateHandler;

        private final ImportTask importTask;
        private final Map<String, ImportConf> importConfMap;
        private final Map<String, ImportTemplateInfo> importTemplateInfoMap;
        private final Map<String, ImportMetadata> importMetadataMap;

        private final Set<String> createdTemporaryNames;

        @SuppressWarnings("DuplicatedCode")
        public InternalImporterContext(
                TemporaryFileHandler temporaryFileHandler,
                ImportTemplateOperateHandler importTemplateOperateHandler,
                ImportFileOperateHandler importFileOperateHandler,
                ImportTask importTask,
                Map<String, ImportConf> importConfMap,
                Map<String, ImportTemplateInfo> importTemplateInfoMap,
                Map<String, ImportMetadata> importMetadataMap,
                Set<String> createdTemporaryNames
        ) {
            this.temporaryFileHandler = temporaryFileHandler;
            this.importTemplateOperateHandler = importTemplateOperateHandler;
            this.importFileOperateHandler = importFileOperateHandler;
            this.importTask = importTask;
            this.importConfMap = importConfMap;
            this.importTemplateInfoMap = importTemplateInfoMap;
            this.importMetadataMap = importMetadataMap;
            this.createdTemporaryNames = createdTemporaryNames;
        }

        @Override
        public ImportConf getConf(String identifier) {
            return importConfMap.get(identifier);
        }

        @Override
        public Template retrieveTemplate(TemplateRetrieveInfo info) throws Exception {
            ImportTemplateInfo importTemplateInfo = importTemplateInfoMap.get(info.getIdentifier());
            TemplateDownloadInfo downloadInfo = new TemplateDownloadInfo(importTemplateInfo.getKey());
            return importTemplateOperateHandler.download(downloadInfo);
        }

        @Override
        public TemplateStream retrieveTemplateStream(TemplateStreamRetrieveInfo info) throws Exception {
            ImportTemplateInfo importTemplateInfo = importTemplateInfoMap.get(info.getIdentifier());
            TemplateStreamDownloadInfo downloadInfo = new TemplateStreamDownloadInfo(importTemplateInfo.getKey());
            return importTemplateOperateHandler.downloadStream(downloadInfo);
        }

        @Override
        public ImportMetadata getMetadata(String identifier) {
            return importMetadataMap.get(identifier);
        }

        @Override
        public File retrieveFile(FileRetrieveInfo info) throws Exception {
            FileDownloadInfo downloadInfo = new FileDownloadInfo(
                    new TaskItemKey(importTask.getKey().getLongId(), info.getIdentifier())
            );
            return importFileOperateHandler.download(downloadInfo, Operator.IMPORTER_CONTEXT);
        }

        @Override
        public FileStream retrieveFileStream(FileStreamRetrieveInfo info) throws Exception {
            FileStreamDownloadInfo downloadInfo = new FileStreamDownloadInfo(
                    new TaskItemKey(importTask.getKey().getLongId(), info.getIdentifier())
            );
            return importFileOperateHandler.downloadStream(downloadInfo, Operator.IMPORTER_CONTEXT);
        }

        @Override
        public void storeFile(FileStoreInfo info) throws Exception {
            FileUploadInfo uploadInfo = new FileUploadInfo(
                    new TaskItemKey(importTask.getKey().getLongId(), info.getIdentifier()),
                    info.getOriginName(), info.getContent()
            );
            importFileOperateHandler.upload(uploadInfo, Operator.IMPORTER_CONTEXT);
        }

        @Override
        public void storeFileStream(FileStreamStoreInfo info) throws Exception {
            FileStreamUploadInfo uploadInfo = new FileStreamUploadInfo(
                    new TaskItemKey(importTask.getKey().getLongId(), info.getIdentifier()),
                    info.getOriginName(), info.getLength(), info.getContent()
            );
            importFileOperateHandler.uploadStream(uploadInfo, Operator.IMPORTER_CONTEXT);
        }

        @Override
        public void removeFile(String identifier) throws Exception {
            FileRemoveInfo removeInfo = new FileRemoveInfo(
                    new TaskItemKey(importTask.getKey().getLongId(), identifier)
            );
            importFileOperateHandler.remove(removeInfo, Operator.IMPORTER_CONTEXT);
        }

        @Override
        public void createTemporary(String name) throws Exception {
            LongIdKey taskKey = importTask.getKey();
            temporaryFileHandler.create(TEMPORARY_FILE_TYPE, taskKey, name);
            createdTemporaryNames.add(name);
        }

        @Override
        public InputStream openTemporaryInputStream(String name) throws Exception {
            LongIdKey taskKey = importTask.getKey();
            return temporaryFileHandler.openInputStream(TEMPORARY_FILE_TYPE, taskKey, name);
        }

        @Override
        public OutputStream openTemporaryOutputStream(String name) throws Exception {
            LongIdKey taskKey = importTask.getKey();
            return temporaryFileHandler.openOutputStream(TEMPORARY_FILE_TYPE, taskKey, name);
        }

        @SuppressWarnings("DuplicatedCode")
        @Override
        public void copyTemplateToTemporary(String templateIdentifier, String temporaryName) throws Exception {
            ImportTemplateInfo importTemplateInfo = importTemplateInfoMap.get(templateIdentifier);
            TemplateStreamDownloadInfo downloadInfo = new TemplateStreamDownloadInfo(importTemplateInfo.getKey());
            TemplateStream templateStream = importTemplateOperateHandler.downloadStream(downloadInfo);
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
                    new TaskItemKey(importTask.getKey().getLongId(), fileIdentifier)
            );
            FileStream fileStream = importFileOperateHandler.downloadStream(downloadInfo, Operator.IMPORTER_CONTEXT);
            try (
                    InputStream inputStream = fileStream.getContent();
                    OutputStream outputStream = openTemporaryOutputStream(temporaryName)
            ) {
                IOUtil.trans(inputStream, outputStream, 4096);
            }
        }

        @Override
        public void copyTemporaryToFile(String temporaryName, String fileIdentifier) throws Exception {
            LongIdKey taskKey = importTask.getKey();
            long length = temporaryFileHandler.length(TEMPORARY_FILE_TYPE, taskKey, temporaryName);
            try (
                    InputStream inputStream = openTemporaryInputStream(temporaryName)
            ) {
                FileStreamUploadInfo uploadInfo = new FileStreamUploadInfo(
                        new TaskItemKey(taskKey.getLongId(), fileIdentifier),
                        temporaryName, length, inputStream
                );
                importFileOperateHandler.uploadStream(uploadInfo, Operator.IMPORTER_CONTEXT);
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
            LongIdKey taskKey = importTask.getKey();
            long length = temporaryFileHandler.length(TEMPORARY_FILE_TYPE, taskKey, temporaryName);
            try (
                    InputStream inputStream = openTemporaryInputStream(temporaryName)
            ) {
                FileStreamUploadInfo uploadInfo = new FileStreamUploadInfo(
                        new TaskItemKey(taskKey.getLongId(), fileIdentifier),
                        fileOriginName, length, inputStream
                );
                importFileOperateHandler.uploadStream(uploadInfo, Operator.WRITER_CONTEXT);
            }
        }

        @Override
        public String toString() {
            return "InternalImporterContext{" +
                    "temporaryFileHandler=" + temporaryFileHandler +
                    ", importTemplateOperateHandler=" + importTemplateOperateHandler +
                    ", importFileOperateHandler=" + importFileOperateHandler +
                    ", importTask=" + importTask +
                    ", importConfMap=" + importConfMap +
                    ", importTemplateInfoMap=" + importTemplateInfoMap +
                    ", importMetadataMap=" + importMetadataMap +
                    ", createdTemporaryNames=" + createdTemporaryNames +
                    '}';
        }
    }

    /**
     * 写入执行器任务。
     *
     * @author DwArFeng
     * @since 1.0.0
     */
    @Component
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public static class ImportExecutorTask implements Runnable {

        private static final Logger LOGGER = LoggerFactory.getLogger(ImportExecutorTask.class);

        private static final int EXECUTE_STATUS_EXECUTED = 1;
        private static final int EXECUTE_STATUS_EXCEPTION = 2;

        private final ImportTaskOperateHandler importTaskOperateHandler;

        private final LongIdKey taskSettingKey;
        private final LongIdKey taskKey;

        private final String identifier;

        private final Importer.Executor importerExecutor;
        private final Collection<ImportDataBuffer> buffers;

        public ImportExecutorTask(
                ImportTaskOperateHandler importTaskOperateHandler,
                LongIdKey taskSettingKey,
                LongIdKey taskKey,
                String identifier,
                Importer.Executor importerExecutor,
                Collection<ImportDataBuffer> buffers
        ) {
            this.importTaskOperateHandler = importTaskOperateHandler;
            this.taskSettingKey = taskSettingKey;
            this.taskKey = taskKey;
            this.identifier = identifier;
            this.importerExecutor = importerExecutor;
            this.buffers = buffers;
        }

        @Override
        public void run() {
            // 获取主题空间。
            Set<String> topicSpace;
            try {
                // 执行方法。
                topicSpace = importerExecutor.topicSpace();
            } catch (Exception e) {
                // 将所有导入数据缓冲标识符对应的输入标记为结束。
                buffers.forEach(buffer -> buffer.markEndOfInput(identifier));
                // 记录日志。
                String message = "获取写入器执行器主题空间时发生异常, 任务设置键: " + taskSettingKey + ", 任务键: " +
                        taskKey + ", 写入器标识: " + identifier + ", 异常信息如下: ";
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
                importerExecutor.beforeExecute();
                // 置位状态标识。
                beforeExecuteExecuteStatus = EXECUTE_STATUS_EXECUTED;
            } catch (Exception e) {
                // 将所有导入数据缓冲标识符对应的输入标记为结束。
                buffers.forEach(buffer -> buffer.markEndOfInput(identifier));
                // 记录日志。
                String message = "写入器执行器执行前调度时发生异常, 任务设置键: " + taskSettingKey + ", 任务键: " +
                        taskKey + ", 写入器标识: " + identifier + ", 异常信息如下: ";
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
                    for (String topic : topicSpace) {
                        executeSingleTopic(topic);
                    }
                } catch (Exception e) {
                    // 将所有导入数据缓冲标识符对应的输入标记为结束。
                    buffers.forEach(buffer -> buffer.markEndOfInput(identifier));
                    // 记录日志。
                    String message = "写入器执行器执行调度时发生异常, 任务设置键: " + taskSettingKey + ", 任务键: " +
                            taskKey + ", 写入器标识: " + identifier + ", 异常信息如下: ";
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
                importerExecutor.afterExecute();
            } catch (Exception e) {
                // 将所有导入数据缓冲标识符对应的输入标记为结束。
                buffers.forEach(buffer -> buffer.markEndOfInput(identifier));
                // 记录日志。
                String message = "写入器执行器执行后执行调度时发生异常, 任务设置键: " + taskSettingKey + ", 任务键: " +
                        taskKey + ", 写入器标识: " + identifier + ", 异常信息如下: ";
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
            // 循环写入，直到写入完毕。
            boolean hasNext;
            do {
                // 获取导入结果。
                ImportResult importResult = importerExecutor.execute(topic);
                // 展开参数。
                hasNext = importResult.isHasNext();
                List<Map<String, Object>> datas = importResult.getDatas();
                // 更新任务数据量。
                importTaskOperateHandler.increaseDataCount(new TaskDataCountIncreaseInfo(taskKey, datas.size()));
                // 封装数据块。
                ImportDataBuffer.DataBlock dataBlock = new ImportDataBuffer.DataBlock(
                        identifier, topic, importResult
                );
                // 将数据块放入缓冲。
                buffers.forEach(buffer -> buffer.put(dataBlock));
            } while (hasNext);
        }

        @Override
        public String toString() {
            return "ImportExecutorTask{" +
                    "taskSettingKey=" + taskSettingKey +
                    ", taskKey=" + taskKey +
                    ", identifier='" + identifier + '\'' +
                    ", importerExecutor=" + importerExecutor +
                    ", buffers=" + buffers +
                    '}';
        }
    }

    /**
     * 导入执行器任务。
     *
     * @author DwArFeng
     * @since 1.0.0
     */
    @Component
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public static class WriteExecutorTask implements Runnable {

        private static final Logger LOGGER = LoggerFactory.getLogger(WriteExecutorTask.class);

        private static final int EXECUTE_STATUS_EXECUTED = 1;
        private static final int EXECUTE_STATUS_EXCEPTION = 2;

        private final LongIdKey taskSettingKey;
        private final LongIdKey taskKey;

        private final String identifier;

        private final Writer.Executor writerExecutor;
        private final ImportDataBuffer buffer;

        public WriteExecutorTask(
                LongIdKey taskSettingKey,
                LongIdKey taskKey,
                String identifier,
                Writer.Executor writerExecutor,
                ImportDataBuffer buffer
        ) {
            this.taskSettingKey = taskSettingKey;
            this.taskKey = taskKey;
            this.identifier = identifier;
            this.writerExecutor = writerExecutor;
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
                writerExecutor.beforeExecute();
                // 置位状态标识。
                beforeExecuteExecuteStatus = EXECUTE_STATUS_EXECUTED;
            } catch (Exception e) {
                // 将导入数据缓冲的输出标记为结束。
                buffer.markEndOfOutput();
                // 记录日志。
                String message = "导入器执行器执行前调度时发生异常, 任务设置键: " + taskSettingKey + ", 任务键: " +
                        taskKey + ", 导入器标识: " + identifier + ", 异常信息如下: ";
                LOGGER.warn(message, e);
                // 置位异常。
                occurredException = e;
                // 置位状态标识。
                beforeExecuteExecuteStatus = EXECUTE_STATUS_EXCEPTION;
            }

            // 如果执行前调度方法被执行，则执行执行调度。
            if (Objects.equals(beforeExecuteExecuteStatus, EXECUTE_STATUS_EXECUTED)) {
                try {
                    // 循环导入，直到导入完毕。
                    while (!buffer.isEndOfOutput()) {
                        ImportDataBuffer.DataBlock dataBlock = buffer.take();
                        if (Objects.isNull(dataBlock)) {
                            continue;
                        }
                        String writerIdentifier = dataBlock.getIdentifier();
                        String writerTopic = dataBlock.getTopic();
                        ImportResult importResult = dataBlock.getImportResult();
                        writerExecutor.execute(writerIdentifier, writerTopic, importResult);
                    }
                } catch (Exception e) {
                    // 将导出数据缓冲的输出标记为结束。
                    buffer.markEndOfOutput();
                    // 记录日志。
                    String message = "导入器执行器执行调度时发生异常, 任务设置键: " + taskSettingKey + ", 任务键: " +
                            taskKey + ", 导入器标识: " + identifier + ", 异常信息如下: ";
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
                writerExecutor.afterExecute();
            } catch (Exception e) {
                // 将导出数据缓冲的输出标记为结束。
                buffer.markEndOfOutput();
                // 记录日志。
                String message = "导入器执行器执行后执行调度时发生异常, 任务设置键: " + taskSettingKey + ", 任务键: " +
                        taskKey + ", 导入器标识: " + identifier + ", 异常信息如下: ";
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
            return "WriteExecutorTask{" +
                    "taskSettingKey=" + taskSettingKey +
                    ", taskKey=" + taskKey +
                    ", identifier='" + identifier + '\'' +
                    ", writerExecutor=" + writerExecutor +
                    ", buffers=" + buffer +
                    '}';
        }
    }
}
