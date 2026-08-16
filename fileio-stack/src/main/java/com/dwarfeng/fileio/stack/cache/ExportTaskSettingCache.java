package com.dwarfeng.fileio.stack.cache;

import com.dwarfeng.fileio.stack.bean.entity.ExportTaskSetting;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.cache.BatchBaseCache;

/**
 * 导出任务设置缓存。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface ExportTaskSettingCache extends BatchBaseCache<LongIdKey, ExportTaskSetting> {
}
