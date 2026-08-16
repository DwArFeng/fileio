package com.dwarfeng.fileio.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fileio.sdk.bean.key.FastJsonTaskItemKey;
import com.dwarfeng.fileio.stack.bean.entity.ImportMetadata;
import com.dwarfeng.subgrade.stack.bean.Bean;

import java.util.Objects;

/**
 * FastJson 导入元数据。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class FastJsonImportMetadata implements Bean {

    private static final long serialVersionUID = 4565276174513182917L;

    public static FastJsonImportMetadata of(ImportMetadata importMetadata) {
        if (Objects.isNull(importMetadata)) {
            return null;
        } else {
            return new FastJsonImportMetadata(
                    FastJsonTaskItemKey.of(importMetadata.getKey()),
                    importMetadata.getContent(),
                    importMetadata.getRemark()
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private FastJsonTaskItemKey key;

    @JSONField(name = "content", ordinal = 2)
    private String content;

    @JSONField(name = "remark", ordinal = 3)
    private String remark;

    public FastJsonImportMetadata() {
    }

    public FastJsonImportMetadata(FastJsonTaskItemKey key, String content, String remark) {
        this.key = key;
        this.content = content;
        this.remark = remark;
    }

    public FastJsonTaskItemKey getKey() {
        return key;
    }

    public void setKey(FastJsonTaskItemKey key) {
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
        return "FastJsonImportMetadata{" +
                "key=" + key +
                ", content='" + content + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
