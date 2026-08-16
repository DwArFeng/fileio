package com.dwarfeng.fileio.stack.service;

import com.dwarfeng.fileio.stack.bean.entity.ImporterSupport;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.service.BatchCrudService;
import com.dwarfeng.subgrade.stack.service.EntireLookupService;
import com.dwarfeng.subgrade.stack.service.PresetLookupService;

/**
 * 导入器支持维护服务。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface ImporterSupportMaintainService extends BatchCrudService<StringIdKey, ImporterSupport>,
        EntireLookupService<ImporterSupport>, PresetLookupService<ImporterSupport> {

    String ID_LIKE = "id_like";
    String LABEL_LIKE = "label_like";
}
