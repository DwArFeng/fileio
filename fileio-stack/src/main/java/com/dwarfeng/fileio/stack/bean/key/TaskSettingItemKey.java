package com.dwarfeng.fileio.stack.bean.key;

import com.dwarfeng.subgrade.stack.bean.key.Key;

import java.util.Objects;

/**
 * 任务设置项主键。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class TaskSettingItemKey implements Key {

    private static final long serialVersionUID = 3908577263305176829L;

    private Long taskSettingId;
    private String identifier;

    public TaskSettingItemKey() {
    }

    public TaskSettingItemKey(Long taskSettingId, String identifier) {
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

        TaskSettingItemKey that = (TaskSettingItemKey) o;

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
        return "TaskSettingItemKey{" +
                "taskSettingId=" + taskSettingId +
                ", identifier='" + identifier + '\'' +
                '}';
    }
}
