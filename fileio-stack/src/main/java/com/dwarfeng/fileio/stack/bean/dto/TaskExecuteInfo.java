package com.dwarfeng.fileio.stack.bean.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

/**
 * 任务执行信息。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public class TaskExecuteInfo implements Dto {

    private static final long serialVersionUID = -42162158602348748L;

    private LongIdKey taskKey;
    private String message;

    public TaskExecuteInfo() {
    }

    public TaskExecuteInfo(LongIdKey taskKey, String message) {
        this.taskKey = taskKey;
        this.message = message;
    }

    public LongIdKey getTaskKey() {
        return taskKey;
    }

    public void setTaskKey(LongIdKey taskKey) {
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
        return "TaskExecuteInfo{" +
                "taskKey=" + taskKey +
                ", message='" + message + '\'' +
                '}';
    }
}
