package com.dwarfeng.fileio.impl.handler.exporter.excel.vm;

import java.time.LocalDateTime;

/**
 * 通用 LocalDateTime 值映射器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class GeneralLocalDateTimeValueMapper extends AbstractValueMapper<LocalDateTime> {

    public static final String VALUE_MAPPER_NAME = "general-local-date-time";

    public GeneralLocalDateTimeValueMapper() {
        super(VALUE_MAPPER_NAME);
    }

    @Override
    public LocalDateTime map(Object value) {
        // 如果值为 null，则返回 null 。
        if (value == null) {
            return null;
        }
        // 如果值是 LocalDateTime，则直接返回。
        if (value instanceof LocalDateTime) {
            return (LocalDateTime) value;
        }
        // 如果值是字符串，则尝试将其解析为 LocalDateTime。
        if (value instanceof String) {
            return LocalDateTime.parse((String) value);
        }
        // 如果值是数字，则将数字当做时间戳，返回时间戳转换成的 LocalDateTime。
        if (value instanceof Number) {
            return LocalDateTime.ofEpochSecond(((Number) value).longValue(), 0, java.time.ZoneOffset.UTC);
        }
        // 如果值是 Date，则返回 Date 转换成的 LocalDateTime。
        if (value instanceof java.util.Date) {
            return ((java.util.Date) value).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime();
        }
        // 如果值是 LocalDate，则返回 LocalDate 转换成的 LocalDateTime。
        if (value instanceof java.time.LocalDate) {
            return ((java.time.LocalDate) value).atStartOfDay();
        }
        // 如果值是 Calendar，则返回 Calendar 转换成的 LocalDateTime。
        if (value instanceof java.util.Calendar) {
            return ((java.util.Calendar) value).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime();
        }
        // 如果值是 Instant，则返回 Instant 转换成的 LocalDateTime。
        if (value instanceof java.time.Instant) {
            return ((java.time.Instant) value).atZone(java.time.ZoneId.systemDefault()).toLocalDateTime();
        }
        // 如果值是其他类型，则抛出异常。
        throw new IllegalArgumentException("无法将 " + value + " 转换为 LocalDateTime, 其类型为 " + value.getClass());
    }
}
