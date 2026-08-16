package com.dwarfeng.fileio.stack.cache;

import com.dwarfeng.fileio.stack.bean.entity.ExporterInfo;
import com.dwarfeng.fileio.stack.bean.key.TaskSettingItemKey;
import com.dwarfeng.subgrade.stack.cache.BatchBaseCache;

/**
 * 导出器信息缓存。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface ExporterInfoCache extends BatchBaseCache<TaskSettingItemKey, ExporterInfo> {
}
