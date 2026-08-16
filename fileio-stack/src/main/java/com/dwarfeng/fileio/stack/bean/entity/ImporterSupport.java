package com.dwarfeng.fileio.stack.bean.entity;

import com.dwarfeng.subgrade.stack.bean.entity.Entity;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;

/**
 * 导入器支持。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class ImporterSupport implements Entity<StringIdKey> {

    private static final long serialVersionUID = -810666197463187862L;

    private StringIdKey key;
    private String label;
    private String description;
    private String exampleParam;
    private String exampleMetadata;

    public ImporterSupport() {
    }

    public ImporterSupport(
            StringIdKey key, String label, String description, String exampleParam, String exampleMetadata
    ) {
        this.key = key;
        this.label = label;
        this.description = description;
        this.exampleParam = exampleParam;
        this.exampleMetadata = exampleMetadata;
    }

    @Override
    public StringIdKey getKey() {
        return key;
    }

    @Override
    public void setKey(StringIdKey key) {
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
        return "ImporterSupport{" +
                "key=" + key +
                ", label='" + label + '\'' +
                ", description='" + description + '\'' +
                ", exampleParam='" + exampleParam + '\'' +
                ", exampleMetadata='" + exampleMetadata + '\'' +
                '}';
    }
}
