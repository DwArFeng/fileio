package com.dwarfeng.fileio.stack.bean.entity;

import com.dwarfeng.fileio.stack.bean.key.TaskSettingItemKey;
import com.dwarfeng.subgrade.stack.bean.entity.Entity;

import java.util.Date;

/**
 * 导入模板信息。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class ImportTemplateInfo implements Entity<TaskSettingItemKey> {

    private static final long serialVersionUID = 1580458395146537702L;

    private TaskSettingItemKey key;
    private String originName;
    private long length;
    private Date uploadedDate;
    private String remark;

    public ImportTemplateInfo() {
    }

    public ImportTemplateInfo(
            TaskSettingItemKey key, String originName, long length, Date uploadedDate, String remark
    ) {
        this.key = key;
        this.originName = originName;
        this.length = length;
        this.uploadedDate = uploadedDate;
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

    public Date getUploadedDate() {
        return uploadedDate;
    }

    public void setUploadedDate(Date uploadedDate) {
        this.uploadedDate = uploadedDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "ImportTemplateInfo{" +
                "key=" + key +
                ", originName='" + originName + '\'' +
                ", length=" + length +
                ", uploadedDate=" + uploadedDate +
                ", remark='" + remark + '\'' +
                '}';
    }
}
