package com.dwarfeng.fileio.stack.bean.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

/**
 * 任务启动信息。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class TaskStartInfo implements Dto {

    private static final long serialVersionUID = 1315867527884393381L;

    private LongIdKey taskKey;
    private String message;

    public TaskStartInfo() {
    }

    public TaskStartInfo(LongIdKey taskKey, String message) {
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
        return "TaskStartInfo{" +
                "taskKey=" + taskKey +
                ", message='" + message + '\'' +
                '}';
    }
}
