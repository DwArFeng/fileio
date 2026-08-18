package com.dwarfeng.fileio.stack.bean.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;

/**
 * 任务创建信息。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class TaskCreateInfo implements Dto {

    private static final long serialVersionUID = -4390802296075430885L;

    private LongIdKey settingKey;

    /**
     * @since 1.5.0
     */
    private StringIdKey userKey;

    private String message;

    public TaskCreateInfo() {
    }

    public TaskCreateInfo(LongIdKey settingKey, String message) {
        this(settingKey, null, message);
    }

    public TaskCreateInfo(LongIdKey settingKey, StringIdKey userKey, String message) {
        this.settingKey = settingKey;
        this.userKey = userKey;
        this.message = message;
    }

    public LongIdKey getSettingKey() {
        return settingKey;
    }

    public void setSettingKey(LongIdKey settingKey) {
        this.settingKey = settingKey;
    }

    public StringIdKey getUserKey() {
        return userKey;
    }

    public void setUserKey(StringIdKey userKey) {
        this.userKey = userKey;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "TaskCreateInfo{" +
                "settingKey=" + settingKey +
                ", userKey=" + userKey +
                ", message='" + message + '\'' +
                '}';
    }
}
