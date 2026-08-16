package com.dwarfeng.fileio.stack.cache;

import com.dwarfeng.fileio.stack.bean.entity.ImporterSupport;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.cache.BatchBaseCache;

/**
 * 导入器支持缓存。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface ImporterSupportCache extends BatchBaseCache<StringIdKey, ImporterSupport> {
}
