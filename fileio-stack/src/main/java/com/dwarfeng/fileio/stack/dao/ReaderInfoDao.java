package com.dwarfeng.fileio.stack.dao;

import com.dwarfeng.fileio.stack.bean.entity.ReaderInfo;
import com.dwarfeng.fileio.stack.bean.key.TaskSettingItemKey;
import com.dwarfeng.subgrade.stack.dao.BatchBaseDao;
import com.dwarfeng.subgrade.stack.dao.EntireLookupDao;
import com.dwarfeng.subgrade.stack.dao.PresetLookupDao;

/**
 * 读取器信息数据访问层。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface ReaderInfoDao extends BatchBaseDao<TaskSettingItemKey, ReaderInfo>, EntireLookupDao<ReaderInfo>,
        PresetLookupDao<ReaderInfo> {
}
