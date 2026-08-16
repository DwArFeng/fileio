package com.dwarfeng.fileio.stack.dao;

import com.dwarfeng.fileio.stack.bean.entity.WriterInfo;
import com.dwarfeng.fileio.stack.bean.key.TaskSettingItemKey;
import com.dwarfeng.subgrade.stack.dao.BatchBaseDao;
import com.dwarfeng.subgrade.stack.dao.EntireLookupDao;
import com.dwarfeng.subgrade.stack.dao.PresetLookupDao;

/**
 * 写入器信息数据访问层。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface WriterInfoDao extends BatchBaseDao<TaskSettingItemKey, WriterInfo>, EntireLookupDao<WriterInfo>,
        PresetLookupDao<WriterInfo> {
}
