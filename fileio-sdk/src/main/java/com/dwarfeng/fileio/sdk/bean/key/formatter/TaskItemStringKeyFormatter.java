package com.dwarfeng.fileio.sdk.bean.key.formatter;

import com.dwarfeng.fileio.stack.bean.key.TaskItemKey;
import com.dwarfeng.subgrade.sdk.common.Constants;
import com.dwarfeng.subgrade.sdk.redis.formatter.StringKeyFormatter;

import java.util.Objects;

/**
 * TaskItemKey 的文本格式化转换器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class TaskItemStringKeyFormatter implements StringKeyFormatter<TaskItemKey> {

    private String prefix;

    public TaskItemStringKeyFormatter(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public String format(TaskItemKey key) {
        Objects.requireNonNull(key);
        return prefix + key.getTaskId() + "_" + key.getIdentifier();
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
        return "TaskItemStringKeyFormatter{" +
                "prefix='" + prefix + '\'' +
                '}';
    }
}
