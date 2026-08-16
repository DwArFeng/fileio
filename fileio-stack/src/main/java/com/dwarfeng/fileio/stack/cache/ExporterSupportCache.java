package com.dwarfeng.fileio.stack.cache;

import com.dwarfeng.fileio.stack.bean.entity.ExporterSupport;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.cache.BatchBaseCache;

/**
 * 导出器支持缓存。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface ExporterSupportCache extends BatchBaseCache<StringIdKey, ExporterSupport> {
}
