package com.dwarfeng.fileio.stack.bean.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

/**
 * 任务数据数量更新信息。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class TaskDataCountUpdateInfo implements Dto {

    private static final long serialVersionUID = 6174885028045644735L;

    private LongIdKey taskKey;
    private int dataCount;

    public TaskDataCountUpdateInfo() {
    }

    public TaskDataCountUpdateInfo(LongIdKey taskKey, int dataCount) {
        this.taskKey = taskKey;
        this.dataCount = dataCount;
    }

    public LongIdKey getTaskKey() {
        return taskKey;
    }

    public void setTaskKey(LongIdKey taskKey) {
        this.taskKey = taskKey;
    }

    public int getDataCount() {
        return dataCount;
    }

    public void setDataCount(int dataCount) {
        this.dataCount = dataCount;
    }

    @Override
    public String toString() {
        return "TaskDataCountUpdateInfo{" +
                "taskKey=" + taskKey +
                ", dataCount=" + dataCount +
                '}';
    }
}
