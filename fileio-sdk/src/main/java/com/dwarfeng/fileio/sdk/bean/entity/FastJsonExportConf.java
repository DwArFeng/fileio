package com.dwarfeng.fileio.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fileio.sdk.bean.key.FastJsonTaskSettingItemKey;
import com.dwarfeng.fileio.stack.bean.entity.ExportConf;
import com.dwarfeng.subgrade.stack.bean.Bean;

import java.util.Objects;

/**
 * FastJson 导出配置。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class FastJsonExportConf implements Bean {

    private static final long serialVersionUID = -6350811968652698284L;

    public static FastJsonExportConf of(ExportConf exportConf) {
        if (Objects.isNull(exportConf)) {
            return null;
        } else {
            return new FastJsonExportConf(
                    FastJsonTaskSettingItemKey.of(exportConf.getKey()),
                    exportConf.getContent(),
                    exportConf.getRemark()
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private FastJsonTaskSettingItemKey key;

    @JSONField(name = "content", ordinal = 2)
    private String content;

    @JSONField(name = "remark", ordinal = 3)
    private String remark;

    public FastJsonExportConf() {
    }

    public FastJsonExportConf(FastJsonTaskSettingItemKey key, String content, String remark) {
        this.key = key;
        this.content = content;
        this.remark = remark;
    }

    public FastJsonTaskSettingItemKey getKey() {
        return key;
    }

    public void setKey(FastJsonTaskSettingItemKey key) {
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
        return "FastJsonExportConf{" +
                "key=" + key +
                ", content='" + content + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
