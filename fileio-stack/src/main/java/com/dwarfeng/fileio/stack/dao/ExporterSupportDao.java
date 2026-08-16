package com.dwarfeng.fileio.stack.dao;

import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.dao.BatchBaseDao;
import com.dwarfeng.subgrade.stack.dao.EntireLookupDao;
import com.dwarfeng.subgrade.stack.dao.PresetLookupDao;
import com.dwarfeng.fileio.stack.bean.entity.ExporterSupport;

/**
 * 导出器支持数据访问层。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface ExporterSupportDao extends BatchBaseDao<StringIdKey, ExporterSupport>,
        EntireLookupDao<ExporterSupport>, PresetLookupDao<ExporterSupport> {
}
