package com.dwarfeng.fileio.stack.bean.entity;

import com.dwarfeng.fileio.stack.bean.key.TaskSettingItemKey;
import com.dwarfeng.subgrade.stack.bean.entity.Entity;

/**
 * 导入器信息。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class ImporterInfo implements Entity<TaskSettingItemKey> {

    private static final long serialVersionUID = 4066238982595260398L;

    private TaskSettingItemKey key;
    private boolean enabled;
    private String type;
    private String param;
    private String remark;

    public ImporterInfo() {
    }

    public ImporterInfo(TaskSettingItemKey key, boolean enabled, String type, String param, String remark) {
        this.key = key;
        this.enabled = enabled;
        this.type = type;
        this.param = param;
        this.remark = remark;
    }

    @Override
    public TaskSettingItemKey getKey() {
        return key;
    }

    @Override
    public void setKey(TaskSettingItemKey key) {
        this.key = key;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "ImporterInfo{" +
                "key=" + key +
                ", enabled=" + enabled +
                ", type='" + type + '\'' +
                ", param='" + param + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
