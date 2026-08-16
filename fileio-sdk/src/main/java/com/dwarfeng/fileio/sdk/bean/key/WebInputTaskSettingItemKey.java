package com.dwarfeng.fileio.sdk.bean.key;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fileio.sdk.util.Constraints;
import com.dwarfeng.fileio.stack.bean.key.TaskSettingItemKey;
import com.dwarfeng.subgrade.stack.bean.key.Key;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * WebInput 任务设置项主键。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class WebInputTaskSettingItemKey implements Key {

    private static final long serialVersionUID = -6504105646660593724L;

    public static TaskSettingItemKey toStackBean(WebInputTaskSettingItemKey webInput) {
        if (Objects.isNull(webInput)) {
            return null;
        } else {
            return new TaskSettingItemKey(
                    webInput.getTaskSettingId(),
                    webInput.getIdentifier()
            );
        }
    }

    @JSONField(name = "task_setting_id")
    @NotNull
    private Long taskSettingId;

    @JSONField(name = "identifier")
    @NotNull
    @NotEmpty
    @Length(max = Constraints.LENGTH_IDENTIFIER)
    private String identifier;

    public WebInputTaskSettingItemKey() {
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

        WebInputTaskSettingItemKey that = (WebInputTaskSettingItemKey) o;

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
        return "WebInputTaskSettingItemKey{" +
                "taskSettingId=" + taskSettingId +
                ", identifier='" + identifier + '\'' +
                '}';
    }
}
