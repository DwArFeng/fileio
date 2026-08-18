package com.dwarfeng.fileio.impl.handler.exporter.excel.vm;

import com.dwarfeng.subgrade.stack.bean.key.IntegerIdKey;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;

/**
 * 通用字符串值映射器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class GeneralStringValueMapper extends AbstractValueMapper<String> {

    public static final String VALUE_MAPPER_NAME = "general-string";

    public GeneralStringValueMapper() {
        super(VALUE_MAPPER_NAME);
    }

    // 为了代码的可读性，此处不做简化。
    @SuppressWarnings("RedundantCast")
    @Override
    public String map(Object value) {
        // 如果值为 null，则返回 null 。
        if (Objects.isNull(value)) {
            return null;
        }
        // 如果值为字符串，则直接返回。
        if (value instanceof String) {
            return (String) value;
        }
        // 如果值是 StringIdKey，则返回 StringIdKey 的 stringId。
        if (value instanceof StringIdKey) {
            return ((StringIdKey) value).getStringId();
        }
        // 如果值是 LongIdKey，则返回 LongIdKey 的 longId 的字符串形式。
        if (value instanceof LongIdKey) {
            return Long.toString(((LongIdKey) value).getLongId());
        }
        // 如果值是 IntegerIdKey，则返回 IntegerIdKey 的 integerId 的字符串形式。
        if (value instanceof IntegerIdKey) {
            return Integer.toString(((IntegerIdKey) value).getIntegerId());
        }
        // 如果值是数字，则返回数字的字符串形式。
        if (value instanceof Byte) {
            return Byte.toString((Byte) value);
        } else if (value instanceof Short) {
            return Short.toString((Short) value);
        } else if (value instanceof Integer) {
            return Integer.toString((Integer) value);
        } else if (value instanceof Long) {
            return Long.toString((Long) value);
        } else if (value instanceof Float) {
            return Float.toString((Float) value);
        } else if (value instanceof Double) {
            return Double.toString((Double) value);
        }
        // 如果值是 BigDecimal，则返回 BigDecimal 的字符串形式。
        if (value instanceof BigDecimal) {
            return ((BigDecimal) value).toPlainString();
        }
        // 如果值是 BigInteger，则返回 BigInteger 的字符串形式。
        if (value instanceof BigInteger) {
            return ((BigInteger) value).toString();
        }
        // 如果值是 Character，则返回 Character 的字符串形式。
        if (value instanceof Character) {
            return Character.toString((Character) value);
        }
        // 如果值是 Date，则返回 Date 的字符串形式。
        if (value instanceof Date) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sdf.format((Date) value);
        }
        // 如果值是 LocalDate，则返回 LocalDate 的格式化字符串形式。
        if (value instanceof java.time.LocalDate) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return ((java.time.LocalDate) value).format(formatter);
        }
        // 如果值是 LocalDateTime，则返回 LocalDateTime 的格式化字符串形式。
        if (value instanceof java.time.LocalDateTime) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            return ((java.time.LocalDateTime) value).format(formatter);
        }
        // 如果值是 Instant，则返回 Instant 的格式化字符串形式。
        if (value instanceof java.time.Instant) {
            DateTimeFormatter formatter =
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.systemDefault());
            return formatter.format((java.time.Instant) value);
        }
        // 如果值是 Calendar，则返回 Calendar 的格式化字符串形式。
        if (value instanceof java.util.Calendar) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sdf.format(((java.util.Calendar) value).getTime());
        }
        // 如果值是 Boolean，则返回 Boolean 的字符串形式。
        if (value instanceof Boolean) {
            return Boolean.toString((Boolean) value);
        }
        // 如果值是其他类型，则抛出异常。
        throw new IllegalArgumentException("无法将 " + value + " 转换为字符串, 其类型为 " + value.getClass());
    }
}
