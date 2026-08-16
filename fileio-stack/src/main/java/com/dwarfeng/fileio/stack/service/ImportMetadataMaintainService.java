package com.dwarfeng.fileio.stack.service;

import com.dwarfeng.fileio.stack.bean.entity.ImportMetadata;
import com.dwarfeng.fileio.stack.bean.key.TaskItemKey;
import com.dwarfeng.subgrade.stack.service.BatchCrudService;
import com.dwarfeng.subgrade.stack.service.EntireLookupService;
import com.dwarfeng.subgrade.stack.service.PresetLookupService;

/**
 * 导入元数据维护服务。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface ImportMetadataMaintainService extends BatchCrudService<TaskItemKey, ImportMetadata>,
        EntireLookupService<ImportMetadata>, PresetLookupService<ImportMetadata> {

    String CHILD_FOR_TASK = "child_for_task";
}
