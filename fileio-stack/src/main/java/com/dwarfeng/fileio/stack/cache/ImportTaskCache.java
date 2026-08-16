package com.dwarfeng.fileio.stack.cache;

import com.dwarfeng.fileio.stack.bean.entity.ImportTask;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.cache.BatchBaseCache;

/**
 * 导入任务缓存。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface ImportTaskCache extends BatchBaseCache<LongIdKey, ImportTask> {
}
