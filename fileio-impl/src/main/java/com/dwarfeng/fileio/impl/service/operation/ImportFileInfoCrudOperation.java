package com.dwarfeng.fileio.impl.service.operation;

import com.dwarfeng.fileio.stack.bean.entity.ImportFileInfo;
import com.dwarfeng.fileio.stack.bean.key.TaskItemKey;
import com.dwarfeng.fileio.stack.cache.ImportFileInfoCache;
import com.dwarfeng.fileio.stack.dao.ImportFileInfoDao;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionCodes;
import com.dwarfeng.subgrade.sdk.service.custom.operation.BatchCrudOperation;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ImportFileInfoCrudOperation implements BatchCrudOperation<TaskItemKey, ImportFileInfo> {

    private final ImportFileInfoDao importFileInfoDao;
    private final ImportFileInfoCache importFileInfoCache;

    @Value("${com.dwarfeng.fileio.cache.timeout.entity.import_file_info}")
    private long importFileInfoTimeout;

    public ImportFileInfoCrudOperation(
            ImportFileInfoDao importFileInfoDao,
            ImportFileInfoCache importFileInfoCache
    ) {
        this.importFileInfoDao = importFileInfoDao;
        this.importFileInfoCache = importFileInfoCache;
    }

    @Override
    public boolean exists(TaskItemKey key) throws Exception {
        return importFileInfoCache.exists(key) || importFileInfoDao.exists(key);
    }

    @Override
    public ImportFileInfo get(TaskItemKey key) throws Exception {
        if (importFileInfoCache.exists(key)) {
            return importFileInfoCache.get(key);
        } else {
            if (!importFileInfoDao.exists(key)) {
                throw new ServiceException(ServiceExceptionCodes.ENTITY_NOT_EXIST);
            }
            ImportFileInfo importFileInfo = importFileInfoDao.get(key);
            importFileInfoCache.push(importFileInfo, importFileInfoTimeout);
            return importFileInfo;
        }
    }

    @Override
    public TaskItemKey insert(ImportFileInfo importFileInfo) throws Exception {
        importFileInfoCache.push(importFileInfo, importFileInfoTimeout);
        return importFileInfoDao.insert(importFileInfo);
    }

    @Override
    public void update(ImportFileInfo importFileInfo) throws Exception {
        importFileInfoCache.push(importFileInfo, importFileInfoTimeout);
        importFileInfoDao.update(importFileInfo);
    }

    @Override
    public void delete(TaskItemKey key) throws Exception {
        // TODO 如果存在票据文件，则删除票据文件。

        // 删除票据文件信息实体自身。
        importFileInfoCache.delete(key);
        importFileInfoDao.delete(key);
    }

    @Override
    public boolean allExists(List<TaskItemKey> keys) throws Exception {
        return importFileInfoCache.allExists(keys) || importFileInfoDao.allExists(keys);
    }

    @Override
    public boolean nonExists(List<TaskItemKey> keys) throws Exception {
        return importFileInfoCache.nonExists(keys) && importFileInfoDao.nonExists(keys);
    }

    @Override
    public List<ImportFileInfo> batchGet(List<TaskItemKey> keys) throws Exception {
        if (importFileInfoCache.allExists(keys)) {
            return importFileInfoCache.batchGet(keys);
        } else {
            if (!importFileInfoDao.allExists(keys)) {
                throw new ServiceException(ServiceExceptionCodes.ENTITY_NOT_EXIST);
            }
            List<ImportFileInfo> importFileInfos = importFileInfoDao.batchGet(keys);
            importFileInfoCache.batchPush(importFileInfos, importFileInfoTimeout);
            return importFileInfos;
        }
    }

    @Override
    public List<TaskItemKey> batchInsert(List<ImportFileInfo> importFileInfos) throws Exception {
        importFileInfoCache.batchPush(importFileInfos, importFileInfoTimeout);
        return importFileInfoDao.batchInsert(importFileInfos);
    }

    @Override
    public void batchUpdate(List<ImportFileInfo> importFileInfos) throws Exception {
        importFileInfoCache.batchPush(importFileInfos, importFileInfoTimeout);
        importFileInfoDao.batchUpdate(importFileInfos);
    }

    @Override
    public void batchDelete(List<TaskItemKey> keys) throws Exception {
        for (TaskItemKey key : keys) {
            delete(key);
        }
    }
}
