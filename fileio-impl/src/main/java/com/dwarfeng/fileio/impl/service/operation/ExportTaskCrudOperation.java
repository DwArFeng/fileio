package com.dwarfeng.fileio.impl.service.operation;

import com.dwarfeng.fileio.stack.bean.entity.ExportFileInfo;
import com.dwarfeng.fileio.stack.bean.entity.ExportMetadata;
import com.dwarfeng.fileio.stack.bean.entity.ExportTask;
import com.dwarfeng.fileio.stack.bean.key.TaskItemKey;
import com.dwarfeng.fileio.stack.cache.ExportMetadataCache;
import com.dwarfeng.fileio.stack.cache.ExportTaskCache;
import com.dwarfeng.fileio.stack.dao.ExportFileInfoDao;
import com.dwarfeng.fileio.stack.dao.ExportMetadataDao;
import com.dwarfeng.fileio.stack.dao.ExportTaskDao;
import com.dwarfeng.fileio.stack.service.ExportFileInfoMaintainService;
import com.dwarfeng.fileio.stack.service.ExportMetadataMaintainService;
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
public class ExportTaskCrudOperation implements BatchCrudOperation<LongIdKey, ExportTask> {

    private final ExportTaskDao exportTaskDao;
    private final ExportTaskCache exportTaskCache;

    private final ExportFileInfoCrudOperation exportFileInfoCrudOperation;
    private final ExportFileInfoDao exportFileInfoDao;

    private final ExportMetadataDao exportMetadataDao;
    private final ExportMetadataCache exportMetadataCache;

    @Value("${com.dwarfeng.fileio.cache.timeout.entity.export_task}")
    private long exportTaskTimeout;

    public ExportTaskCrudOperation(
            ExportTaskDao exportTaskDao,
            ExportTaskCache exportTaskCache,
            ExportFileInfoCrudOperation exportFileInfoCrudOperation,
            ExportFileInfoDao exportFileInfoDao,
            ExportMetadataDao exportMetadataDao,
            ExportMetadataCache exportMetadataCache
    ) {
        this.exportTaskDao = exportTaskDao;
        this.exportTaskCache = exportTaskCache;
        this.exportFileInfoCrudOperation = exportFileInfoCrudOperation;
        this.exportFileInfoDao = exportFileInfoDao;
        this.exportMetadataDao = exportMetadataDao;
        this.exportMetadataCache = exportMetadataCache;
    }

    @Override
    public boolean exists(LongIdKey key) throws Exception {
        return exportTaskCache.exists(key) || exportTaskDao.exists(key);
    }

    @Override
    public ExportTask get(LongIdKey key) throws Exception {
        if (exportTaskCache.exists(key)) {
            return exportTaskCache.get(key);
        } else {
            if (!exportTaskDao.exists(key)) {
                throw new ServiceException(ServiceExceptionCodes.ENTITY_NOT_EXIST);
            }
            ExportTask exportTask = exportTaskDao.get(key);
            exportTaskCache.push(exportTask, exportTaskTimeout);
            return exportTask;
        }
    }

    @Override
    public LongIdKey insert(ExportTask exportTask) throws Exception {
        exportTaskCache.push(exportTask, exportTaskTimeout);
        return exportTaskDao.insert(exportTask);
    }

    @Override
    public void update(ExportTask exportTask) throws Exception {
        exportTaskCache.push(exportTask, exportTaskTimeout);
        exportTaskDao.update(exportTask);
    }

    @Override
    public void delete(LongIdKey key) throws Exception {
        // 删除与导出任务相关的导出文件信息。
        List<TaskItemKey> exportFileInfoKeys = exportFileInfoDao.lookup(
                ExportFileInfoMaintainService.CHILD_FOR_TASK, new Object[]{key}
        ).stream().map(ExportFileInfo::getKey).collect(Collectors.toList());
        exportFileInfoCrudOperation.batchDelete(exportFileInfoKeys);

        // 删除与导出任务相关的导出元数据。
        List<TaskItemKey> exportMetadataKey = exportMetadataDao.lookup(
                ExportMetadataMaintainService.CHILD_FOR_TASK, new Object[]{key}
        ).stream().map(ExportMetadata::getKey).collect(Collectors.toList());
        exportMetadataCache.batchDelete(exportMetadataKey);
        exportMetadataDao.batchDelete(exportMetadataKey);

        // 删除导出任务自身。
        exportTaskDao.delete(key);
        exportTaskCache.delete(key);
    }

    @Override
    public boolean allExists(List<LongIdKey> keys) throws Exception {
        return exportTaskCache.allExists(keys) || exportTaskDao.allExists(keys);
    }

    @Override
    public boolean nonExists(List<LongIdKey> keys) throws Exception {
        return exportTaskCache.nonExists(keys) && exportTaskDao.nonExists(keys);
    }

    @Override
    public List<ExportTask> batchGet(List<LongIdKey> keys) throws Exception {
        if (exportTaskCache.allExists(keys)) {
            return exportTaskCache.batchGet(keys);
        } else {
            if (!exportTaskDao.allExists(keys)) {
                throw new ServiceException(ServiceExceptionCodes.ENTITY_NOT_EXIST);
            }
            List<ExportTask> exportTasks = exportTaskDao.batchGet(keys);
            exportTaskCache.batchPush(exportTasks, exportTaskTimeout);
            return exportTasks;
        }
    }

    @Override
    public List<LongIdKey> batchInsert(List<ExportTask> exportTasks) throws Exception {
        List<LongIdKey> keys = new ArrayList<>(exportTasks.size());
        for (ExportTask exportTask : exportTasks) {
            keys.add(insert(exportTask));
        }
        return keys;
    }

    @Override
    public void batchUpdate(List<ExportTask> exportTasks) throws Exception {
        for (ExportTask exportTask : exportTasks) {
            update(exportTask);
        }
    }

    @Override
    public void batchDelete(List<LongIdKey> keys) throws Exception {
        for (LongIdKey key : keys) {
            delete(key);
        }
    }
}
