package com.dwarfeng.fileio.stack.cache;

import com.dwarfeng.fileio.stack.bean.entity.ImportConf;
import com.dwarfeng.fileio.stack.bean.key.TaskSettingItemKey;
import com.dwarfeng.subgrade.stack.cache.BatchBaseCache;

/**
 * 导入配置缓存。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface ImportConfCache extends BatchBaseCache<TaskSettingItemKey, ImportConf> {
}
