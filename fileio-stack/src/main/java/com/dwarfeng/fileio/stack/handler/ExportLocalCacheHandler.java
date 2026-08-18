package com.dwarfeng.fileio.stack.handler;

import com.dwarfeng.fileio.stack.bean.dto.ExportInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.handler.LocalCacheHandler;

/**
 * 导出用本地缓存处理器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface ExportLocalCacheHandler extends LocalCacheHandler<LongIdKey, ExportInfo> {
}
