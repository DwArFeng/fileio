package com.dwarfeng.fileio.stack.cache;

import com.dwarfeng.fileio.stack.bean.entity.ExportTask;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.cache.BatchBaseCache;

/**
 * 导出任务缓存。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface ExportTaskCache extends BatchBaseCache<LongIdKey, ExportTask> {
}
