package com.dwarfeng.fileio.stack.cache;

import com.dwarfeng.subgrade.stack.cache.BatchBaseCache;
import com.dwarfeng.fileio.stack.bean.entity.WriterInfo;
import com.dwarfeng.fileio.stack.bean.key.TaskSettingItemKey;

/**
 * 写入器信息缓存。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface WriterInfoCache extends BatchBaseCache<TaskSettingItemKey, WriterInfo> {
}
