package com.dwarfeng.fileio.sdk.util;

import com.dwarfeng.fileio.stack.bean.key.TaskItemKey;
import com.dwarfeng.fileio.stack.bean.key.TaskSettingItemKey;

/**
 * 文件工具类。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public final class FileUtil {

    public static String resolveFileName(TaskSettingItemKey taskSettingItemKey) {
        return Long.toString(taskSettingItemKey.getTaskSettingId()) + '-' + taskSettingItemKey.getIdentifier();
    }

    public static String resolveFileName(TaskItemKey taskItemKey) {
        return Long.toString(taskItemKey.getTaskId()) + '-' + taskItemKey.getIdentifier();
    }

    private FileUtil() {
        throw new IllegalStateException("禁止实例化");
    }
}
