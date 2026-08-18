package com.dwarfeng.fileio.impl.handler.exporter.log;

/**
 * 常量类。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public final class Constants {

    public static final String LEVEL_TRACE = "TRACE";
    public static final String LEVEL_DEBUG = "DEBUG";
    public static final String LEVEL_INFO = "INFO";
    public static final String LEVEL_WARN = "WARN";
    public static final String LEVEL_ERROR = "ERROR";

    private Constants() {
        throw new IllegalStateException("禁止实例化");
    }
}
