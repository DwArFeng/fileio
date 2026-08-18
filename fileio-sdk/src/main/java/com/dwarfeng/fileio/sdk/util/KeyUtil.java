package com.dwarfeng.fileio.sdk.util;

import com.dwarfeng.fileio.stack.bean.key.TaskItemKey;
import com.dwarfeng.fileio.stack.bean.key.TaskSettingItemKey;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

import javax.annotation.Nonnull;

/**
 * 主键工具类。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public final class KeyUtil {

    /**
     * 生成任务设置项主键。
     *
     * @param taskSettingKey 任务设置主键。
     * @param identifier     标识符。
     * @return 生成的任务设置项主键。
     */
    public static TaskSettingItemKey newTaskSettingItemKey(
            @Nonnull LongIdKey taskSettingKey, @Nonnull String identifier
    ) {
        return new TaskSettingItemKey(taskSettingKey.getLongId(), identifier);
    }

    /**
     * 生成任务项主键。
     *
     * @param taskSettingKey 任务设置主键。
     * @param identifier     标识符。
     * @return 生成的任务项主键。
     */
    public static TaskItemKey newTaskItemKey(
            @Nonnull LongIdKey taskSettingKey, @Nonnull String identifier
    ) {
        return new TaskItemKey(taskSettingKey.getLongId(), identifier);
    }

    private KeyUtil() {
        throw new IllegalStateException("禁止实例化");
    }
}
