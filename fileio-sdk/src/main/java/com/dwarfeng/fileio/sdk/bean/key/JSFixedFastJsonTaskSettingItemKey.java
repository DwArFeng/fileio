package com.dwarfeng.fileio.sdk.bean.key;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.dwarfeng.fileio.stack.bean.key.TaskSettingItemKey;
import com.dwarfeng.subgrade.stack.bean.key.Key;

import java.util.Objects;

/**
 * JSFixed FastJson 任务设置项主键。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class JSFixedFastJsonTaskSettingItemKey implements Key {

    private static final long serialVersionUID = -4828439775786298472L;

    public static JSFixedFastJsonTaskSettingItemKey of(TaskSettingItemKey taskSettingItemKey) {
        if (Objects.isNull(taskSettingItemKey)) {
            return null;
        } else {
            return new JSFixedFastJsonTaskSettingItemKey(
                    taskSettingItemKey.getTaskSettingId(),
                    taskSettingItemKey.getIdentifier()
            );
        }
    }

    @JSONField(name = "task_setting_id", ordinal = 1, serializeUsing = ToStringSerializer.class)
    private Long taskSettingId;

    @JSONField(name = "identifier", ordinal = 2)
    private String identifier;

    public JSFixedFastJsonTaskSettingItemKey() {
    }

    public JSFixedFastJsonTaskSettingItemKey(Long taskSettingId, String identifier) {
        this.taskSettingId = taskSettingId;
        this.identifier = identifier;
    }

    public Long getTaskSettingId() {
        return taskSettingId;
    }

    public void setTaskSettingId(Long taskSettingId) {
        this.taskSettingId = taskSettingId;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JSFixedFastJsonTaskSettingItemKey that = (JSFixedFastJsonTaskSettingItemKey) o;

        if (!Objects.equals(taskSettingId, that.taskSettingId))
            return false;
        return Objects.equals(identifier, that.identifier);
    }

    @Override
    public int hashCode() {
        int result = taskSettingId != null ? taskSettingId.hashCode() : 0;
        result = 31 * result + (identifier != null ? identifier.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "JSFixedFastJsonTaskSettingItemKey{" +
                "taskSettingId=" + taskSettingId +
                ", identifier='" + identifier + '\'' +
                '}';
    }
}
