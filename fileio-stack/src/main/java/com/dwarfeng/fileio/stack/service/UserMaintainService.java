package com.dwarfeng.fileio.stack.service;

import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.service.BatchCrudService;
import com.dwarfeng.subgrade.stack.service.EntireLookupService;
import com.dwarfeng.subgrade.stack.service.PresetLookupService;
import com.dwarfeng.fileio.stack.bean.entity.User;

/**
 * 用户维护服务。
 *
 * @author dwArFeng
 * @since 1.0.0
 */
public interface UserMaintainService extends BatchCrudService<StringIdKey, User>, PresetLookupService<User>,
        EntireLookupService<User> {
}
