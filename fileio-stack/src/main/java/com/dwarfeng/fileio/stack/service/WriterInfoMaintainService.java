package com.dwarfeng.fileio.stack.service;

import com.dwarfeng.fileio.stack.bean.entity.WriterInfo;
import com.dwarfeng.fileio.stack.bean.key.TaskSettingItemKey;
import com.dwarfeng.subgrade.stack.service.BatchCrudService;
import com.dwarfeng.subgrade.stack.service.EntireLookupService;
import com.dwarfeng.subgrade.stack.service.PresetLookupService;

/**
 * 写入器信息维护服务。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface WriterInfoMaintainService extends BatchCrudService<TaskSettingItemKey, WriterInfo>,
        EntireLookupService<WriterInfo>, PresetLookupService<WriterInfo> {

    String CHILD_FOR_TASK_SETTING = "child_for_task_setting";
    String CHILD_FOR_TASK_SETTING_ENABLED = "child_for_task_setting_enabled";
}
