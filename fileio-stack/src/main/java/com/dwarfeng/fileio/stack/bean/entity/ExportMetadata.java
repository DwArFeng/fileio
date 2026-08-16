package com.dwarfeng.fileio.stack.bean.entity;

import com.dwarfeng.fileio.stack.bean.key.TaskItemKey;
import com.dwarfeng.subgrade.stack.bean.entity.Entity;

/**
 * 导出元数据。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class ExportMetadata implements Entity<TaskItemKey> {

    private static final long serialVersionUID = -3233824554512886191L;

    private TaskItemKey key;
    private String content;
    private String remark;

    public ExportMetadata() {
    }

    public ExportMetadata(TaskItemKey key, String content, String remark) {
        this.key = key;
        this.content = content;
        this.remark = remark;
    }

    @Override
    public TaskItemKey getKey() {
        return key;
    }

    @Override
    public void setKey(TaskItemKey key) {
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
        return "ExportMetadata{" +
                "key=" + key +
                ", content='" + content + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
