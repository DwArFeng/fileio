package com.dwarfeng.fileio.stack.cache;

import com.dwarfeng.subgrade.stack.cache.BatchBaseCache;
import com.dwarfeng.fileio.stack.bean.entity.ImportTemplateInfo;
import com.dwarfeng.fileio.stack.bean.key.TaskSettingItemKey;

/**
 * 导入模板信息缓存。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface ImportTemplateInfoCache extends BatchBaseCache<TaskSettingItemKey, ImportTemplateInfo> {
}
