package com.dwarfeng.fileio.stack.dao;

import com.dwarfeng.fileio.stack.bean.entity.ImportConf;
import com.dwarfeng.fileio.stack.bean.key.TaskSettingItemKey;
import com.dwarfeng.subgrade.stack.dao.BatchBaseDao;
import com.dwarfeng.subgrade.stack.dao.EntireLookupDao;
import com.dwarfeng.subgrade.stack.dao.PresetLookupDao;

/**
 * 导入配置数据访问层。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface ImportConfDao extends BatchBaseDao<TaskSettingItemKey, ImportConf>, EntireLookupDao<ImportConf>,
        PresetLookupDao<ImportConf> {
}
