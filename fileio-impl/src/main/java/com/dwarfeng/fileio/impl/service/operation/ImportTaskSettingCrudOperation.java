package com.dwarfeng.fileio.impl.service.operation;

import com.dwarfeng.fileio.stack.bean.entity.*;
import com.dwarfeng.fileio.stack.bean.key.TaskSettingItemKey;
import com.dwarfeng.fileio.stack.cache.ImportConfCache;
import com.dwarfeng.fileio.stack.cache.ImportTaskSettingCache;
import com.dwarfeng.fileio.stack.cache.ImporterInfoCache;
import com.dwarfeng.fileio.stack.cache.WriterInfoCache;
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
public class ImportTaskSettingCrudOperation implements BatchCrudOperation<LongIdKey, ImportTaskSetting> {

    private final ImportTaskSettingDao importTaskSettingDao;
    private final ImportTaskSettingCache importTaskSettingCache;

    private final WriterInfoDao writerInfoDao;
    private final WriterInfoCache writerInfoCache;

    private final ImporterInfoDao importerInfoDao;
    private final ImporterInfoCache importerInfoCache;

    private final ImportConfDao importConfDao;
    private final ImportConfCache importConfCache;

    private final ImportTemplateInfoCrudOperation importTemplateInfoCrudOperation;
    private final ImportTemplateInfoDao importTemplateInfoDao;

    private final ImportTaskCrudOperation importTaskCrudOperation;
    private final ImportTaskDao importTaskDao;

    @Value("${com.dwarfeng.fileio.cache.timeout.entity.import_task_setting}")
    private long importTaskSettingTimeout;

    public ImportTaskSettingCrudOperation(
            ImportTaskSettingDao importTaskSettingDao,
            ImportTaskSettingCache importTaskSettingCache,
            WriterInfoDao writerInfoDao,
            WriterInfoCache writerInfoCache,
            ImporterInfoDao importerInfoDao,
            ImporterInfoCache importerInfoCache,
            ImportConfDao importConfDao,
            ImportConfCache importConfCache,
            ImportTemplateInfoCrudOperation importTemplateInfoCrudOperation,
            ImportTemplateInfoDao importTemplateInfoDao,
            ImportTaskCrudOperation importTaskCrudOperation,
            ImportTaskDao importTaskDao
    ) {
        this.importTaskSettingDao = importTaskSettingDao;
        this.importTaskSettingCache = importTaskSettingCache;
        this.writerInfoDao = writerInfoDao;
        this.writerInfoCache = writerInfoCache;
        this.importerInfoDao = importerInfoDao;
        this.importerInfoCache = importerInfoCache;
        this.importConfDao = importConfDao;
        this.importConfCache = importConfCache;
        this.importTemplateInfoCrudOperation = importTemplateInfoCrudOperation;
        this.importTemplateInfoDao = importTemplateInfoDao;
        this.importTaskCrudOperation = importTaskCrudOperation;
        this.importTaskDao = importTaskDao;
    }

    @Override
    public boolean exists(LongIdKey key) throws Exception {
        return importTaskSettingCache.exists(key) || importTaskSettingDao.exists(key);
    }

    @Override
    public ImportTaskSetting get(LongIdKey key) throws Exception {
        if (importTaskSettingCache.exists(key)) {
            return importTaskSettingCache.get(key);
        } else {
            if (!importTaskSettingDao.exists(key)) {
                throw new ServiceException(ServiceExceptionCodes.ENTITY_NOT_EXIST);
            }
            ImportTaskSetting importTaskSetting = importTaskSettingDao.get(key);
            importTaskSettingCache.push(importTaskSetting, importTaskSettingTimeout);
            return importTaskSetting;
        }
    }

    @Override
    public LongIdKey insert(ImportTaskSetting importTaskSetting) throws Exception {
        importTaskSettingCache.push(importTaskSetting, importTaskSettingTimeout);
        return importTaskSettingDao.insert(importTaskSetting);
    }

    @Override
    public void update(ImportTaskSetting importTaskSetting) throws Exception {
        importTaskSettingCache.push(importTaskSetting, importTaskSettingTimeout);
        importTaskSettingDao.update(importTaskSetting);
    }

    @Override
    public void delete(LongIdKey key) throws Exception {
        // 删除与导入任务设置相关的阅读器信息。
        List<TaskSettingItemKey> writerInfoKeys = writerInfoDao.lookup(
                WriterInfoMaintainService.CHILD_FOR_TASK_SETTING, new Object[]{key}
        ).stream().map(WriterInfo::getKey).collect(Collectors.toList());
        writerInfoCache.batchDelete(writerInfoKeys);
        writerInfoDao.batchDelete(writerInfoKeys);

        // 删除与导入任务设置相关的导入器信息。
        List<TaskSettingItemKey> importerInfoKeys = importerInfoDao.lookup(
                ImporterInfoMaintainService.CHILD_FOR_TASK_SETTING, new Object[]{key}
        ).stream().map(ImporterInfo::getKey).collect(Collectors.toList());
        importerInfoCache.batchDelete(importerInfoKeys);
        importerInfoDao.batchDelete(importerInfoKeys);

        // 删除与导入任务设置相关的导入配置信息。
        List<TaskSettingItemKey> importConfKeys = importConfDao.lookup(
                ImportConfMaintainService.CHILD_FOR_TASK_SETTING, new Object[]{key}
        ).stream().map(ImportConf::getKey).collect(Collectors.toList());
        importConfCache.batchDelete(importConfKeys);
        importConfDao.batchDelete(importConfKeys);

        // 删除与导入任务设置相关的导入模板信息。
        List<TaskSettingItemKey> executorInfoKeys = importTemplateInfoDao.lookup(
                ImportTemplateInfoMaintainService.CHILD_FOR_TASK_SETTING, new Object[]{key}
        ).stream().map(ImportTemplateInfo::getKey).collect(Collectors.toList());
        importTemplateInfoCrudOperation.batchDelete(executorInfoKeys);

        // 解除与导出任务设置相关的导出任务信息的关联。
        List<ImportTask> importTasks = importTaskDao.lookup(
                ImportTaskMaintainService.CHILD_FOR_TASK_SETTING, new Object[]{key}
        );
        importTasks.forEach(importTask -> importTask.setSettingKey(null));
        importTaskCrudOperation.batchUpdate(importTasks);

        // 删除导入任务设置自身。
        importTaskSettingDao.delete(key);
        importTaskSettingCache.delete(key);
    }

    @Override
    public boolean allExists(List<LongIdKey> keys) throws Exception {
        return importTaskSettingCache.allExists(keys) || importTaskSettingDao.allExists(keys);
    }

    @Override
    public boolean nonExists(List<LongIdKey> keys) throws Exception {
        return importTaskSettingCache.nonExists(keys) && importTaskSettingDao.nonExists(keys);
    }

    @Override
    public List<ImportTaskSetting> batchGet(List<LongIdKey> keys) throws Exception {
        if (importTaskSettingCache.allExists(keys)) {
            return importTaskSettingCache.batchGet(keys);
        } else {
            if (!importTaskSettingDao.allExists(keys)) {
                throw new ServiceException(ServiceExceptionCodes.ENTITY_NOT_EXIST);
            }
            List<ImportTaskSetting> importTaskSettings = importTaskSettingDao.batchGet(keys);
            importTaskSettingCache.batchPush(importTaskSettings, importTaskSettingTimeout);
            return importTaskSettings;
        }
    }

    @Override
    public List<LongIdKey> batchInsert(List<ImportTaskSetting> importTaskSettings) throws Exception {
        List<LongIdKey> keys = new ArrayList<>(importTaskSettings.size());
        for (ImportTaskSetting importTaskSetting : importTaskSettings) {
            keys.add(insert(importTaskSetting));
        }
        return keys;
    }

    @Override
    public void batchUpdate(List<ImportTaskSetting> importTaskSettings) throws Exception {
        for (ImportTaskSetting importTaskSetting : importTaskSettings) {
            update(importTaskSetting);
        }
    }

    @Override
    public void batchDelete(List<LongIdKey> keys) throws Exception {
        for (LongIdKey key : keys) {
            delete(key);
        }
    }
}
