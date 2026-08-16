package com.dwarfeng.fileio.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fileio.sdk.bean.key.JSFixedFastJsonTaskSettingItemKey;
import com.dwarfeng.fileio.stack.bean.entity.ExportConf;
import com.dwarfeng.subgrade.stack.bean.Bean;

import java.util.Objects;

/**
 * JSFixed FastJson 导出配置。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class JSFixedFastJsonExportConf implements Bean {

    private static final long serialVersionUID = -9098261288490697614L;

    public static JSFixedFastJsonExportConf of(ExportConf exportConf) {
        if (Objects.isNull(exportConf)) {
            return null;
        } else {
            return new JSFixedFastJsonExportConf(
                    JSFixedFastJsonTaskSettingItemKey.of(exportConf.getKey()),
                    exportConf.getContent(),
                    exportConf.getRemark()
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private JSFixedFastJsonTaskSettingItemKey key;

    @JSONField(name = "content", ordinal = 2)
    private String content;

    @JSONField(name = "remark", ordinal = 3)
    private String remark;

    public JSFixedFastJsonExportConf() {
    }

    public JSFixedFastJsonExportConf(JSFixedFastJsonTaskSettingItemKey key, String content, String remark) {
        this.key = key;
        this.content = content;
        this.remark = remark;
    }

    public JSFixedFastJsonTaskSettingItemKey getKey() {
        return key;
    }

    public void setKey(JSFixedFastJsonTaskSettingItemKey key) {
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
        return "JSFixedFastJsonExportConf{" +
                "key=" + key +
                ", content='" + content + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
