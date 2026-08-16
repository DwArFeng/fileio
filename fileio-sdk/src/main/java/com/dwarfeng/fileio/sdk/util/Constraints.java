package com.dwarfeng.fileio.sdk.util;

/**
 * 约束类。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public final class Constraints {

    /**
     * 字符串 ID 的长度约束。
     */
    public static final int LENGTH_STRING_ID = 100;

    /**
     * 标识符的长度约束。
     */
    public static final int LENGTH_IDENTIFIER = 100;

    /**
     * 名称的长度约束。
     */
    public static final int LENGTH_NAME = 50;

    /**
     * 标签的长度约束。
     */
    public static final int LENGTH_LABEL = 50;

    /**
     * 类型的长度约束。
     */
    public static final int LENGTH_TYPE = 50;

    /**
     * 备注的长度约束。
     */
    public static final int LENGTH_REMARK = 200;

    /**
     * 消息的长度约束。
     */
    public static final int LENGTH_MESSAGE = 200;

    private Constraints() {
        throw new IllegalStateException("禁止实例化");
    }
}
