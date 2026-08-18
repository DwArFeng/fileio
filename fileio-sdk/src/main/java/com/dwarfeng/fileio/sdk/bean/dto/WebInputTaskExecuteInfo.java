package com.dwarfeng.fileio.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fileio.stack.bean.dto.TaskExecuteInfo;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputLongIdKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * WebInput 任务执行信息。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public class WebInputTaskExecuteInfo implements Dto {

    private static final long serialVersionUID = -2295039404167897838L;

    public static TaskExecuteInfo toStackBean(WebInputTaskExecuteInfo webInput) {
        if (Objects.isNull(webInput)) {
            return null;
        } else {
            return new TaskExecuteInfo(
                    WebInputLongIdKey.toStackBean(webInput.getTaskKey()),
                    webInput.getMessage()
            );
        }
    }

    @JSONField(name = "task_key")
    @NotNull
    @Valid
    private WebInputLongIdKey taskKey;

    @JSONField(name = "message")
    private String message;

    public WebInputTaskExecuteInfo() {
    }

    public WebInputLongIdKey getTaskKey() {
        return taskKey;
    }

    public void setTaskKey(WebInputLongIdKey taskKey) {
        this.taskKey = taskKey;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "WebInputTaskExecuteInfo{" +
                "taskKey=" + taskKey +
                ", message='" + message + '\'' +
                '}';
    }
}
