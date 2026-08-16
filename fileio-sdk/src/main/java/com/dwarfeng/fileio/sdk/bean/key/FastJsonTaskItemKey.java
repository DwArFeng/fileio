package com.dwarfeng.fileio.sdk.bean.key;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.subgrade.stack.bean.key.Key;
import com.dwarfeng.fileio.stack.bean.key.TaskItemKey;

import java.util.Objects;

/**
 * FastJson 任务项主键。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class FastJsonTaskItemKey implements Key {

    private static final long serialVersionUID = 4695264890491854565L;

    public static FastJsonTaskItemKey of(TaskItemKey taskItemKey) {
        if (Objects.isNull(taskItemKey)) {
            return null;
        } else {
            return new FastJsonTaskItemKey(
                    taskItemKey.getTaskId(),
                    taskItemKey.getIdentifier()
            );
        }
    }

    @JSONField(name = "task_id", ordinal = 1)
    private Long taskId;

    @JSONField(name = "identifier", ordinal = 2)
    private String identifier;

    public FastJsonTaskItemKey() {
    }

    public FastJsonTaskItemKey(Long taskId, String identifier) {
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

        FastJsonTaskItemKey that = (FastJsonTaskItemKey) o;

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
        return "FastJsonTaskItemKey{" +
                "taskId=" + taskId +
                ", identifier='" + identifier + '\'' +
                '}';
    }
}
