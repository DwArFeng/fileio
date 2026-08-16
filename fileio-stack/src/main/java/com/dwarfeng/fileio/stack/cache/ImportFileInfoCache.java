package com.dwarfeng.fileio.stack.cache;

import com.dwarfeng.subgrade.stack.cache.BatchBaseCache;
import com.dwarfeng.fileio.stack.bean.entity.ImportFileInfo;
import com.dwarfeng.fileio.stack.bean.key.TaskItemKey;

/**
 * 导入文件信息缓存。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface ImportFileInfoCache extends BatchBaseCache<TaskItemKey, ImportFileInfo> {
}
