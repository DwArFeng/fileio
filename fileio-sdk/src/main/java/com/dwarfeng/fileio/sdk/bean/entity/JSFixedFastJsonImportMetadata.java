package com.dwarfeng.fileio.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.subgrade.stack.bean.Bean;
import com.dwarfeng.fileio.sdk.bean.key.JSFixedFastJsonTaskItemKey;
import com.dwarfeng.fileio.stack.bean.entity.ImportMetadata;

import java.util.Objects;

/**
 * JSFixed FastJson 导入元数据。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class JSFixedFastJsonImportMetadata implements Bean {

    private static final long serialVersionUID = 6559000978326190546L;

    public static JSFixedFastJsonImportMetadata of(ImportMetadata importMetadata) {
        if (Objects.isNull(importMetadata)) {
            return null;
        } else {
            return new JSFixedFastJsonImportMetadata(
                    JSFixedFastJsonTaskItemKey.of(importMetadata.getKey()),
                    importMetadata.getContent(),
                    importMetadata.getRemark()
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private JSFixedFastJsonTaskItemKey key;

    @JSONField(name = "content", ordinal = 2)
    private String content;

    @JSONField(name = "remark", ordinal = 3)
    private String remark;

    public JSFixedFastJsonImportMetadata() {
    }

    public JSFixedFastJsonImportMetadata(JSFixedFastJsonTaskItemKey key, String content, String remark) {
        this.key = key;
        this.content = content;
        this.remark = remark;
    }

    public JSFixedFastJsonTaskItemKey getKey() {
        return key;
    }

    public void setKey(JSFixedFastJsonTaskItemKey key) {
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
        return "JSFixedFastJsonImportMetadata{" +
                "key=" + key +
                ", content='" + content + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
