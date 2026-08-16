package com.dwarfeng.fileio.impl.service.operation;

import com.dwarfeng.fileio.stack.bean.entity.*;
import com.dwarfeng.fileio.stack.bean.key.TaskSettingItemKey;
import com.dwarfeng.fileio.stack.cache.ExportConfCache;
import com.dwarfeng.fileio.stack.cache.ExportTaskSettingCache;
import com.dwarfeng.fileio.stack.cache.ExporterInfoCache;
import com.dwarfeng.fileio.stack.cache.ReaderInfoCache;
import com.dwarfeng.fileio.stack.dao.*;
import com.dwarfeng.fileio.stack.service.*;
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
public class ExportTaskSettingCrudOperation implements BatchCrudOperation<LongIdKey, ExportTaskSetting> {

    private final ExportTaskSettingDao exportTaskSettingDao;
    private final ExportTaskSettingCache exportTaskSettingCache;

    private final ReaderInfoDao readerInfoDao;
    private final ReaderInfoCache readerInfoCache;

    private final ExporterInfoDao exporterInfoDao;
    private final ExporterInfoCache exporterInfoCache;

    private final ExportConfDao exportConfDao;
    private final ExportConfCache exportConfCache;

    private final ExportTemplateInfoCrudOperation exportTemplateInfoCrudOperation;
    private final ExportTemplateInfoDao exportTemplateInfoDao;

    private final ExportTaskCrudOperation exportTaskCrudOperation;
    private final ExportTaskDao exportTaskDao;

    @Value("${com.dwarfeng.fileio.cache.timeout.entity.export_task_setting}")
    private long exportTaskSettingTimeout;

    public ExportTaskSettingCrudOperation(
            ExportTaskSettingDao exportTaskSettingDao,
            ExportTaskSettingCache exportTaskSettingCache,
            ReaderInfoDao readerInfoDao,
            ReaderInfoCache readerInfoCache,
            ExporterInfoDao exporterInfoDao,
            ExporterInfoCache exporterInfoCache,
            ExportConfDao exportConfDao,
            ExportConfCache exportConfCache,
            ExportTemplateInfoCrudOperation exportTemplateInfoCrudOperation,
            ExportTemplateInfoDao exportTemplateInfoDao,
            ExportTaskCrudOperation exportTaskCrudOperation,
            ExportTaskDao exportTaskDao
    ) {
        this.exportTaskSettingDao = exportTaskSettingDao;
        this.exportTaskSettingCache = exportTaskSettingCache;
        this.readerInfoDao = readerInfoDao;
        this.readerInfoCache = readerInfoCache;
        this.exporterInfoDao = exporterInfoDao;
        this.exporterInfoCache = exporterInfoCache;
        this.exportConfDao = exportConfDao;
        this.exportConfCache = exportConfCache;
        this.exportTemplateInfoCrudOperation = exportTemplateInfoCrudOperation;
        this.exportTemplateInfoDao = exportTemplateInfoDao;
        this.exportTaskCrudOperation = exportTaskCrudOperation;
        this.exportTaskDao = exportTaskDao;
    }

    @Override
    public boolean exists(LongIdKey key) throws Exception {
        return exportTaskSettingCache.exists(key) || exportTaskSettingDao.exists(key);
    }

    @Override
    public ExportTaskSetting get(LongIdKey key) throws Exception {
        if (exportTaskSettingCache.exists(key)) {
            return exportTaskSettingCache.get(key);
        } else {
            if (!exportTaskSettingDao.exists(key)) {
                throw new ServiceException(ServiceExceptionCodes.ENTITY_NOT_EXIST);
            }
            ExportTaskSetting exportTaskSetting = exportTaskSettingDao.get(key);
            exportTaskSettingCache.push(exportTaskSetting, exportTaskSettingTimeout);
            return exportTaskSetting;
        }
    }

    @Override
    public LongIdKey insert(ExportTaskSetting exportTaskSetting) throws Exception {
        exportTaskSettingCache.push(exportTaskSetting, exportTaskSettingTimeout);
        return exportTaskSettingDao.insert(exportTaskSetting);
    }

    @Override
    public void update(ExportTaskSetting exportTaskSetting) throws Exception {
        exportTaskSettingCache.push(exportTaskSetting, exportTaskSettingTimeout);
        exportTaskSettingDao.update(exportTaskSetting);
    }

    @Override
    public void delete(LongIdKey key) throws Exception {
        // 删除与导出任务设置相关的阅读器信息。
        List<TaskSettingItemKey> readerInfoKeys = readerInfoDao.lookup(
                ReaderInfoMaintainService.CHILD_FOR_TASK_SETTING, new Object[]{key}
        ).stream().map(ReaderInfo::getKey).collect(Collectors.toList());
        readerInfoCache.batchDelete(readerInfoKeys);
        readerInfoDao.batchDelete(readerInfoKeys);

        // 删除与导出任务设置相关的导出器信息。
        List<TaskSettingItemKey> exporterInfoKeys = exporterInfoDao.lookup(
                ExporterInfoMaintainService.CHILD_FOR_TASK_SETTING, new Object[]{key}
        ).stream().map(ExporterInfo::getKey).collect(Collectors.toList());
        exporterInfoCache.batchDelete(exporterInfoKeys);
        exporterInfoDao.batchDelete(exporterInfoKeys);

        // 删除与导出任务设置相关的导出配置信息。
        List<TaskSettingItemKey> exportConfKeys = exportConfDao.lookup(
                ExportConfMaintainService.CHILD_FOR_TASK_SETTING, new Object[]{key}
        ).stream().map(ExportConf::getKey).collect(Collectors.toList());
        exportConfCache.batchDelete(exportConfKeys);
        exportConfDao.batchDelete(exportConfKeys);

        // 删除与导出任务设置相关的导出模板信息。
        List<TaskSettingItemKey> executorInfoKeys = exportTemplateInfoDao.lookup(
                ExportTemplateInfoMaintainService.CHILD_FOR_TASK_SETTING, new Object[]{key}
        ).stream().map(ExportTemplateInfo::getKey).collect(Collectors.toList());
        exportTemplateInfoCrudOperation.batchDelete(executorInfoKeys);

        // 解除与导出任务设置相关的导出任务信息的关联。
        List<ExportTask> exportTasks = exportTaskDao.lookup(
                ExportTaskMaintainService.CHILD_FOR_TASK_SETTING, new Object[]{key}
        );
        exportTasks.forEach(exportTask -> exportTask.setSettingKey(null));
        exportTaskCrudOperation.batchUpdate(exportTasks);

        // 删除导出任务设置自身。
        exportTaskSettingDao.delete(key);
        exportTaskSettingCache.delete(key);
    }

    @Override
    public boolean allExists(List<LongIdKey> keys) throws Exception {
        return exportTaskSettingCache.allExists(keys) || exportTaskSettingDao.allExists(keys);
    }

    @Override
    public boolean nonExists(List<LongIdKey> keys) throws Exception {
        return exportTaskSettingCache.nonExists(keys) && exportTaskSettingDao.nonExists(keys);
    }

    @Override
    public List<ExportTaskSetting> batchGet(List<LongIdKey> keys) throws Exception {
        if (exportTaskSettingCache.allExists(keys)) {
            return exportTaskSettingCache.batchGet(keys);
        } else {
            if (!exportTaskSettingDao.allExists(keys)) {
                throw new ServiceException(ServiceExceptionCodes.ENTITY_NOT_EXIST);
            }
            List<ExportTaskSetting> exportTaskSettings = exportTaskSettingDao.batchGet(keys);
            exportTaskSettingCache.batchPush(exportTaskSettings, exportTaskSettingTimeout);
            return exportTaskSettings;
        }
    }

    @Override
    public List<LongIdKey> batchInsert(List<ExportTaskSetting> exportTaskSettings) throws Exception {
        List<LongIdKey> keys = new ArrayList<>(exportTaskSettings.size());
        for (ExportTaskSetting exportTaskSetting : exportTaskSettings) {
            keys.add(insert(exportTaskSetting));
        }
        return keys;
    }

    @Override
    public void batchUpdate(List<ExportTaskSetting> exportTaskSettings) throws Exception {
        for (ExportTaskSetting exportTaskSetting : exportTaskSettings) {
            update(exportTaskSetting);
        }
    }

    @Override
    public void batchDelete(List<LongIdKey> keys) throws Exception {
        for (LongIdKey key : keys) {
            delete(key);
        }
    }
}
