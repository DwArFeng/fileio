package com.dwarfeng.fileio.sdk.bean.key;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fileio.sdk.util.Constraints;
import com.dwarfeng.fileio.stack.bean.key.TaskItemKey;
import com.dwarfeng.subgrade.stack.bean.key.Key;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * WebInput 任务项主键。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class WebInputTaskItemKey implements Key {

    private static final long serialVersionUID = -664822810347291738L;

    public static TaskItemKey toStackBean(WebInputTaskItemKey webInput) {
        if (Objects.isNull(webInput)) {
            return null;
        } else {
            return new TaskItemKey(
                    webInput.getTaskId(),
                    webInput.getIdentifier()
            );
        }
    }

    @JSONField(name = "task_id")
    @NotNull
    private Long taskId;

    @JSONField(name = "identifier")
    @NotNull
    @NotEmpty
    @Length(max = Constraints.LENGTH_IDENTIFIER)
    private String identifier;

    public WebInputTaskItemKey() {
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

        WebInputTaskItemKey that = (WebInputTaskItemKey) o;

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
        return "WebInputTaskItemKey{" +
                "taskId=" + taskId +
                ", identifier='" + identifier + '\'' +
                '}';
    }
}
