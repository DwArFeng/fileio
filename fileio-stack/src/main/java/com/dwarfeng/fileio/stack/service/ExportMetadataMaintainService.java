package com.dwarfeng.fileio.stack.service;

import com.dwarfeng.fileio.stack.bean.entity.ExportMetadata;
import com.dwarfeng.fileio.stack.bean.key.TaskItemKey;
import com.dwarfeng.subgrade.stack.service.BatchCrudService;
import com.dwarfeng.subgrade.stack.service.EntireLookupService;
import com.dwarfeng.subgrade.stack.service.PresetLookupService;

/**
 * 导出元数据维护服务。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface ExportMetadataMaintainService extends BatchCrudService<TaskItemKey, ExportMetadata>,
        EntireLookupService<ExportMetadata>, PresetLookupService<ExportMetadata> {

    String CHILD_FOR_TASK = "child_for_task";
}
