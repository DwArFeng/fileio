package com.dwarfeng.fileio.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fileio.sdk.bean.key.FastJsonTaskItemKey;
import com.dwarfeng.fileio.stack.bean.entity.ExportFileInfo;
import com.dwarfeng.subgrade.stack.bean.Bean;

import java.util.Date;
import java.util.Objects;

/**
 * FastJson 导出文件信息。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class FastJsonExportFileInfo implements Bean {

    private static final long serialVersionUID = -9188352800519062670L;

    public static FastJsonExportFileInfo of(ExportFileInfo exportFileInfo) {
        if (Objects.isNull(exportFileInfo)) {
            return null;
        } else {
            return new FastJsonExportFileInfo(
                    FastJsonTaskItemKey.of(exportFileInfo.getKey()),
                    exportFileInfo.getOriginName(),
                    exportFileInfo.getLength(),
                    exportFileInfo.getGeneratedDate(),
                    exportFileInfo.getRemark()
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private FastJsonTaskItemKey key;

    @JSONField(name = "origin_name", ordinal = 2)
    private String originName;

    @JSONField(name = "length", ordinal = 3)
    private long length;

    @JSONField(name = "generated_date", ordinal = 4)
    private Date generatedDate;

    @JSONField(name = "remark", ordinal = 5)
    private String remark;

    public FastJsonExportFileInfo() {
    }

    public FastJsonExportFileInfo(
            FastJsonTaskItemKey key, String originName, long length, Date generatedDate, String remark
    ) {
        this.key = key;
        this.originName = originName;
        this.length = length;
        this.generatedDate = generatedDate;
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
        return "FastJsonExportFileInfo{" +
                "key=" + key +
                ", originName='" + originName + '\'' +
                ", length=" + length +
                ", generatedDate=" + generatedDate +
                ", remark='" + remark + '\'' +
                '}';
    }
}
