package com.dwarfeng.fileio.stack.service;

import com.dwarfeng.fileio.stack.bean.entity.ImportFileInfo;
import com.dwarfeng.fileio.stack.bean.key.TaskItemKey;
import com.dwarfeng.subgrade.stack.service.BatchCrudService;
import com.dwarfeng.subgrade.stack.service.EntireLookupService;
import com.dwarfeng.subgrade.stack.service.PresetLookupService;

/**
 * 导入文件信息维护服务。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface ImportFileInfoMaintainService extends BatchCrudService<TaskItemKey, ImportFileInfo>,
        EntireLookupService<ImportFileInfo>, PresetLookupService<ImportFileInfo> {

    String CHILD_FOR_TASK = "child_for_task";
}
