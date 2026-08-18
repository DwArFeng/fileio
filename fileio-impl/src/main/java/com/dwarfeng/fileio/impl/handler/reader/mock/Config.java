package com.dwarfeng.fileio.impl.handler.reader.mock;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.subgrade.stack.bean.Bean;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.List;

/**
 * 配置。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class Config implements Bean {

    private static final long serialVersionUID = 4188737388211621977L;

    @JSONField(name = "#random_seed", ordinal = 1, deserialize = false)
    private String randomSeedRem = "随机种子, 用于生成随机数据, 可以为 null。";

    @JSONField(name = "random_seed", ordinal = 2)
    private Long randomSeed;

    @JSONField(name = "#valid_field_types", ordinal = 3, deserialize = false)
    private List<String> validFieldTypesRem = FieldTypeUtil.VALID_FIELD_TYPES;

    @JSONField(name = "field_items", ordinal = 4)
    private List<FieldItem> fieldItems;

    @JSONField(name = "#batch_size", ordinal = 5, deserialize = false)
    private String batchSizeRem = "批量大小, 用于指定每次生成的数据的数量。";

    @JSONField(name = "batch_size", ordinal = 6)
    private int batchSize;

    @JSONField(name = "#metadata_identifier", ordinal = 7, deserialize = false)
    private String metadataIdentifierRem = "元数据标识符, 用于指定读取器的元数据对应的标识符。";

    @JSONField(name = "metadata_identifier", ordinal = 8)
    private String metadataIdentifier;

    public Config() {
    }

    public Config(Long randomSeed, List<FieldItem> fieldItems, int batchSize, String metadataIdentifier) {
        this.randomSeed = randomSeed;
        this.fieldItems = fieldItems;
        this.batchSize = batchSize;
        this.metadataIdentifier = metadataIdentifier;
    }

    public String getRandomSeedRem() {
        return randomSeedRem;
    }

    public void setRandomSeedRem(String randomSeedRem) {
        this.randomSeedRem = randomSeedRem;
    }

    public Long getRandomSeed() {
        return randomSeed;
    }

    public void setRandomSeed(Long randomSeed) {
        this.randomSeed = randomSeed;
    }

    public List<String> getValidFieldTypesRem() {
        return validFieldTypesRem;
    }

    public void setValidFieldTypesRem(List<String> validFieldTypesRem) {
        this.validFieldTypesRem = validFieldTypesRem;
    }

    public List<FieldItem> getFieldItems() {
        return fieldItems;
    }

    public void setFieldItems(List<FieldItem> fieldItems) {
        this.fieldItems = fieldItems;
    }

    public String getBatchSizeRem() {
        return batchSizeRem;
    }

    public void setBatchSizeRem(String batchSizeRem) {
        this.batchSizeRem = batchSizeRem;
    }

    public int getBatchSize() {
        return batchSize;
    }

    public void setBatchSize(int batchSize) {
        this.batchSize = batchSize;
    }

    public String getMetadataIdentifierRem() {
        return metadataIdentifierRem;
    }

    public void setMetadataIdentifierRem(String metadataIdentifierRem) {
        this.metadataIdentifierRem = metadataIdentifierRem;
    }

    public String getMetadataIdentifier() {
        return metadataIdentifier;
    }

    public void setMetadataIdentifier(String metadataIdentifier) {
        this.metadataIdentifier = metadataIdentifier;
    }

    @Override
    public String toString() {
        return "Config{" +
                "randomSeedRem='" + randomSeedRem + '\'' +
                ", randomSeed=" + randomSeed +
                ", validFieldTypesRem=" + validFieldTypesRem +
                ", fieldItems=" + fieldItems +
                ", batchSizeRem='" + batchSizeRem + '\'' +
                ", batchSize=" + batchSize +
                ", metadataIdentifierRem='" + metadataIdentifierRem + '\'' +
                ", metadataIdentifier='" + metadataIdentifier + '\'' +
                '}';
    }

    public static class FieldItem implements Dto {

        private static final long serialVersionUID = 769242870013441051L;

        @JSONField(name = "field_name", ordinal = 1)
        private String fieldName;

        @JSONField(name = "field_type", ordinal = 2)
        private String fieldType;

        public FieldItem() {
        }

        public FieldItem(String fieldName, String fieldType) {
            this.fieldName = fieldName;
            this.fieldType = fieldType;
        }

        public String getFieldName() {
            return fieldName;
        }

        public void setFieldName(String fieldName) {
            this.fieldName = fieldName;
        }

        public String getFieldType() {
            return fieldType;
        }

        public void setFieldType(String fieldType) {
            this.fieldType = fieldType;
        }

        @Override
        public String toString() {
            return "FieldItem{" +
                    "fieldName='" + fieldName + '\'' +
                    ", fieldType='" + fieldType + '\'' +
                    '}';
        }
    }
}
