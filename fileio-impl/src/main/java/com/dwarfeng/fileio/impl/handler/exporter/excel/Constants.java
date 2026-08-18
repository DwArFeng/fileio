package com.dwarfeng.fileio.impl.handler.exporter.excel;

/**
 * @author DwArFeng
 * @since 1.0.0
 */
public final class Constants {

    /**
     * 列类型：布尔。
     */
    public static final int COLUMN_TYPE_BOOLEAN = 0;

    /**
     * 列类型：日期。
     */
    public static final int COLUMN_TYPE_DATE = 1;

    /**
     * 列类型：双精度浮点数。
     */
    public static final int COLUMN_TYPE_DOUBLE = 2;

    /**
     * 列类型：本地日期。
     */
    public static final int COLUMN_TYPE_LOCAL_DATE = 3;

    /**
     * 列类型：本地日期时间。
     */
    public static final int COLUMN_TYPE_LOCAL_DATE_TIME = 4;

    /**
     * 列类型：文本。
     */
    public static final int COLUMN_TYPE_STRING = 5;

    private Constants() {
        throw new IllegalStateException("禁止实例化");
    }
}
