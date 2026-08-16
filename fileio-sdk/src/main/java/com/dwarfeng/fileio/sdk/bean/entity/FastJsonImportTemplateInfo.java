package com.dwarfeng.fileio.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fileio.sdk.bean.key.FastJsonTaskSettingItemKey;
import com.dwarfeng.fileio.stack.bean.entity.ImportTemplateInfo;
import com.dwarfeng.subgrade.stack.bean.Bean;

import java.util.Date;
import java.util.Objects;

/**
 * FastJson 导入模板信息。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class FastJsonImportTemplateInfo implements Bean {

    private static final long serialVersionUID = -7008127870531208149L;

    public static FastJsonImportTemplateInfo of(ImportTemplateInfo importTemplateInfo) {
        if (Objects.isNull(importTemplateInfo)) {
            return null;
        } else {
            return new FastJsonImportTemplateInfo(
                    FastJsonTaskSettingItemKey.of(importTemplateInfo.getKey()),
                    importTemplateInfo.getOriginName(),
                    importTemplateInfo.getLength(),
                    importTemplateInfo.getUploadedDate(),
                    importTemplateInfo.getRemark()
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private FastJsonTaskSettingItemKey key;

    @JSONField(name = "origin_name", ordinal = 2)
    private String originName;

    @JSONField(name = "length", ordinal = 3)
    private long length;

    @JSONField(name = "uploaded_date", ordinal = 4)
    private Date uploadedDate;

    @JSONField(name = "remark", ordinal = 5)
    private String remark;

    public FastJsonImportTemplateInfo() {
    }

    public FastJsonImportTemplateInfo(
            FastJsonTaskSettingItemKey key, String originName, long length, Date uploadedDate, String remark
    ) {
        this.key = key;
        this.originName = originName;
        this.length = length;
        this.uploadedDate = uploadedDate;
        this.remark = remark;
    }

    public FastJsonTaskSettingItemKey getKey() {
        return key;
    }

    public void setKey(FastJsonTaskSettingItemKey key) {
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
        return "FastJsonImportTemplateInfo{" +
                "key=" + key +
                ", originName='" + originName + '\'' +
                ", length=" + length +
                ", uploadedDate=" + uploadedDate +
                ", remark='" + remark + '\'' +
                '}';
    }
}
