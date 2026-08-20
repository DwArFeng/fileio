package com.dwarfeng.fileio.impl.service;

import com.dwarfeng.fileio.stack.bean.dto.*;
import com.dwarfeng.fileio.stack.bean.key.TaskSettingItemKey;
import com.dwarfeng.fileio.stack.handler.ImportTemplateOperateHandler;
import com.dwarfeng.fileio.stack.service.ImportTemplateQosService;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.springframework.stereotype.Service;

@Service
public class ImportTemplateQosServiceImpl implements ImportTemplateQosService {

    private final ImportTemplateOperateHandler importTemplateOperateHandler;

    private final ServiceExceptionMapper sem;

    public ImportTemplateQosServiceImpl(
            ImportTemplateOperateHandler importTemplateOperateHandler, ServiceExceptionMapper sem
    ) {
        this.importTemplateOperateHandler = importTemplateOperateHandler;
        this.sem = sem;
    }

    @Override
    public TaskSettingItemKey create(TemplateCreateInfo createInfo) throws ServiceException {
        try {
            return importTemplateOperateHandler.create(createInfo);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("创建模板时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public Template download(TemplateDownloadInfo downloadInfo) throws ServiceException {
        try {
            return importTemplateOperateHandler.download(downloadInfo);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("下载模板时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public TemplateStream downloadStream(TemplateStreamDownloadInfo downloadInfo) throws ServiceException {
        try {
            return importTemplateOperateHandler.downloadStream(downloadInfo);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("下载模板流时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public void upload(TemplateUploadInfo uploadInfo) throws ServiceException {
        try {
            importTemplateOperateHandler.upload(uploadInfo);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("上传模板时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public void uploadStream(TemplateStreamUploadInfo uploadInfo) throws ServiceException {
        try {
            importTemplateOperateHandler.uploadStream(uploadInfo);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("上传模板流时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public void remove(TemplateRemoveInfo removeInfo) throws ServiceException {
        try {
            importTemplateOperateHandler.remove(removeInfo);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("删除模板时发生异常", LogLevel.WARN, e, sem);
        }
    }
}
