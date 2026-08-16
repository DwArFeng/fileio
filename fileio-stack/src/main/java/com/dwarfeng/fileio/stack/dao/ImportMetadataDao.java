package com.dwarfeng.fileio.stack.dao;

import com.dwarfeng.fileio.stack.bean.entity.ImportMetadata;
import com.dwarfeng.fileio.stack.bean.key.TaskItemKey;
import com.dwarfeng.subgrade.stack.dao.BatchBaseDao;
import com.dwarfeng.subgrade.stack.dao.EntireLookupDao;
import com.dwarfeng.subgrade.stack.dao.PresetLookupDao;

/**
 * 导入元数据数据访问层。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface ImportMetadataDao extends BatchBaseDao<TaskItemKey, ImportMetadata>, EntireLookupDao<ImportMetadata>,
        PresetLookupDao<ImportMetadata> {
}
