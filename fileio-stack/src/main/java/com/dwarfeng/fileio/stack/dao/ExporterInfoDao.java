package com.dwarfeng.fileio.stack.dao;

import com.dwarfeng.fileio.stack.bean.entity.ExporterInfo;
import com.dwarfeng.fileio.stack.bean.key.TaskSettingItemKey;
import com.dwarfeng.subgrade.stack.dao.BatchBaseDao;
import com.dwarfeng.subgrade.stack.dao.EntireLookupDao;
import com.dwarfeng.subgrade.stack.dao.PresetLookupDao;

/**
 * 导出器信息数据访问层。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface ExporterInfoDao extends BatchBaseDao<TaskSettingItemKey, ExporterInfo>, EntireLookupDao<ExporterInfo>,
        PresetLookupDao<ExporterInfo> {
}
