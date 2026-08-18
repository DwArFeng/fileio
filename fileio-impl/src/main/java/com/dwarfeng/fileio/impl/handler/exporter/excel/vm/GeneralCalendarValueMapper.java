package com.dwarfeng.fileio.impl.handler.exporter.excel.vm;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

/**
 * 通用日历值映射器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class GeneralCalendarValueMapper extends AbstractValueMapper<Calendar> {

    public static final String VALUE_MAPPER_NAME = "general-calendar";

    public GeneralCalendarValueMapper() {
        super(VALUE_MAPPER_NAME);
    }

    @Override
    public Calendar map(Object value) throws Exception {
        // 如果值为 null，则返回 null 。
        if (Objects.isNull(value)) {
            return null;
        }
        // 如果值是 Calendar，则直接返回。
        if (value instanceof Calendar) {
            return (Calendar) value;
        }
        // 如果值是字符串，则尝试将其解析为日期，返回日期转换成的 Calendar。
        if (value instanceof String) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(DateFormat.getInstance().parse((String) value));
            return calendar;
        }
        // 如果值是 Date，则返回 Date 转换成的 Calendar。
        if (value instanceof Date) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime((Date) value);
            return calendar;
        }
        // 如果值是数字，则将数字当做时间戳，返回时间戳转换成的 Calendar。
        if (value instanceof Number) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(((Number) value).longValue());
            return calendar;
        }
        // 如果值是 LocalDateTime，则返回 LocalDateTime 转换成的 Calendar。
        if (value instanceof java.time.LocalDateTime) {
            Calendar calendar = Calendar.getInstance();
            long epochMilli = ((LocalDateTime) value).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
            calendar.setTimeInMillis(epochMilli);
            return calendar;
        }
        // 如果值是 LocalDate，则返回 LocalDate 转换成的 Calendar。
        if (value instanceof java.time.LocalDate) {
            Calendar calendar = Calendar.getInstance();
            long epochMilli = ((LocalDate) value).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
            calendar.setTimeInMillis(epochMilli);
            return calendar;
        }
        // 如果值是 Instant，则返回 Instant 转换成的 Calendar。
        if (value instanceof java.time.Instant) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(((java.time.Instant) value).toEpochMilli());
            return calendar;
        }
        // 如果值是其他类型，则抛出异常。
        throw new IllegalArgumentException("无法将 " + value + " 转换为 Calendar, 其类型为 " + value.getClass());
    }
}
