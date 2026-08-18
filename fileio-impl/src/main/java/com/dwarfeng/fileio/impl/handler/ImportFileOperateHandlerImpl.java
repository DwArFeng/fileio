package com.dwarfeng.fileio.impl.handler;

import com.dwarfeng.dutil.basic.io.IOUtil;
import com.dwarfeng.fileio.sdk.util.Constants;
import com.dwarfeng.fileio.sdk.util.FileUtil;
import com.dwarfeng.fileio.sdk.util.KeyUtil;
import com.dwarfeng.fileio.stack.bean.dto.*;
import com.dwarfeng.fileio.stack.bean.entity.ImportFileInfo;
import com.dwarfeng.fileio.stack.bean.key.TaskItemKey;
import com.dwarfeng.fileio.stack.handler.ImportFileOperateHandler;
import com.dwarfeng.fileio.stack.service.ImportFileInfoMaintainService;
import com.dwarfeng.ftp.stack.handler.FtpHandler;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Component
public class ImportFileOperateHandlerImpl implements ImportFileOperateHandler {

    private static final Set<Integer> VALID_STATUS_SET_CREATE;
    private static final Set<Integer> VALID_STATUS_SET_DOWNLOAD;
    private static final Set<Integer> VALID_STATUS_SET_UPLOAD_IMPORTER_CONTEXT;
    private static final Set<Integer> VALID_STATUS_SET_UPLOAD_WRITER_CONTEXT;
    private static final Set<Integer> VALID_STATUS_SET_UPLOAD_SERVICE;
    private static final Set<Integer> VALID_STATUS_SET_REMOVE;

    static {
        Set<Integer> VALID_STATUS_SET_CREATE_DEJA_VU = new HashSet<>();
        VALID_STATUS_SET_CREATE_DEJA_VU.add(Constants.IMPORT_TASK_STATUS_CREATED);
        VALID_STATUS_SET_CREATE = Collections.unmodifiableSet(VALID_STATUS_SET_CREATE_DEJA_VU);

        Set<Integer> VALID_STATUS_SET_DOWNLOAD_DEJA_VU = new HashSet<>();
        VALID_STATUS_SET_DOWNLOAD_DEJA_VU.add(Constants.IMPORT_TASK_STATUS_CREATED);
        VALID_STATUS_SET_DOWNLOAD_DEJA_VU.add(Constants.IMPORT_TASK_STATUS_PROCESSING);
        VALID_STATUS_SET_DOWNLOAD_DEJA_VU.add(Constants.IMPORT_TASK_STATUS_FINISHED);
        VALID_STATUS_SET_DOWNLOAD_DEJA_VU.add(Constants.IMPORT_TASK_STATUS_FAILED);
        VALID_STATUS_SET_DOWNLOAD_DEJA_VU.add(Constants.IMPORT_TASK_STATUS_EXPIRED);
        VALID_STATUS_SET_DOWNLOAD_DEJA_VU.add(Constants.IMPORT_TASK_STATUS_DEAD);
        VALID_STATUS_SET_DOWNLOAD = Collections.unmodifiableSet(VALID_STATUS_SET_DOWNLOAD_DEJA_VU);

        Set<Integer> VALID_STATUS_SET_UPLOAD_IMPORTER_CONTEXT_DEJA_VU = new HashSet<>();
        VALID_STATUS_SET_UPLOAD_IMPORTER_CONTEXT_DEJA_VU.add(Constants.IMPORT_TASK_STATUS_CREATED);
        VALID_STATUS_SET_UPLOAD_IMPORTER_CONTEXT_DEJA_VU.add(Constants.IMPORT_TASK_STATUS_PROCESSING);
        VALID_STATUS_SET_UPLOAD_IMPORTER_CONTEXT = Collections.unmodifiableSet(
                VALID_STATUS_SET_UPLOAD_IMPORTER_CONTEXT_DEJA_VU
        );

        Set<Integer> VALID_STATUS_SET_UPLOAD_WRITER_CONTEXT_DEJA_VU = new HashSet<>();
        VALID_STATUS_SET_UPLOAD_WRITER_CONTEXT_DEJA_VU.add(Constants.IMPORT_TASK_STATUS_CREATED);
        VALID_STATUS_SET_UPLOAD_WRITER_CONTEXT_DEJA_VU.add(Constants.IMPORT_TASK_STATUS_PROCESSING);
        VALID_STATUS_SET_UPLOAD_WRITER_CONTEXT = Collections.unmodifiableSet(
                VALID_STATUS_SET_UPLOAD_WRITER_CONTEXT_DEJA_VU
        );

        Set<Integer> VALID_STATUS_SET_UPLOAD_SERVICE_DEJA_VU = new HashSet<>();
        VALID_STATUS_SET_UPLOAD_SERVICE_DEJA_VU.add(Constants.IMPORT_TASK_STATUS_CREATED);
        VALID_STATUS_SET_UPLOAD_SERVICE = Collections.unmodifiableSet(VALID_STATUS_SET_UPLOAD_SERVICE_DEJA_VU);

        Set<Integer> VALID_STATUS_SET_REMOVE_DEJA_VU = new HashSet<>();
        VALID_STATUS_SET_REMOVE_DEJA_VU.add(Constants.IMPORT_TASK_STATUS_CREATED);
        VALID_STATUS_SET_REMOVE = Collections.unmodifiableSet(VALID_STATUS_SET_REMOVE_DEJA_VU);
    }

    private final ImportFileInfoMaintainService importFileInfoMaintainService;

    private final FtpHandler ftpHandler;

    private final FtpPathResolver ftpPathResolver;

    private final HandlerValidator handlerValidator;

    public ImportFileOperateHandlerImpl(
            ImportFileInfoMaintainService importFileInfoMaintainService,
            FtpHandler ftpHandler, FtpPathResolver ftpPathResolver,
            HandlerValidator handlerValidator
    ) {
        this.importFileInfoMaintainService = importFileInfoMaintainService;
        this.ftpHandler = ftpHandler;
        this.ftpPathResolver = ftpPathResolver;
        this.handlerValidator = handlerValidator;
    }

    @Override
    public TaskItemKey create(FileCreateInfo createInfo, Operator operator) throws HandlerException {
        try {
            // 展开参数。
            LongIdKey taskKey = createInfo.getTaskKey();
            String identifier = createInfo.getIdentifier();
            String originName = createInfo.getOriginName();

            // 确认任务存在。
            handlerValidator.makeSureImportTaskExists(taskKey);

            // 确认任务状态合法。
            handlerValidator.makeSureImportTaskStatusValid(taskKey, VALID_STATUS_SET_CREATE);

            // 确认文件信息不存在。
            TaskItemKey taskItemKey = KeyUtil.newTaskItemKey(taskKey, identifier);
            handlerValidator.makeSureImportFileInfoNotExists(taskItemKey);

            // 新建空文件。
            ftpHandler.storeFile(
                    ftpPathResolver.resolvePath(FtpPathResolver.RELATIVE_PATHS_IMPORT_FILE),
                    FileUtil.resolveFileName(taskItemKey),
                    new byte[0]
            );

            // 生成文件信息。
            if (StringUtils.isBlank(originName)) {
                originName = Constants.DEFAULT_FILE_NAME;
            }
            ImportFileInfo importFileInfo = new ImportFileInfo();
            importFileInfo.setKey(taskItemKey);
            importFileInfo.setOriginName(originName);
            importFileInfo.setUploadedDate(new Date());
            importFileInfoMaintainService.insert(importFileInfo);

            // 返回主键。
            return taskItemKey;
        } catch (HandlerException e) {
            throw e;
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    @Override
    public File download(FileDownloadInfo downloadInfo, Operator operator) throws HandlerException {
        try {
            // 展开参数。
            TaskItemKey key = downloadInfo.getKey();

            // 确认任务存在。
            LongIdKey taskKey = new LongIdKey(key.getTaskId());
            handlerValidator.makeSureImportTaskExists(taskKey);

            // 确认任务状态合法。
            handlerValidator.makeSureImportTaskStatusValid(taskKey, VALID_STATUS_SET_DOWNLOAD);

            // 获取对应的文件信息。
            ImportFileInfo importFileInfo = importFileInfoMaintainService.get(key);

            // 获取文件文件。
            byte[] content = ftpHandler.retrieveFile(
                    ftpPathResolver.resolvePath(FtpPathResolver.RELATIVE_PATHS_IMPORT_FILE),
                    FileUtil.resolveFileName(key)
            );

            // 构造文件并返回。
            return new File(importFileInfo.getOriginName(), content);
        } catch (HandlerException e) {
            throw e;
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    @Override
    public FileStream downloadStream(FileStreamDownloadInfo downloadInfo, Operator operator) throws HandlerException {
        try {
            // 展开参数。
            TaskItemKey key = downloadInfo.getKey();

            // 确认任务存在。
            LongIdKey taskKey = new LongIdKey(key.getTaskId());
            handlerValidator.makeSureImportTaskExists(taskKey);

            // 确认任务状态合法。
            handlerValidator.makeSureImportTaskStatusValid(taskKey, VALID_STATUS_SET_DOWNLOAD);

            // 获取对应的文件信息。
            ImportFileInfo importFileInfo = importFileInfoMaintainService.get(key);

            // 获取文件对应的输入流。
            InputStream inputStream = ftpHandler.openInputStream(
                    ftpPathResolver.resolvePath(FtpPathResolver.RELATIVE_PATHS_IMPORT_FILE),
                    FileUtil.resolveFileName(key)
            );

            // 构造文件流并返回。
            return new FileStream(importFileInfo.getOriginName(), importFileInfo.getLength(), inputStream);
        } catch (HandlerException e) {
            throw e;
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public void upload(FileUploadInfo uploadInfo, Operator operator) throws HandlerException {
        try {
            // 展开参数。
            TaskItemKey key = uploadInfo.getKey();
            String originName = uploadInfo.getOriginName();
            byte[] content = uploadInfo.getContent();

            // 确认任务存在。
            LongIdKey taskKey = new LongIdKey(key.getTaskId());
            handlerValidator.makeSureImportTaskExists(taskKey);

            // 确认任务状态合法。
            switch (operator) {
                case IMPORTER_CONTEXT:
                    handlerValidator.makeSureImportTaskStatusValid(taskKey, VALID_STATUS_SET_UPLOAD_IMPORTER_CONTEXT);
                    break;
                case WRITER_CONTEXT:
                    handlerValidator.makeSureImportTaskStatusValid(taskKey, VALID_STATUS_SET_UPLOAD_WRITER_CONTEXT);
                    break;
                case SERVICE:
                    handlerValidator.makeSureImportTaskStatusValid(taskKey, VALID_STATUS_SET_UPLOAD_SERVICE);
                    break;
                default:
                    throw new IllegalArgumentException("未知的操作者：" + operator);
            }

            // 存储文件。
            ftpHandler.storeFile(
                    ftpPathResolver.resolvePath(FtpPathResolver.RELATIVE_PATHS_IMPORT_FILE),
                    FileUtil.resolveFileName(key),
                    content
            );

            // 构造文件信息并插入或更新。
            ImportFileInfo importFileInfo = new ImportFileInfo(
                    key, originName, content.length, new Date(), "通过 templateio 操作处理器上传"
            );
            importFileInfoMaintainService.insertOrUpdate(importFileInfo);
        } catch (HandlerException e) {
            throw e;
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public void uploadStream(FileStreamUploadInfo uploadInfo, Operator operator) throws HandlerException {
        try {
            // 展开参数。
            TaskItemKey key = uploadInfo.getKey();
            String originName = uploadInfo.getOriginName();
            long length = uploadInfo.getLength();
            InputStream cin = uploadInfo.getContent();

            // 确认任务存在。
            LongIdKey taskKey = new LongIdKey(key.getTaskId());
            handlerValidator.makeSureImportTaskExists(taskKey);

            // 确认任务状态合法。
            switch (operator) {
                case IMPORTER_CONTEXT:
                    handlerValidator.makeSureImportTaskStatusValid(taskKey, VALID_STATUS_SET_UPLOAD_IMPORTER_CONTEXT);
                    break;
                case WRITER_CONTEXT:
                    handlerValidator.makeSureImportTaskStatusValid(taskKey, VALID_STATUS_SET_UPLOAD_WRITER_CONTEXT);
                    break;
                case SERVICE:
                    handlerValidator.makeSureImportTaskStatusValid(taskKey, VALID_STATUS_SET_UPLOAD_SERVICE);
                    break;
                default:
                    throw new IllegalArgumentException("未知的操作者：" + operator);
            }

            // 存储文件内容（覆盖）。
            try (OutputStream fout = ftpHandler.openOutputStream(
                    ftpPathResolver.resolvePath(FtpPathResolver.RELATIVE_PATHS_IMPORT_FILE),
                    FileUtil.resolveFileName(key)
            )) {
                IOUtil.trans(cin, fout, 4096);
            }

            // 根据 uploadInfo 构造 ImportFileInfo，插入或更新。
            ImportFileInfo importFileInfo = new ImportFileInfo();
            importFileInfo.setKey(key);
            importFileInfo.setOriginName(originName);
            importFileInfo.setLength(length);
            importFileInfo.setUploadedDate(new Date());
            importFileInfo.setRemark("通过 templateio 操作处理器上传");
            importFileInfoMaintainService.insertOrUpdate(importFileInfo);
        } catch (HandlerException e) {
            throw e;
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    @Override
    public void remove(FileRemoveInfo removeInfo, Operator operator) throws HandlerException {
        try {
            // 展开参数。
            TaskItemKey key = removeInfo.getKey();

            // 确认任务存在。
            LongIdKey taskKey = new LongIdKey(key.getTaskId());
            handlerValidator.makeSureImportTaskExists(taskKey);

            // 确认任务状态合法。
            handlerValidator.makeSureImportTaskStatusValid(taskKey, VALID_STATUS_SET_REMOVE);

            // 确认文件信息存在。
            handlerValidator.makeSureImportFileInfoExists(key);

            // 如果存在对应的文件，则删除。
            if (
                    ftpHandler.existsFile(
                            ftpPathResolver.resolvePath(FtpPathResolver.RELATIVE_PATHS_IMPORT_FILE),
                            FileUtil.resolveFileName(key)
                    )) {
                ftpHandler.deleteFile(
                        ftpPathResolver.resolvePath(FtpPathResolver.RELATIVE_PATHS_IMPORT_FILE),
                        FileUtil.resolveFileName(key)
                );
            }

            // 删除文件信息。
            importFileInfoMaintainService.delete(key);
        } catch (HandlerException e) {
            throw e;
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }
}
