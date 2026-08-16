package com.dwarfeng.fileio.stack.bean.entity;

import com.dwarfeng.fileio.stack.bean.key.TaskSettingItemKey;
import com.dwarfeng.subgrade.stack.bean.entity.Entity;

/**
 * 导出配置。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class ExportConf implements Entity<TaskSettingItemKey> {

    private static final long serialVersionUID = -2059451216019101068L;

    private TaskSettingItemKey key;
    private String content;
    private String remark;

    public ExportConf() {
    }

    public ExportConf(TaskSettingItemKey key, String content, String remark) {
        this.key = key;
        this.content = content;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "ExportConf{" +
                "key=" + key +
                ", content='" + content + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
