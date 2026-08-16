package com.dwarfeng.fileio.stack.bean.entity;

import com.dwarfeng.fileio.stack.bean.key.TaskItemKey;
import com.dwarfeng.subgrade.stack.bean.entity.Entity;

/**
 * 导入元数据。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class ImportMetadata implements Entity<TaskItemKey> {

    private static final long serialVersionUID = 8637933276234316280L;

    private TaskItemKey key;
    private String content;
    private String remark;

    public ImportMetadata() {
    }

    public ImportMetadata(TaskItemKey key, String content, String remark) {
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
        return "ImportMetadata{" +
                "key=" + key +
                ", content='" + content + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
