package com.dwarfeng.fileio.stack.dao;

import com.dwarfeng.fileio.stack.bean.entity.ExportTask;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.dao.BatchBaseDao;
import com.dwarfeng.subgrade.stack.dao.EntireLookupDao;
import com.dwarfeng.subgrade.stack.dao.PresetLookupDao;

/**
 * 导出任务数据访问层。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface ExportTaskDao extends BatchBaseDao<LongIdKey, ExportTask>, EntireLookupDao<ExportTask>,
        PresetLookupDao<ExportTask> {
}
