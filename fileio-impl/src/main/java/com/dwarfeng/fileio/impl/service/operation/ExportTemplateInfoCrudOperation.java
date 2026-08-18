package com.dwarfeng.fileio.impl.service.operation;

import com.dwarfeng.fileio.impl.handler.FtpPathResolver;
import com.dwarfeng.fileio.sdk.util.FileUtil;
import com.dwarfeng.fileio.stack.bean.entity.ExportTemplateInfo;
import com.dwarfeng.fileio.stack.bean.key.TaskSettingItemKey;
import com.dwarfeng.fileio.stack.cache.ExportTemplateInfoCache;
import com.dwarfeng.fileio.stack.dao.ExportTemplateInfoDao;
import com.dwarfeng.ftp.stack.handler.FtpHandler;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionCodes;
import com.dwarfeng.subgrade.sdk.service.custom.operation.BatchCrudOperation;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ExportTemplateInfoCrudOperation implements BatchCrudOperation<TaskSettingItemKey, ExportTemplateInfo> {

    private final ExportTemplateInfoDao exportTemplateInfoDao;
    private final ExportTemplateInfoCache exportTemplateInfoCache;

    private final FtpHandler ftpHandler;

    private final FtpPathResolver ftpPathResolver;

    @Value("${com.dwarfeng.fileio.cache.timeout.entity.export_template_info}")
    private long exportTemplateInfoTimeout;

    public ExportTemplateInfoCrudOperation(
            ExportTemplateInfoDao exportTemplateInfoDao,
            ExportTemplateInfoCache exportTemplateInfoCache,
            FtpHandler ftpHandler,
            FtpPathResolver ftpPathResolver
    ) {
        this.exportTemplateInfoDao = exportTemplateInfoDao;
        this.exportTemplateInfoCache = exportTemplateInfoCache;
        this.ftpHandler = ftpHandler;
        this.ftpPathResolver = ftpPathResolver;
    }

    @Override
    public boolean exists(TaskSettingItemKey key) throws Exception {
        return exportTemplateInfoCache.exists(key) || exportTemplateInfoDao.exists(key);
    }

    @Override
    public ExportTemplateInfo get(TaskSettingItemKey key) throws Exception {
        if (exportTemplateInfoCache.exists(key)) {
            return exportTemplateInfoCache.get(key);
        } else {
            if (!exportTemplateInfoDao.exists(key)) {
                throw new ServiceException(ServiceExceptionCodes.ENTITY_NOT_EXIST);
            }
            ExportTemplateInfo exportTemplateInfo = exportTemplateInfoDao.get(key);
            exportTemplateInfoCache.push(exportTemplateInfo, exportTemplateInfoTimeout);
            return exportTemplateInfo;
        }
    }

    @Override
    public TaskSettingItemKey insert(ExportTemplateInfo exportTemplateInfo) throws Exception {
        exportTemplateInfoCache.push(exportTemplateInfo, exportTemplateInfoTimeout);
        return exportTemplateInfoDao.insert(exportTemplateInfo);
    }

    @Override
    public void update(ExportTemplateInfo exportTemplateInfo) throws Exception {
        exportTemplateInfoCache.push(exportTemplateInfo, exportTemplateInfoTimeout);
        exportTemplateInfoDao.update(exportTemplateInfo);
    }

    @Override
    public void delete(TaskSettingItemKey key) throws Exception {
        // 如果存在票据文件，则删除票据文件。
        if (ftpHandler.existsFile(
                ftpPathResolver.resolvePath(FtpPathResolver.RELATIVE_PATHS_EXPORT_TEMPLATE),
                FileUtil.resolveFileName(key)
        )) {
            ftpHandler.deleteFile(
                    ftpPathResolver.resolvePath(FtpPathResolver.RELATIVE_PATHS_EXPORT_TEMPLATE),
                    FileUtil.resolveFileName(key)
            );
        }

        // 删除票据文件信息实体自身。
        exportTemplateInfoCache.delete(key);
        exportTemplateInfoDao.delete(key);
    }

    @Override
    public boolean allExists(List<TaskSettingItemKey> keys) throws Exception {
        return exportTemplateInfoCache.allExists(keys) || exportTemplateInfoDao.allExists(keys);
    }

    @Override
    public boolean nonExists(List<TaskSettingItemKey> keys) throws Exception {
        return exportTemplateInfoCache.nonExists(keys) && exportTemplateInfoDao.nonExists(keys);
    }

    @Override
    public List<ExportTemplateInfo> batchGet(List<TaskSettingItemKey> keys) throws Exception {
        if (exportTemplateInfoCache.allExists(keys)) {
            return exportTemplateInfoCache.batchGet(keys);
        } else {
            if (!exportTemplateInfoDao.allExists(keys)) {
                throw new ServiceException(ServiceExceptionCodes.ENTITY_NOT_EXIST);
            }
            List<ExportTemplateInfo> exportTemplateInfos = exportTemplateInfoDao.batchGet(keys);
            exportTemplateInfoCache.batchPush(exportTemplateInfos, exportTemplateInfoTimeout);
            return exportTemplateInfos;
        }
    }

    @Override
    public List<TaskSettingItemKey> batchInsert(List<ExportTemplateInfo> exportTemplateInfos) throws Exception {
        exportTemplateInfoCache.batchPush(exportTemplateInfos, exportTemplateInfoTimeout);
        return exportTemplateInfoDao.batchInsert(exportTemplateInfos);
    }

    @Override
    public void batchUpdate(List<ExportTemplateInfo> exportTemplateInfos) throws Exception {
        exportTemplateInfoCache.batchPush(exportTemplateInfos, exportTemplateInfoTimeout);
        exportTemplateInfoDao.batchUpdate(exportTemplateInfos);
    }

    @Override
    public void batchDelete(List<TaskSettingItemKey> keys) throws Exception {
        for (TaskSettingItemKey key : keys) {
            delete(key);
        }
    }
}
