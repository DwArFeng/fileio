package com.dwarfeng.fileio.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonStringIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;
import com.dwarfeng.fileio.stack.bean.entity.ImporterSupport;

import java.util.Objects;

/**
 * FastJson 导入器支持。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class FastJsonImporterSupport implements Bean {

    private static final long serialVersionUID = -2735923141121414508L;

    public static FastJsonImporterSupport of(ImporterSupport importerSupport) {
        if (Objects.isNull(importerSupport)) {
            return null;
        } else {
            return new FastJsonImporterSupport(
                    FastJsonStringIdKey.of(importerSupport.getKey()),
                    importerSupport.getLabel(),
                    importerSupport.getDescription(),
                    importerSupport.getExampleParam(),
                    importerSupport.getExampleMetadata()
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private FastJsonStringIdKey key;

    @JSONField(name = "label", ordinal = 2)
    private String label;

    @JSONField(name = "description", ordinal = 3)
    private String description;

    @JSONField(name = "example_param", ordinal = 4)
    private String exampleParam;

    @JSONField(name = "example_metadata", ordinal = 5)
    private String exampleMetadata;

    public FastJsonImporterSupport() {
    }

    public FastJsonImporterSupport(
            FastJsonStringIdKey key, String label, String description, String exampleParam, String exampleMetadata
    ) {
        this.key = key;
        this.label = label;
        this.description = description;
        this.exampleParam = exampleParam;
        this.exampleMetadata = exampleMetadata;
    }

    public FastJsonStringIdKey getKey() {
        return key;
    }

    public void setKey(FastJsonStringIdKey key) {
        this.key = key;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExampleParam() {
        return exampleParam;
    }

    public void setExampleParam(String exampleParam) {
        this.exampleParam = exampleParam;
    }

    public String getExampleMetadata() {
        return exampleMetadata;
    }

    public void setExampleMetadata(String exampleMetadata) {
        this.exampleMetadata = exampleMetadata;
    }

    @Override
    public String toString() {
        return "FastJsonImporterSupport{" +
                "key=" + key +
                ", label='" + label + '\'' +
                ", description='" + description + '\'' +
                ", exampleParam='" + exampleParam + '\'' +
                ", exampleMetadata='" + exampleMetadata + '\'' +
                '}';
    }
}
