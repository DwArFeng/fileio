package com.dwarfeng.fileio.stack.cache;

import com.dwarfeng.fileio.stack.bean.entity.ReaderSupport;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.cache.BatchBaseCache;

/**
 * 读取器支持缓存。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface ReaderSupportCache extends BatchBaseCache<StringIdKey, ReaderSupport> {
}
