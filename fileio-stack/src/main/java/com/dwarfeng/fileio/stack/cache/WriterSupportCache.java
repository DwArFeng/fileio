package com.dwarfeng.fileio.stack.cache;

import com.dwarfeng.fileio.stack.bean.entity.WriterSupport;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.cache.BatchBaseCache;

/**
 * 写入器支持缓存。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface WriterSupportCache extends BatchBaseCache<StringIdKey, WriterSupport> {
}
