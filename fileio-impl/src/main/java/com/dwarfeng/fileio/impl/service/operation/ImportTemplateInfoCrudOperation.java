package com.dwarfeng.fileio.impl.service.operation;

import com.dwarfeng.fileio.impl.handler.FtpPathResolver;
import com.dwarfeng.fileio.sdk.util.FileUtil;
import com.dwarfeng.fileio.stack.bean.entity.ImportTemplateInfo;
import com.dwarfeng.fileio.stack.bean.key.TaskSettingItemKey;
import com.dwarfeng.fileio.stack.cache.ImportTemplateInfoCache;
import com.dwarfeng.fileio.stack.dao.ImportTemplateInfoDao;
import com.dwarfeng.ftp.stack.handler.FtpHandler;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionCodes;
import com.dwarfeng.subgrade.sdk.service.custom.operation.BatchCrudOperation;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ImportTemplateInfoCrudOperation implements BatchCrudOperation<TaskSettingItemKey, ImportTemplateInfo> {

    private final ImportTemplateInfoDao importTemplateInfoDao;
    private final ImportTemplateInfoCache importTemplateInfoCache;

    private final FtpHandler ftpHandler;

    private final FtpPathResolver ftpPathResolver;

    @Value("${com.dwarfeng.fileio.cache.timeout.entity.import_template_info}")
    private long importTemplateInfoTimeout;

    public ImportTemplateInfoCrudOperation(
            ImportTemplateInfoDao importTemplateInfoDao,
            ImportTemplateInfoCache importTemplateInfoCache,
            FtpHandler ftpHandler,
            FtpPathResolver ftpPathResolver
    ) {
        this.importTemplateInfoDao = importTemplateInfoDao;
        this.importTemplateInfoCache = importTemplateInfoCache;
        this.ftpHandler = ftpHandler;
        this.ftpPathResolver = ftpPathResolver;
    }

    @Override
    public boolean exists(TaskSettingItemKey key) throws Exception {
        return importTemplateInfoCache.exists(key) || importTemplateInfoDao.exists(key);
    }

    @Override
    public ImportTemplateInfo get(TaskSettingItemKey key) throws Exception {
        if (importTemplateInfoCache.exists(key)) {
            return importTemplateInfoCache.get(key);
        } else {
            if (!importTemplateInfoDao.exists(key)) {
                throw new ServiceException(ServiceExceptionCodes.ENTITY_NOT_EXIST);
            }
            ImportTemplateInfo importTemplateInfo = importTemplateInfoDao.get(key);
            importTemplateInfoCache.push(importTemplateInfo, importTemplateInfoTimeout);
            return importTemplateInfo;
        }
    }

    @Override
    public TaskSettingItemKey insert(ImportTemplateInfo importTemplateInfo) throws Exception {
        importTemplateInfoCache.push(importTemplateInfo, importTemplateInfoTimeout);
        return importTemplateInfoDao.insert(importTemplateInfo);
    }

    @Override
    public void update(ImportTemplateInfo importTemplateInfo) throws Exception {
        importTemplateInfoCache.push(importTemplateInfo, importTemplateInfoTimeout);
        importTemplateInfoDao.update(importTemplateInfo);
    }

    @Override
    public void delete(TaskSettingItemKey key) throws Exception {
        // 如果存在票据文件，则删除票据文件。
        if (ftpHandler.existsFile(
                ftpPathResolver.resolvePath(FtpPathResolver.RELATIVE_PATHS_IMPORT_TEMPLATE),
                FileUtil.resolveFileName(key)
        )) {
            ftpHandler.deleteFile(
                    ftpPathResolver.resolvePath(FtpPathResolver.RELATIVE_PATHS_IMPORT_TEMPLATE),
                    FileUtil.resolveFileName(key)
            );
        }

        // 删除票据文件信息实体自身。
        importTemplateInfoCache.delete(key);
        importTemplateInfoDao.delete(key);
    }

    @Override
    public boolean allExists(List<TaskSettingItemKey> keys) throws Exception {
        return importTemplateInfoCache.allExists(keys) || importTemplateInfoDao.allExists(keys);
    }

    @Override
    public boolean nonExists(List<TaskSettingItemKey> keys) throws Exception {
        return importTemplateInfoCache.nonExists(keys) && importTemplateInfoDao.nonExists(keys);
    }

    @Override
    public List<ImportTemplateInfo> batchGet(List<TaskSettingItemKey> keys) throws Exception {
        if (importTemplateInfoCache.allExists(keys)) {
            return importTemplateInfoCache.batchGet(keys);
        } else {
            if (!importTemplateInfoDao.allExists(keys)) {
                throw new ServiceException(ServiceExceptionCodes.ENTITY_NOT_EXIST);
            }
            List<ImportTemplateInfo> importTemplateInfos = importTemplateInfoDao.batchGet(keys);
            importTemplateInfoCache.batchPush(importTemplateInfos, importTemplateInfoTimeout);
            return importTemplateInfos;
        }
    }

    @Override
    public List<TaskSettingItemKey> batchInsert(List<ImportTemplateInfo> importTemplateInfos) throws Exception {
        importTemplateInfoCache.batchPush(importTemplateInfos, importTemplateInfoTimeout);
        return importTemplateInfoDao.batchInsert(importTemplateInfos);
    }

    @Override
    public void batchUpdate(List<ImportTemplateInfo> importTemplateInfos) throws Exception {
        importTemplateInfoCache.batchPush(importTemplateInfos, importTemplateInfoTimeout);
        importTemplateInfoDao.batchUpdate(importTemplateInfos);
    }

    @Override
    public void batchDelete(List<TaskSettingItemKey> keys) throws Exception {
        for (TaskSettingItemKey key : keys) {
            delete(key);
        }
    }
}
