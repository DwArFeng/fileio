package com.dwarfeng.fileio.stack.bean.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

/**
 * 任务数据数量增加信息。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public class TaskDataCountIncreaseInfo implements Dto {

    private static final long serialVersionUID = 4286539534971587336L;

    private LongIdKey taskKey;
    private int increment;

    public TaskDataCountIncreaseInfo() {
    }

    public TaskDataCountIncreaseInfo(LongIdKey taskKey, int increment) {
        this.taskKey = taskKey;
        this.increment = increment;
    }

    public LongIdKey getTaskKey() {
        return taskKey;
    }

    public void setTaskKey(LongIdKey taskKey) {
        this.taskKey = taskKey;
    }

    public int getIncrement() {
        return increment;
    }

    public void setIncrement(int increment) {
        this.increment = increment;
    }

    @Override
    public String toString() {
        return "TaskDataCountIncreaseInfo{" +
                "taskKey=" + taskKey +
                ", increment=" + increment +
                '}';
    }
}
