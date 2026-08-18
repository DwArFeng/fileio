package com.dwarfeng.fileio.impl.handler.exporter.excel.vm;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

/**
 * 通用日期值映射器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class GeneralDateValueMapper extends AbstractValueMapper<Date> {

    public static final String VALUE_MAPPER_NAME = "general-date";

    public GeneralDateValueMapper() {
        super(VALUE_MAPPER_NAME);
    }

    @Override
    public Date map(Object value) throws Exception {
        // 如果值为 null，则返回 null 。
        if (Objects.isNull(value)) {
            return null;
        }
        // 如果值是 Date，则直接返回。
        if (value instanceof Date) {
            return (Date) value;
        }
        // 如果值是字符串，则尝试将其解析为日期。
        if (value instanceof String) {
            return DateFormat.getInstance().parse((String) value);
        }
        // 如果值是数字，则将数字当做时间戳，返回时间戳转换成的日期。
        if (value instanceof Number) {
            return new Date(((Number) value).longValue());
        }
        // 如果值是 Calendar，则返回 Calendar 转换成的日期。
        if (value instanceof Calendar) {
            return ((Calendar) value).getTime();
        }
        // 如果值是 LocalDateTime ，则返回 LocalDateTime 转换成的日期。
        if (value instanceof LocalDateTime) {
            return Date.from(((LocalDateTime) value).atZone(ZoneId.systemDefault()).toInstant());
        }
        // 如果值是 LocalDate ，则返回 LocalDate 转换成的日期。
        if (value instanceof LocalDate) {
            return Date.from(((LocalDate) value).atStartOfDay(ZoneId.systemDefault()).toInstant());
        }
        // 如果值是 Instant ，则返回 Instant 转换成的日期。
        if (value instanceof java.time.Instant) {
            return Date.from(((java.time.Instant) value));
        }
        // 如果值是其他类型，则抛出异常。
        throw new IllegalArgumentException("无法将 " + value + " 转换为日期, 其类型为 " + value.getClass());
    }
}
