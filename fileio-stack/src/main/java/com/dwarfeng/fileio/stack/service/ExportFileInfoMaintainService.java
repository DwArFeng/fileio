package com.dwarfeng.fileio.stack.service;

import com.dwarfeng.fileio.stack.bean.entity.ExportFileInfo;
import com.dwarfeng.fileio.stack.bean.key.TaskItemKey;
import com.dwarfeng.subgrade.stack.service.BatchCrudService;
import com.dwarfeng.subgrade.stack.service.EntireLookupService;
import com.dwarfeng.subgrade.stack.service.PresetLookupService;

/**
 * 导出文件信息维护服务。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface ExportFileInfoMaintainService extends BatchCrudService<TaskItemKey, ExportFileInfo>,
        EntireLookupService<ExportFileInfo>, PresetLookupService<ExportFileInfo> {

    String CHILD_FOR_TASK = "child_for_task";
}
