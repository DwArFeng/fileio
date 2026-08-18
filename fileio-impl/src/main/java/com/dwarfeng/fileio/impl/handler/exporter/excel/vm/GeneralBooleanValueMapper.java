package com.dwarfeng.fileio.impl.handler.exporter.excel.vm;

/**
 * 通用 Boolean 值映射器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class GeneralBooleanValueMapper extends AbstractValueMapper<Boolean> {

    public static final String VALUE_MAPPER_NAME = "general-boolean";

    public GeneralBooleanValueMapper() {
        super(VALUE_MAPPER_NAME);
    }

    @Override
    public Boolean map(Object value) {
        // 如果值为 null，则返回 false 。
        if (value == null) {
            return false;
        }
        // 如果值是 Boolean，则直接返回。
        if (value instanceof Boolean) {
            return (Boolean) value;
        }
        // 如果值是字符串，则尝试将其解析为 Boolean。
        if (value instanceof String) {
            return Boolean.parseBoolean((String) value);
        }
        // 如果值是数字，则将数字当做 Boolean，返回 Boolean。
        if (value instanceof Number) {
            return ((Number) value).doubleValue() != 0;
        }
        // 如果值是字符，则返回字符转换成的 Boolean。
        if (value instanceof Character) {
            return (Character) value != 0;
        }
        // 如果值是其他类型，则抛出异常。
        throw new IllegalArgumentException("无法将 " + value + " 转换为 Boolean, 其类型为 " + value.getClass());
    }
}
