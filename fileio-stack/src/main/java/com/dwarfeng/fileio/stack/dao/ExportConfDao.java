package com.dwarfeng.fileio.stack.dao;

import com.dwarfeng.fileio.stack.bean.entity.ExportConf;
import com.dwarfeng.fileio.stack.bean.key.TaskSettingItemKey;
import com.dwarfeng.subgrade.stack.dao.BatchBaseDao;
import com.dwarfeng.subgrade.stack.dao.EntireLookupDao;
import com.dwarfeng.subgrade.stack.dao.PresetLookupDao;

/**
 * 导出配置数据访问层。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface ExportConfDao extends BatchBaseDao<TaskSettingItemKey, ExportConf>, EntireLookupDao<ExportConf>,
        PresetLookupDao<ExportConf> {
}
