package com.dwarfeng.fileio.stack.cache;

import com.dwarfeng.fileio.stack.bean.entity.ExportConf;
import com.dwarfeng.fileio.stack.bean.key.TaskSettingItemKey;
import com.dwarfeng.subgrade.stack.cache.BatchBaseCache;

/**
 * 导出配置缓存。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface ExportConfCache extends BatchBaseCache<TaskSettingItemKey, ExportConf> {
}
