package com.dwarfeng.fileio.stack.handler;

import com.dwarfeng.fileio.stack.bean.dto.ImportInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.handler.LocalCacheHandler;

/**
 * 导入用本地缓存处理器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface ImportLocalCacheHandler extends LocalCacheHandler<LongIdKey, ImportInfo> {
}
