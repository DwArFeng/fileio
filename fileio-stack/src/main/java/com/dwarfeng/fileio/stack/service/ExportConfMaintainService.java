package com.dwarfeng.fileio.stack.service;

import com.dwarfeng.fileio.stack.bean.entity.ExportConf;
import com.dwarfeng.fileio.stack.bean.key.TaskSettingItemKey;
import com.dwarfeng.subgrade.stack.service.BatchCrudService;
import com.dwarfeng.subgrade.stack.service.EntireLookupService;
import com.dwarfeng.subgrade.stack.service.PresetLookupService;

/**
 * 导出配置维护服务。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface ExportConfMaintainService extends BatchCrudService<TaskSettingItemKey, ExportConf>,
        EntireLookupService<ExportConf>, PresetLookupService<ExportConf> {

    String CHILD_FOR_TASK_SETTING = "child_for_task_setting";
}
