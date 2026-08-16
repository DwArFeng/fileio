package com.dwarfeng.fileio.stack.cache;

import com.dwarfeng.fileio.stack.bean.entity.ExportMetadata;
import com.dwarfeng.fileio.stack.bean.key.TaskItemKey;
import com.dwarfeng.subgrade.stack.cache.BatchBaseCache;

/**
 * 导出元数据缓存。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface ExportMetadataCache extends BatchBaseCache<TaskItemKey, ExportMetadata> {
}
