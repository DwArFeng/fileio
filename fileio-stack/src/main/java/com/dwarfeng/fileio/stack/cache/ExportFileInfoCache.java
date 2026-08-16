package com.dwarfeng.fileio.stack.cache;

import com.dwarfeng.subgrade.stack.cache.BatchBaseCache;
import com.dwarfeng.fileio.stack.bean.entity.ExportFileInfo;
import com.dwarfeng.fileio.stack.bean.key.TaskItemKey;

/**
 * 导出文件信息缓存。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface ExportFileInfoCache extends BatchBaseCache<TaskItemKey, ExportFileInfo> {
}
