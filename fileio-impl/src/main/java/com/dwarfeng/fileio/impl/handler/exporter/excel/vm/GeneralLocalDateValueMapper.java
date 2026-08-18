package com.dwarfeng.fileio.impl.handler.exporter.excel.vm;

import java.time.LocalDate;

/**
 * 通用 LocalDate 值映射器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class GeneralLocalDateValueMapper extends AbstractValueMapper<LocalDate> {

    public static final String VALUE_MAPPER_NAME = "general-local-date";

    public GeneralLocalDateValueMapper() {
        super(VALUE_MAPPER_NAME);
    }

    @Override
    public LocalDate map(Object value) {
        // 如果值为 null，则返回 null 。
        if (value == null) {
            return null;
        }
        // 如果值是 LocalDate，则直接返回。
        if (value instanceof LocalDate) {
            return (LocalDate) value;
        }
        // 如果值是字符串，则尝试将其解析为 LocalDate。
        if (value instanceof String) {
            return LocalDate.parse((String) value);
        }
        // 如果值是数字，则将数字当做时间戳，返回时间戳转换成的 LocalDate。
        if (value instanceof Number) {
            return LocalDate.ofEpochDay(((Number) value).longValue());
        }
        // 如果值是 Date，则返回 Date 转换成的 LocalDate。
        if (value instanceof java.util.Date) {
            return ((java.util.Date) value).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
        }
        // 如果值是 LocalDateTime，则返回 LocalDateTime 转换成的 LocalDate。
        if (value instanceof java.time.LocalDateTime) {
            return ((java.time.LocalDateTime) value).toLocalDate();
        }
        // 如果值是 Calendar，则返回 Calendar 转换成的 LocalDate。
        if (value instanceof java.util.Calendar) {
            return ((java.util.Calendar) value).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
        }
        // 如果值是 Instant，则返回 Instant 转换成的 LocalDate。
        if (value instanceof java.time.Instant) {
            return ((java.time.Instant) value).atZone(java.time.ZoneId.systemDefault()).toLocalDate();
        }
        // 如果值是其他类型，则抛出异常。
        throw new IllegalArgumentException("无法将 " + value + " 转换为 LocalDate, 其类型为 " + value.getClass());
    }
}
