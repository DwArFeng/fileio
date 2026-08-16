package com.dwarfeng.fileio.stack.cache;

import com.dwarfeng.fileio.stack.bean.entity.ImporterInfo;
import com.dwarfeng.fileio.stack.bean.key.TaskSettingItemKey;
import com.dwarfeng.subgrade.stack.cache.BatchBaseCache;

/**
 * 导入器信息缓存。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface ImporterInfoCache extends BatchBaseCache<TaskSettingItemKey, ImporterInfo> {
}
