package com.dwarfeng.fileio.stack.service;

import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.service.BatchCrudService;
import com.dwarfeng.subgrade.stack.service.EntireLookupService;
import com.dwarfeng.subgrade.stack.service.PresetLookupService;
import com.dwarfeng.fileio.stack.bean.entity.ExportTaskSetting;

/**
 * 导出任务设置维护服务。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface ExportTaskSettingMaintainService extends BatchCrudService<LongIdKey, ExportTaskSetting>,
        EntireLookupService<ExportTaskSetting>, PresetLookupService<ExportTaskSetting> {
}
