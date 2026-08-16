package com.dwarfeng.fileio.stack.dao;

import com.dwarfeng.fileio.stack.bean.entity.ImportTask;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.dao.BatchBaseDao;
import com.dwarfeng.subgrade.stack.dao.EntireLookupDao;
import com.dwarfeng.subgrade.stack.dao.PresetLookupDao;

/**
 * 导入任务数据访问层。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface ImportTaskDao extends BatchBaseDao<LongIdKey, ImportTask>, EntireLookupDao<ImportTask>,
        PresetLookupDao<ImportTask> {
}
