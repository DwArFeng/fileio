package com.dwarfeng.fileio.sdk.util;

/**
 * 常量类。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public final class Constants {

    public static final int EXPORT_TASK_STATUS_CREATED = 0;
    public static final int EXPORT_TASK_STATUS_PROCESSING = 1;
    public static final int EXPORT_TASK_STATUS_FINISHED = 2;
    public static final int EXPORT_TASK_STATUS_FAILED = 3;
    public static final int EXPORT_TASK_STATUS_EXPIRED = 4;
    public static final int EXPORT_TASK_STATUS_DEAD = 5;

    public static final int IMPORT_TASK_STATUS_CREATED = 0;
    public static final int IMPORT_TASK_STATUS_PROCESSING = 1;
    public static final int IMPORT_TASK_STATUS_FINISHED = 2;
    public static final int IMPORT_TASK_STATUS_FAILED = 3;
    public static final int IMPORT_TASK_STATUS_EXPIRED = 4;
    public static final int IMPORT_TASK_STATUS_DEAD = 5;

    public static final String DEFAULT_FILE_NAME = "unnamed-file";

    private Constants() {
        throw new IllegalStateException("禁止实例化");
    }
}
