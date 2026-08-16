package com.dwarfeng.fileio.sdk.bean.key.formatter;

import com.dwarfeng.fileio.stack.bean.key.TaskSettingItemKey;
import com.dwarfeng.subgrade.sdk.common.Constants;
import com.dwarfeng.subgrade.sdk.redis.formatter.StringKeyFormatter;

import java.util.Objects;

/**
 * TaskSettingItemKey 的文本格式化转换器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class TaskSettingItemStringKeyFormatter implements StringKeyFormatter<TaskSettingItemKey> {

    private String prefix;

    public TaskSettingItemStringKeyFormatter(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public String format(TaskSettingItemKey key) {
        Objects.requireNonNull(key);
        return prefix + key.getTaskSettingId() + "_" + key.getIdentifier();
    }

    @Override
    public String generalFormat() {
        return prefix + Constants.REDIS_KEY_WILDCARD_CHARACTER;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public String toString() {
        return "TaskSettingItemStringKeyFormatter{" +
                "prefix='" + prefix + '\'' +
                '}';
    }
}
