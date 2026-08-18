package com.dwarfeng.fileio.impl.handler.exporter.excel.vm;

/**
 * 通用 Double 值映射器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class GeneralDoubleValueMapper extends AbstractValueMapper<Double> {

    public static final String VALUE_MAPPER_NAME = "general-double";

    public GeneralDoubleValueMapper() {
        super(VALUE_MAPPER_NAME);
    }

    @Override
    public Double map(Object value) {
        // 如果值为 null，则返回 0 。
        if (value == null) {
            return 0.0;
        }
        // 如果值是 Double，则直接返回。
        if (value instanceof Double) {
            return (Double) value;
        }
        // 如果值是字符串，则尝试将其解析为 Double。
        if (value instanceof String) {
            return Double.parseDouble((String) value);
        }
        // 如果值是数字，则将数字当做 Double，返回 Double。
        if (value instanceof Number) {
            return ((Number) value).doubleValue();
        }
        // 如果值是布尔，则返回布尔转换成的 Double。
        if (value instanceof Boolean) {
            return ((Boolean) value) ? 1.0 : 0.0;
        }
        // 如果值是字符，则返回字符转换成的 Double。
        if (value instanceof Character) {
            return (double) ((Character) value);
        }
        // 如果值是其他类型，则抛出异常。
        throw new IllegalArgumentException("无法将 " + value + " 转换为 Double, 其类型为 " + value.getClass());
    }
}
