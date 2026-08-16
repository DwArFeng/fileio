package com.dwarfeng.fileio.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fileio.sdk.bean.key.FastJsonTaskSettingItemKey;
import com.dwarfeng.fileio.stack.bean.entity.ExportTemplateInfo;
import com.dwarfeng.subgrade.stack.bean.Bean;

import java.util.Date;
import java.util.Objects;

/**
 * FastJson 导出模板信息。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class FastJsonExportTemplateInfo implements Bean {

    private static final long serialVersionUID = -8831151692934992330L;

    public static FastJsonExportTemplateInfo of(ExportTemplateInfo exportTemplateInfo) {
        if (Objects.isNull(exportTemplateInfo)) {
            return null;
        } else {
            return new FastJsonExportTemplateInfo(
                    FastJsonTaskSettingItemKey.of(exportTemplateInfo.getKey()),
                    exportTemplateInfo.getOriginName(),
                    exportTemplateInfo.getLength(),
                    exportTemplateInfo.getUploadedDate(),
                    exportTemplateInfo.getRemark()
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

    public FastJsonExportTemplateInfo() {
    }

    public FastJsonExportTemplateInfo(
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
        return "FastJsonExportTemplateInfo{" +
                "key=" + key +
                ", originName='" + originName + '\'' +
                ", length=" + length +
                ", uploadedDate=" + uploadedDate +
                ", remark='" + remark + '\'' +
                '}';
    }
}
