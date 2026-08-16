package com.dwarfeng.fileio.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fileio.sdk.bean.key.JSFixedFastJsonTaskSettingItemKey;
import com.dwarfeng.fileio.stack.bean.entity.ImportConf;
import com.dwarfeng.subgrade.stack.bean.Bean;

import java.util.Objects;

/**
 * JSFixed FastJson 导入配置。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class JSFixedFastJsonImportConf implements Bean {

    private static final long serialVersionUID = 3447395877182448202L;

    public static JSFixedFastJsonImportConf of(ImportConf importConf) {
        if (Objects.isNull(importConf)) {
            return null;
        } else {
            return new JSFixedFastJsonImportConf(
                    JSFixedFastJsonTaskSettingItemKey.of(importConf.getKey()),
                    importConf.getContent(),
                    importConf.getRemark()
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private JSFixedFastJsonTaskSettingItemKey key;

    @JSONField(name = "content", ordinal = 2)
    private String content;

    @JSONField(name = "remark", ordinal = 3)
    private String remark;

    public JSFixedFastJsonImportConf() {
    }

    public JSFixedFastJsonImportConf(JSFixedFastJsonTaskSettingItemKey key, String content, String remark) {
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
        return "JSFixedFastJsonImportConf{" +
                "key=" + key +
                ", content='" + content + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
