package com.dwarfeng.fileio.stack.bean.key;

import com.dwarfeng.subgrade.stack.bean.key.Key;

import java.util.Objects;

/**
 * 任务项主键。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class TaskItemKey implements Key {

    private static final long serialVersionUID = -2824294666901989453L;

    private Long taskId;
    private String identifier;

    public TaskItemKey() {
    }

    public TaskItemKey(Long taskId, String identifier) {
        this.taskId = taskId;
        this.identifier = identifier;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
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

        TaskItemKey that = (TaskItemKey) o;

        if (!Objects.equals(taskId, that.taskId)) return false;
        return Objects.equals(identifier, that.identifier);
    }

    @Override
    public int hashCode() {
        int result = taskId != null ? taskId.hashCode() : 0;
        result = 31 * result + (identifier != null ? identifier.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TaskItemKey{" +
                "taskId=" + taskId +
                ", identifier='" + identifier + '\'' +
                '}';
    }
}
