package com.dwarfeng.fileio.impl.handler.reader.mock;

import java.lang.reflect.Method;
import java.util.*;

/**
 * 字段类型工具类。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public final class FieldTypeUtil {

    /**
     * 有效的字段类型列表。
     */
    public static final List<String> VALID_FIELD_TYPES;

    private static final Map<String, Method> GENERATOR_FIELD_TYPE_METHOD_MAP;

    static {
        // 定义生成器字段类型方法映射。
        Map<String, Method> generatorFieldTypeMethodMap = new HashMap<>();
        // 扫描 RandomGenerator 中所有方法。
        // 获取其中包含 FieldTypeMarker 注解的方法。
        // 获取注解中的 value 值，并将其与方法映射。
        for (Method method : RandomGenerator.class.getMethods()) {
            if (!method.isAnnotationPresent(FieldTypeMarker.class)) {
                continue;
            }
            FieldTypeMarker fieldTypeMarker = method.getAnnotation(FieldTypeMarker.class);
            generatorFieldTypeMethodMap.put(fieldTypeMarker.value(), method);
        }
        // 初始化有效字段类型列表。
        List<String> validFieldTypes = new ArrayList<>(generatorFieldTypeMethodMap.keySet());
        VALID_FIELD_TYPES = Collections.unmodifiableList(validFieldTypes);
        // 初始化生成器字段类型方法映射。
        GENERATOR_FIELD_TYPE_METHOD_MAP = Collections.unmodifiableMap(generatorFieldTypeMethodMap);
    }

    /**
     * 生成下一个随机值。
     *
     * @param generator 生成器。
     * @param fieldType 字段类型。
     * @return 下一个随机值。
     */
    public static Object generateNextRandomValue(RandomGenerator generator, String fieldType) {
        // 从 GENERATOR_FIELD_TYPE_METHOD_MAP 中获取 fieldType 对应的方法。
        Method method = GENERATOR_FIELD_TYPE_METHOD_MAP.get(fieldType);
        // 如果方法为 null，则抛出异常。
        if (method == null) {
            throw new IllegalArgumentException("未知的字段类型: " + fieldType);
        }
        // 调用方法，生成随机值。
        try {
            return method.invoke(generator);
        } catch (Exception e) {
            throw new IllegalStateException("生成随机值时发生异常", e);
        }
    }

    private FieldTypeUtil() {
        throw new IllegalStateException("禁止实例化");
    }
}
