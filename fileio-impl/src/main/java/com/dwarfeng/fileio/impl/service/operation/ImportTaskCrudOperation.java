package com.dwarfeng.fileio.impl.service.operation;

import com.dwarfeng.fileio.stack.bean.entity.ImportFileInfo;
import com.dwarfeng.fileio.stack.bean.entity.ImportMetadata;
import com.dwarfeng.fileio.stack.bean.entity.ImportTask;
import com.dwarfeng.fileio.stack.bean.key.TaskItemKey;
import com.dwarfeng.fileio.stack.cache.ImportMetadataCache;
import com.dwarfeng.fileio.stack.cache.ImportTaskCache;
import com.dwarfeng.fileio.stack.dao.ImportFileInfoDao;
import com.dwarfeng.fileio.stack.dao.ImportMetadataDao;
import com.dwarfeng.fileio.stack.dao.ImportTaskDao;
import com.dwarfeng.fileio.stack.service.ImportFileInfoMaintainService;
import com.dwarfeng.fileio.stack.service.ImportMetadataMaintainService;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionCodes;
import com.dwarfeng.subgrade.sdk.service.custom.operation.BatchCrudOperation;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ImportTaskCrudOperation implements BatchCrudOperation<LongIdKey, ImportTask> {

    private final ImportTaskDao importTaskDao;
    private final ImportTaskCache importTaskCache;

    private final ImportFileInfoCrudOperation importFileInfoCrudOperation;
    private final ImportFileInfoDao importFileInfoDao;

    private final ImportMetadataDao importMetadataDao;
    private final ImportMetadataCache importMetadataCache;

    @Value("${com.dwarfeng.fileio.cache.timeout.entity.import_task}")
    private long importTaskTimeout;

    public ImportTaskCrudOperation(
            ImportTaskDao importTaskDao,
            ImportTaskCache importTaskCache,
            ImportFileInfoCrudOperation importFileInfoCrudOperation,
            ImportFileInfoDao importFileInfoDao,
            ImportMetadataDao importMetadataDao,
            ImportMetadataCache importMetadataCache
    ) {
        this.importTaskDao = importTaskDao;
        this.importTaskCache = importTaskCache;
        this.importFileInfoCrudOperation = importFileInfoCrudOperation;
        this.importFileInfoDao = importFileInfoDao;
        this.importMetadataDao = importMetadataDao;
        this.importMetadataCache = importMetadataCache;
    }

    @Override
    public boolean exists(LongIdKey key) throws Exception {
        return importTaskCache.exists(key) || importTaskDao.exists(key);
    }

    @Override
    public ImportTask get(LongIdKey key) throws Exception {
        if (importTaskCache.exists(key)) {
            return importTaskCache.get(key);
        } else {
            if (!importTaskDao.exists(key)) {
                throw new ServiceException(ServiceExceptionCodes.ENTITY_NOT_EXIST);
            }
            ImportTask importTask = importTaskDao.get(key);
            importTaskCache.push(importTask, importTaskTimeout);
            return importTask;
        }
    }

    @Override
    public LongIdKey insert(ImportTask importTask) throws Exception {
        importTaskCache.push(importTask, importTaskTimeout);
        return importTaskDao.insert(importTask);
    }

    @Override
    public void update(ImportTask importTask) throws Exception {
        importTaskCache.push(importTask, importTaskTimeout);
        importTaskDao.update(importTask);
    }

    @Override
    public void delete(LongIdKey key) throws Exception {
        // 删除与导入任务相关的导入文件信息。
        List<TaskItemKey> importFileInfoKeys = importFileInfoDao.lookup(
                ImportFileInfoMaintainService.CHILD_FOR_TASK, new Object[]{key}
        ).stream().map(ImportFileInfo::getKey).collect(Collectors.toList());
        importFileInfoCrudOperation.batchDelete(importFileInfoKeys);

        // 删除与导入任务相关的导入元数据。
        List<TaskItemKey> importMetadataKey = importMetadataDao.lookup(
                ImportMetadataMaintainService.CHILD_FOR_TASK, new Object[]{key}
        ).stream().map(ImportMetadata::getKey).collect(Collectors.toList());
        importMetadataCache.batchDelete(importMetadataKey);
        importMetadataDao.batchDelete(importMetadataKey);

        // 删除导入任务自身。
        importTaskDao.delete(key);
        importTaskCache.delete(key);
    }

    @Override
    public boolean allExists(List<LongIdKey> keys) throws Exception {
        return importTaskCache.allExists(keys) || importTaskDao.allExists(keys);
    }

    @Override
    public boolean nonExists(List<LongIdKey> keys) throws Exception {
        return importTaskCache.nonExists(keys) && importTaskDao.nonExists(keys);
    }

    @Override
    public List<ImportTask> batchGet(List<LongIdKey> keys) throws Exception {
        if (importTaskCache.allExists(keys)) {
            return importTaskCache.batchGet(keys);
        } else {
            if (!importTaskDao.allExists(keys)) {
                throw new ServiceException(ServiceExceptionCodes.ENTITY_NOT_EXIST);
            }
            List<ImportTask> importTasks = importTaskDao.batchGet(keys);
            importTaskCache.batchPush(importTasks, importTaskTimeout);
            return importTasks;
        }
    }

    @Override
    public List<LongIdKey> batchInsert(List<ImportTask> importTasks) throws Exception {
        List<LongIdKey> keys = new ArrayList<>(importTasks.size());
        for (ImportTask importTask : importTasks) {
            keys.add(insert(importTask));
        }
        return keys;
    }

    @Override
    public void batchUpdate(List<ImportTask> importTasks) throws Exception {
        for (ImportTask importTask : importTasks) {
            update(importTask);
        }
    }

    @Override
    public void batchDelete(List<LongIdKey> keys) throws Exception {
        for (LongIdKey key : keys) {
            delete(key);
        }
    }
}
