package com.dwarfeng.fileio.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fileio.sdk.bean.key.FastJsonTaskItemKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import java.util.Date;

/**
 * FastJson 导入文件信息。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class FastJsonImportFileInfo implements Bean {

    private static final long serialVersionUID = 9206847464140281608L;

    @JSONField(name = "key", ordinal = 1)
    private FastJsonTaskItemKey key;

    @JSONField(name = "origin_name", ordinal = 3)
    private String originName;

    @JSONField(name = "length", ordinal = 4)
    private long length;

    @JSONField(name = "uploaded_date", ordinal = 5)
    private Date uploadedDate;

    @JSONField(name = "remark", ordinal = 6)
    private String remark;

    public FastJsonImportFileInfo() {
    }

    public FastJsonImportFileInfo(
            FastJsonTaskItemKey key, String originName, long length, Date uploadedDate, String remark
    ) {
        this.key = key;
        this.originName = originName;
        this.length = length;
        this.uploadedDate = uploadedDate;
        this.remark = remark;
    }

    public FastJsonTaskItemKey getKey() {
        return key;
    }

    public void setKey(FastJsonTaskItemKey key) {
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
        return "FastJsonImportFileInfo{" +
                "key=" + key +
                ", originName='" + originName + '\'' +
                ", length=" + length +
                ", uploadedDate=" + uploadedDate +
                ", remark='" + remark + '\'' +
                '}';
    }
}
