package com.dwarfeng.fileio.impl.service;

import com.dwarfeng.fileio.stack.bean.dto.*;
import com.dwarfeng.fileio.stack.handler.ImportHandler;
import com.dwarfeng.fileio.stack.service.ImportQosService;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class ImportQosServiceImpl implements ImportQosService {

    private final ImportHandler importHandler;

    private final ServiceExceptionMapper sem;

    public ImportQosServiceImpl(ImportHandler importHandler, ServiceExceptionMapper sem) {
        this.importHandler = importHandler;
        this.sem = sem;
    }

    @Override
    public LongIdKey createTask(TaskCreateInfo createInfo) throws ServiceException {
        try {
            return importHandler.createTask(createInfo);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("创建任务时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public void upsertTaskMetadata(MetadataUpsertInfo upsertInfo) throws ServiceException {
        try {
            importHandler.upsertTaskMetadata(upsertInfo);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("插入/更新任务元数据时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public void uploadFile(FileUploadInfo uploadInfo) throws ServiceException {
        try {
            importHandler.uploadFile(uploadInfo);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("上传文件时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public void uploadFileStream(FileStreamUploadInfo uploadInfo) throws ServiceException {
        try {
            importHandler.uploadFileStream(uploadInfo);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("上传文件流时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public void executeTask(TaskExecuteInfo executeInfo) throws ServiceException {
        try {
            importHandler.executeTask(executeInfo);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("执行任务时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public CompletableFuture<Void> executeTaskAsync(TaskExecuteInfo executeInfo) throws ServiceException {
        try {
            return importHandler.executeTaskAsync(executeInfo);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("异步执行任务时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public File downloadFile(FileDownloadInfo downloadInfo) throws ServiceException {
        try {
            return importHandler.downloadFile(downloadInfo);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("下载文件时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public FileStream downloadFileStream(FileStreamDownloadInfo downloadInfo) throws ServiceException {
        try {
            return importHandler.downloadFileStream(downloadInfo);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("下载文件流时发生异常", LogLevel.WARN, e, sem);
        }
    }
}
