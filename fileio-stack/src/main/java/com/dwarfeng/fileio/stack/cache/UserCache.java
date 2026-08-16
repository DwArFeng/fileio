package com.dwarfeng.fileio.stack.cache;

import com.dwarfeng.fileio.stack.bean.entity.User;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.cache.BatchBaseCache;

/**
 * 用户缓存。
 *
 * @author dwArFeng
 * @since 1.0.0
 */
public interface UserCache extends BatchBaseCache<StringIdKey, User> {
}
