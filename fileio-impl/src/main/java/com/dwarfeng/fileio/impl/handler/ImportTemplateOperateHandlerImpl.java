package com.dwarfeng.fileio.impl.handler;

import com.dwarfeng.dutil.basic.io.IOUtil;
import com.dwarfeng.fileio.sdk.util.Constants;
import com.dwarfeng.fileio.sdk.util.FileUtil;
import com.dwarfeng.fileio.sdk.util.KeyUtil;
import com.dwarfeng.fileio.stack.bean.dto.*;
import com.dwarfeng.fileio.stack.bean.entity.ImportTemplateInfo;
import com.dwarfeng.fileio.stack.bean.key.TaskSettingItemKey;
import com.dwarfeng.fileio.stack.handler.ImportTemplateOperateHandler;
import com.dwarfeng.fileio.stack.service.ImportTemplateInfoMaintainService;
import com.dwarfeng.ftp.stack.handler.FtpHandler;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

@Component
public class ImportTemplateOperateHandlerImpl implements ImportTemplateOperateHandler {

    private final ImportTemplateInfoMaintainService importTemplateInfoMaintainService;
    private final FtpHandler ftpHandler;

    private final HandlerValidator handlerValidator;

    private final FtpPathResolver ftpPathResolver;

    public ImportTemplateOperateHandlerImpl(
            ImportTemplateInfoMaintainService importTemplateInfoMaintainService,
            FtpHandler ftpHandler,
            HandlerValidator handlerValidator,
            FtpPathResolver ftpPathResolver
    ) {
        this.importTemplateInfoMaintainService = importTemplateInfoMaintainService;
        this.ftpHandler = ftpHandler;
        this.handlerValidator = handlerValidator;
        this.ftpPathResolver = ftpPathResolver;
    }

    @Override
    public TaskSettingItemKey create(TemplateCreateInfo createInfo) throws HandlerException {
        try {
            // 展开参数。
            LongIdKey taskSettingKey = createInfo.getTaskSettingKey();
            String identifier = createInfo.getIdentifier();
            String originName = createInfo.getOriginName();

            // 确认任务设置存在。
            handlerValidator.makeSureImportTaskSettingExists(taskSettingKey);

            // 确认模板信息不存在。
            TaskSettingItemKey taskSettingItemKey = KeyUtil.newTaskSettingItemKey(taskSettingKey, identifier);
            handlerValidator.makeSureImportTemplateInfoNotExists(taskSettingItemKey);

            // 新建空文件。
            ftpHandler.storeFile(
                    ftpPathResolver.resolvePath(FtpPathResolver.RELATIVE_PATHS_IMPORT_TEMPLATE),
                    FileUtil.resolveFileName(taskSettingItemKey),
                    new byte[0]
            );

            // 生成模板信息。
            if (StringUtils.isBlank(originName)) {
                originName = Constants.DEFAULT_FILE_NAME;
            }
            ImportTemplateInfo importTemplateInfo = new ImportTemplateInfo();
            importTemplateInfo.setKey(taskSettingItemKey);
            importTemplateInfo.setOriginName(originName);
            importTemplateInfo.setUploadedDate(new Date());
            importTemplateInfoMaintainService.insert(importTemplateInfo);

            // 返回主键。
            return taskSettingItemKey;
        } catch (HandlerException e) {
            throw e;
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    @Override
    public Template download(TemplateDownloadInfo downloadInfo) throws HandlerException {
        try {
            // 展开参数。
            TaskSettingItemKey key = downloadInfo.getKey();

            // 确认模板信息存在。
            handlerValidator.makeSureImportTemplateInfoExists(key);

            // 获取对应的模板信息。
            ImportTemplateInfo importTemplateInfo = importTemplateInfoMaintainService.get(key);

            // 获取模板文件。
            byte[] content = ftpHandler.retrieveFile(
                    ftpPathResolver.resolvePath(FtpPathResolver.RELATIVE_PATHS_IMPORT_TEMPLATE),
                    FileUtil.resolveFileName(key)
            );

            // 构造模板并返回。
            return new Template(importTemplateInfo.getOriginName(), content);
        } catch (HandlerException e) {
            throw e;
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    @Override
    public TemplateStream downloadStream(TemplateStreamDownloadInfo downloadInfo) throws HandlerException {
        try {
            // 展开参数。
            TaskSettingItemKey key = downloadInfo.getKey();

            // 确认模板信息存在。
            handlerValidator.makeSureImportTemplateInfoExists(key);

            // 获取对应的模板信息。
            ImportTemplateInfo importTemplateInfo = importTemplateInfoMaintainService.get(key);

            // 获取模板对应的输入流。
            InputStream inputStream = ftpHandler.openInputStream(
                    ftpPathResolver.resolvePath(FtpPathResolver.RELATIVE_PATHS_IMPORT_TEMPLATE),
                    FileUtil.resolveFileName(key)
            );

            // 构造模板流并返回。
            return new TemplateStream(importTemplateInfo.getOriginName(), importTemplateInfo.getLength(), inputStream);
        } catch (HandlerException e) {
            throw e;
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    @Override
    public void upload(TemplateUploadInfo uploadInfo) throws HandlerException {
        try {
            // 展开参数。
            TaskSettingItemKey key = uploadInfo.getKey();
            String originName = uploadInfo.getOriginName();
            byte[] content = uploadInfo.getContent();

            // 确认任务设置存在。
            LongIdKey importTaskSettingKey = new LongIdKey(key.getTaskSettingId());
            handlerValidator.makeSureImportTaskSettingExists(importTaskSettingKey);

            // 确认模板信息不存在。
            handlerValidator.makeSureImportTemplateInfoNotExists(key);

            // 存储文件。
            ftpHandler.storeFile(
                    ftpPathResolver.resolvePath(FtpPathResolver.RELATIVE_PATHS_IMPORT_TEMPLATE),
                    FileUtil.resolveFileName(key),
                    content
            );

            // 构造模板信息并插入。
            ImportTemplateInfo importTemplateInfo = new ImportTemplateInfo(
                    key, originName, content.length, new Date(), "通过 templateio 操作处理器上传"
            );
            importTemplateInfoMaintainService.insert(importTemplateInfo);
        } catch (HandlerException e) {
            throw e;
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    @Override
    public void uploadStream(TemplateStreamUploadInfo uploadInfo) throws HandlerException {
        try {
            // 展开参数。
            TaskSettingItemKey key = uploadInfo.getKey();
            String originName = uploadInfo.getOriginName();
            long length = uploadInfo.getLength();
            InputStream cin = uploadInfo.getContent();

            // 确认任务设置存在。
            LongIdKey importTaskSettingKey = new LongIdKey(key.getTaskSettingId());
            handlerValidator.makeSureImportTaskSettingExists(importTaskSettingKey);

            // 存储模板内容（覆盖）。
            try (OutputStream fout = ftpHandler.openOutputStream(
                    ftpPathResolver.resolvePath(FtpPathResolver.RELATIVE_PATHS_IMPORT_TEMPLATE),
                    FileUtil.resolveFileName(key)
            )) {
                IOUtil.trans(cin, fout, 4096);
            }

            // 根据 uploadInfo 构造 ImportTemplateInfo，插入或更新。
            ImportTemplateInfo importTemplateInfo = new ImportTemplateInfo();
            importTemplateInfo.setKey(key);
            importTemplateInfo.setOriginName(originName);
            importTemplateInfo.setLength(length);
            importTemplateInfo.setUploadedDate(new Date());
            importTemplateInfo.setRemark("通过 templateio 操作处理器上传");
            importTemplateInfoMaintainService.insertOrUpdate(importTemplateInfo);
        } catch (HandlerException e) {
            throw e;
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    @Override
    public void remove(TemplateRemoveInfo removeInfo) throws HandlerException {
        try {
            // 展开参数。
            TaskSettingItemKey key = removeInfo.getKey();

            // 确认模板信息存在。
            handlerValidator.makeSureImportTemplateInfoExists(key);

            // 如果存在对应的文件，则删除。
            if (
                    ftpHandler.existsFile(
                            ftpPathResolver.resolvePath(FtpPathResolver.RELATIVE_PATHS_IMPORT_TEMPLATE),
                            FileUtil.resolveFileName(key)
                    )) {
                ftpHandler.deleteFile(
                        ftpPathResolver.resolvePath(FtpPathResolver.RELATIVE_PATHS_IMPORT_TEMPLATE),
                        FileUtil.resolveFileName(key)
                );
            }

            // 删除模板信息。
            importTemplateInfoMaintainService.delete(key);
        } catch (HandlerException e) {
            throw e;
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }
}
