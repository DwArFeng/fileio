package com.dwarfeng.fileio.stack.cache;

import com.dwarfeng.subgrade.stack.cache.BatchBaseCache;
import com.dwarfeng.fileio.stack.bean.entity.ExportTemplateInfo;
import com.dwarfeng.fileio.stack.bean.key.TaskSettingItemKey;

/**
 * 导出模板信息缓存。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface ExportTemplateInfoCache extends BatchBaseCache<TaskSettingItemKey, ExportTemplateInfo> {
}
