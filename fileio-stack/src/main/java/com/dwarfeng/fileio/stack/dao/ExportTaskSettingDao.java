package com.dwarfeng.fileio.stack.dao;

import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.dao.BatchBaseDao;
import com.dwarfeng.subgrade.stack.dao.EntireLookupDao;
import com.dwarfeng.subgrade.stack.dao.PresetLookupDao;
import com.dwarfeng.fileio.stack.bean.entity.ExportTaskSetting;

/**
 * 导出任务设置数据访问层。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface ExportTaskSettingDao extends BatchBaseDao<LongIdKey, ExportTaskSetting>,
        EntireLookupDao<ExportTaskSetting>, PresetLookupDao<ExportTaskSetting> {
}
