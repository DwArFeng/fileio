package com.dwarfeng.fileio.stack.bean.entity;

import com.dwarfeng.fileio.stack.bean.key.TaskItemKey;
import com.dwarfeng.subgrade.stack.bean.entity.Entity;

import java.util.Date;

/**
 * 导出文件信息。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class ExportFileInfo implements Entity<TaskItemKey> {

    private static final long serialVersionUID = 4082391065718824826L;

    private TaskItemKey key;
    private String originName;
    private long length;
    private Date generatedDate;
    private String remark;

    public ExportFileInfo() {
    }

    public ExportFileInfo(TaskItemKey key, String originName, long length, Date generatedDate, String remark) {
        this.key = key;
        this.originName = originName;
        this.length = length;
        this.generatedDate = generatedDate;
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

    public String getOriginName() {
        return originName;
    }

    public void setOriginName(String originName) {
        this.originName = originName;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public Date getGeneratedDate() {
        return generatedDate;
    }

    public void setGeneratedDate(Date generatedDate) {
        this.generatedDate = generatedDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "ExportFileInfo{" +
                "key=" + key +
                ", originName='" + originName + '\'' +
                ", length=" + length +
                ", generatedDate=" + generatedDate +
                ", remark='" + remark + '\'' +
                '}';
    }
}
