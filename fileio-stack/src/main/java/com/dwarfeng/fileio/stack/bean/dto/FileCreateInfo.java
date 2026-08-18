package com.dwarfeng.fileio.stack.bean.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

/**
 * 文件创建信息。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class FileCreateInfo implements Dto {

    private static final long serialVersionUID = 1976494562060880123L;

    private LongIdKey taskKey;
    private String identifier;
    private String originName;

    public FileCreateInfo() {
    }

    public FileCreateInfo(LongIdKey taskKey, String identifier, String originName) {
        this.taskKey = taskKey;
        this.identifier = identifier;
        this.originName = originName;
    }

    public LongIdKey getTaskKey() {
        return taskKey;
    }

    public void setTaskKey(LongIdKey taskKey) {
        this.taskKey = taskKey;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getOriginName() {
        return originName;
    }

    public void setOriginName(String originName) {
        this.originName = originName;
    }

    @Override
    public String toString() {
        return "FileCreateInfo{" +
                "taskKey=" + taskKey +
                ", identifier='" + identifier + '\'' +
                ", originName='" + originName + '\'' +
                '}';
    }
}
