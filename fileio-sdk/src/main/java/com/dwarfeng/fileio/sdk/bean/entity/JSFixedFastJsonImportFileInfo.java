package com.dwarfeng.fileio.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.subgrade.stack.bean.Bean;
import com.dwarfeng.fileio.sdk.bean.key.JSFixedFastJsonTaskItemKey;
import com.dwarfeng.fileio.stack.bean.entity.ImportFileInfo;

import java.util.Date;
import java.util.Objects;

/**
 * JSFixed FastJson 导入文件信息。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class JSFixedFastJsonImportFileInfo implements Bean {

    private static final long serialVersionUID = 4709446668006934635L;

    public static JSFixedFastJsonImportFileInfo of(ImportFileInfo importFileInfo) {
        if (Objects.isNull(importFileInfo)) {
            return null;
        } else {
            return new JSFixedFastJsonImportFileInfo(
                    JSFixedFastJsonTaskItemKey.of(importFileInfo.getKey()),
                    importFileInfo.getOriginName(),
                    importFileInfo.getLength(),
                    importFileInfo.getUploadedDate(),
                    importFileInfo.getRemark()
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private JSFixedFastJsonTaskItemKey key;

    @JSONField(name = "origin_name", ordinal = 2)
    private String originName;

    @JSONField(name = "length", ordinal = 3)
    private long length;

    @JSONField(name = "uploaded_date", ordinal = 4)
    private Date uploadedDate;

    @JSONField(name = "remark", ordinal = 6)
    private String remark;

    public JSFixedFastJsonImportFileInfo() {
    }

    public JSFixedFastJsonImportFileInfo(
            JSFixedFastJsonTaskItemKey key, String originName, long length, Date uploadedDate, String remark
    ) {
        this.key = key;
        this.originName = originName;
        this.length = length;
        this.uploadedDate = uploadedDate;
        this.remark = remark;
    }

    public JSFixedFastJsonTaskItemKey getKey() {
        return key;
    }

    public void setKey(JSFixedFastJsonTaskItemKey key) {
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
        return "JSFixedFastJsonImportFileInfo{" +
                "key=" + key +
                ", originName='" + originName + '\'' +
                ", length=" + length +
                ", uploadedDate=" + uploadedDate +
                ", remark='" + remark + '\'' +
                '}';
    }
}
