package com.dwarfeng.fileio.impl.handler;

import com.dwarfeng.dutil.basic.io.IOUtil;
import com.dwarfeng.fileio.sdk.util.Constants;
import com.dwarfeng.fileio.sdk.util.FileUtil;
import com.dwarfeng.fileio.sdk.util.KeyUtil;
import com.dwarfeng.fileio.stack.bean.dto.*;
import com.dwarfeng.fileio.stack.bean.entity.ExportTemplateInfo;
import com.dwarfeng.fileio.stack.bean.key.TaskSettingItemKey;
import com.dwarfeng.fileio.stack.handler.ExportTemplateOperateHandler;
import com.dwarfeng.fileio.stack.service.ExportTemplateInfoMaintainService;
import com.dwarfeng.ftp.stack.handler.FtpHandler;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

@Component
public class ExportTemplateOperateHandlerImpl implements ExportTemplateOperateHandler {

    private final ExportTemplateInfoMaintainService exportTemplateInfoMaintainService;

    private final FtpHandler ftpHandler;

    private final FtpPathResolver ftpPathResolver;

    private final HandlerValidator handlerValidator;

    public ExportTemplateOperateHandlerImpl(
            ExportTemplateInfoMaintainService exportTemplateInfoMaintainService,
            FtpHandler ftpHandler, FtpPathResolver ftpPathResolver,
            HandlerValidator handlerValidator
    ) {
        this.exportTemplateInfoMaintainService = exportTemplateInfoMaintainService;
        this.ftpHandler = ftpHandler;
        this.ftpPathResolver = ftpPathResolver;
        this.handlerValidator = handlerValidator;
    }

    @Override
    public TaskSettingItemKey create(TemplateCreateInfo createInfo) throws HandlerException {
        try {
            // 展开参数。
            LongIdKey taskSettingKey = createInfo.getTaskSettingKey();
            String identifier = createInfo.getIdentifier();
            String originName = createInfo.getOriginName();

            // 确认任务设置存在。
            handlerValidator.makeSureExportTaskSettingExists(taskSettingKey);

            // 确认模板信息不存在。
            TaskSettingItemKey taskSettingItemKey = KeyUtil.newTaskSettingItemKey(taskSettingKey, identifier);
            handlerValidator.makeSureExportTemplateInfoNotExists(taskSettingItemKey);

            // 新建空文件。
            ftpHandler.storeFile(
                    ftpPathResolver.resolvePath(FtpPathResolver.RELATIVE_PATHS_EXPORT_TEMPLATE),
                    FileUtil.resolveFileName(taskSettingItemKey),
                    new byte[0]
            );

            // 生成模板信息。
            if (StringUtils.isBlank(originName)) {
                originName = Constants.DEFAULT_FILE_NAME;
            }
            ExportTemplateInfo exportTemplateInfo = new ExportTemplateInfo();
            exportTemplateInfo.setKey(taskSettingItemKey);
            exportTemplateInfo.setOriginName(originName);
            exportTemplateInfo.setUploadedDate(new Date());
            exportTemplateInfoMaintainService.insert(exportTemplateInfo);

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
            handlerValidator.makeSureExportTemplateInfoExists(key);

            // 获取对应的模板信息。
            ExportTemplateInfo exportTemplateInfo = exportTemplateInfoMaintainService.get(key);

            // 获取模板文件。
            byte[] content = ftpHandler.retrieveFile(
                    ftpPathResolver.resolvePath(FtpPathResolver.RELATIVE_PATHS_EXPORT_TEMPLATE),
                    FileUtil.resolveFileName(key)
            );

            // 构造模板并返回。
            return new Template(exportTemplateInfo.getOriginName(), content);
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
            handlerValidator.makeSureExportTemplateInfoExists(key);

            // 获取对应的模板信息。
            ExportTemplateInfo exportTemplateInfo = exportTemplateInfoMaintainService.get(key);

            // 获取模板对应的输入流。
            InputStream inputStream = ftpHandler.openInputStream(
                    ftpPathResolver.resolvePath(FtpPathResolver.RELATIVE_PATHS_EXPORT_TEMPLATE),
                    FileUtil.resolveFileName(key)
            );

            // 构造模板流并返回。
            return new TemplateStream(exportTemplateInfo.getOriginName(), exportTemplateInfo.getLength(), inputStream);
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
            LongIdKey exportTaskSettingKey = new LongIdKey(key.getTaskSettingId());
            handlerValidator.makeSureExportTaskSettingExists(exportTaskSettingKey);

            // 存储文件。
            ftpHandler.storeFile(
                    ftpPathResolver.resolvePath(FtpPathResolver.RELATIVE_PATHS_EXPORT_TEMPLATE),
                    FileUtil.resolveFileName(key),
                    content
            );

            // 构造模板信息并插入或更新。
            ExportTemplateInfo exportTemplateInfo = new ExportTemplateInfo(
                    key, originName, content.length, new Date(), "通过 templateio 操作处理器上传"
            );
            exportTemplateInfoMaintainService.insertOrUpdate(exportTemplateInfo);
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
            LongIdKey exportTaskSettingKey = new LongIdKey(key.getTaskSettingId());
            handlerValidator.makeSureExportTaskSettingExists(exportTaskSettingKey);

            // 存储模板内容（覆盖）。
            try (OutputStream fout = ftpHandler.openOutputStream(
                    ftpPathResolver.resolvePath(FtpPathResolver.RELATIVE_PATHS_EXPORT_TEMPLATE),
                    FileUtil.resolveFileName(key)
            )) {
                IOUtil.trans(cin, fout, 4096);
            }

            // 根据 uploadInfo 构造 ExportTemplateInfo，插入或更新。
            ExportTemplateInfo exportTemplateInfo = new ExportTemplateInfo();
            exportTemplateInfo.setKey(key);
            exportTemplateInfo.setOriginName(originName);
            exportTemplateInfo.setLength(length);
            exportTemplateInfo.setUploadedDate(new Date());
            exportTemplateInfo.setRemark("通过 templateio 操作处理器上传");
            exportTemplateInfoMaintainService.insertOrUpdate(exportTemplateInfo);
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
            handlerValidator.makeSureExportTemplateInfoExists(key);

            // 如果存在对应的文件，则删除。
            if (ftpHandler.existsFile(
                    ftpPathResolver.resolvePath(FtpPathResolver.RELATIVE_PATHS_EXPORT_TEMPLATE),
                    FileUtil.resolveFileName(key)
            )) {
                ftpHandler.deleteFile(
                        ftpPathResolver.resolvePath(FtpPathResolver.RELATIVE_PATHS_EXPORT_TEMPLATE),
                        FileUtil.resolveFileName(key)
                );
            }

            // 删除模板信息。
            exportTemplateInfoMaintainService.delete(key);
        } catch (HandlerException e) {
            throw e;
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }
}
