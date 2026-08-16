package com.dwarfeng.fileio.stack.dao;

import com.dwarfeng.fileio.stack.bean.entity.WriterSupport;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.dao.BatchBaseDao;
import com.dwarfeng.subgrade.stack.dao.EntireLookupDao;
import com.dwarfeng.subgrade.stack.dao.PresetLookupDao;

/**
 * 写入器支持数据访问层。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface WriterSupportDao extends BatchBaseDao<StringIdKey, WriterSupport>, EntireLookupDao<WriterSupport>,
        PresetLookupDao<WriterSupport> {
}
