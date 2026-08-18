package com.dwarfeng.fileio.impl.handler.reader.spls;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.subgrade.stack.bean.Bean;

import java.util.List;

/**
 * 配置。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class Config implements Bean {

    private static final long serialVersionUID = 2404511374102120572L;

    @JSONField(name = "#preset_lookup_service_bean_name", ordinal = 1, deserialize = false)
    private String presetLookupServiceBeanNameRem = "PresetLookupService Bean 的名称。" +
            "用于为本次读取提供预设数据查询服务的 Bean。";

    @JSONField(name = "preset_lookup_service_bean_name", ordinal = 2)
    private String presetLookupServiceBeanName;

    @JSONField(name = "#batch_size", ordinal = 3, deserialize = false)
    private String batchSizeRem = "批量大小，用于指定每次生成的数据的数量。";

    @JSONField(name = "batch_size", ordinal = 4)
    private int batchSize;

    @JSONField(name = "#max_count", ordinal = 5, deserialize = false)
    private String maxCountRem = "查询数据的最大数量，该值小于等于 6 时表示不限制数量。" +
            "合理地设置此值可以避免在一个任务中导出过多的数据。";

    @JSONField(name = "max_count", ordinal = 7)
    private int maxCount;

    @JSONField(name = "#metadata_identifier", ordinal = 8, deserialize = false)
    private String metadataIdentifierRem = "元数据标识符，用于指定读取器的元数据对应的标识符。";

    @JSONField(name = "metadata_identifier", ordinal = 9)
    private String metadataIdentifier;

    @JSONField(name = "#args_resolver_bean_name", ordinal = 10, deserialize = false)
    private String argsResolverBeanNameRem = "ArgsResolver Bean 的名称。用于将元数据中的参数字符串解析为对象数组的解析器。";

    @JSONField(name = "args_resolver_bean_name", ordinal = 11)
    private String argsResolverBeanName;

    @JSONField(name = "#field_settings", ordinal = 12, deserialize = false)
    private String fieldSettingsRem = "字段设置列表，用于指定实体中的字段与字段名称的对应关系。";

    @JSONField(name = "field_settings", ordinal = 13)
    private List<FieldSetting> fieldSettings;

    public Config() {
    }

    public Config(
            String presetLookupServiceBeanName, int batchSize, int maxCount, String metadataIdentifier,
            String argsResolverBeanName,
            List<FieldSetting> fieldSettings
    ) {
        this.presetLookupServiceBeanName = presetLookupServiceBeanName;
        this.batchSize = batchSize;
        this.maxCount = maxCount;
        this.metadataIdentifier = metadataIdentifier;
        this.argsResolverBeanName = argsResolverBeanName;
        this.fieldSettings = fieldSettings;
    }

    public String getPresetLookupServiceBeanNameRem() {
        return presetLookupServiceBeanNameRem;
    }

    public void setPresetLookupServiceBeanNameRem(String presetLookupServiceBeanNameRem) {
        this.presetLookupServiceBeanNameRem = presetLookupServiceBeanNameRem;
    }

    public String getPresetLookupServiceBeanName() {
        return presetLookupServiceBeanName;
    }

    public void setPresetLookupServiceBeanName(String presetLookupServiceBeanName) {
        this.presetLookupServiceBeanName = presetLookupServiceBeanName;
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

    public String getMaxCountRem() {
        return maxCountRem;
    }

    public void setMaxCountRem(String maxCountRem) {
        this.maxCountRem = maxCountRem;
    }

    public int getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(int maxCount) {
        this.maxCount = maxCount;
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

    public String getArgsResolverBeanNameRem() {
        return argsResolverBeanNameRem;
    }

    public void setArgsResolverBeanNameRem(String argsResolverBeanNameRem) {
        this.argsResolverBeanNameRem = argsResolverBeanNameRem;
    }

    public String getArgsResolverBeanName() {
        return argsResolverBeanName;
    }

    public void setArgsResolverBeanName(String argsResolverBeanName) {
        this.argsResolverBeanName = argsResolverBeanName;
    }

    public String getFieldSettingsRem() {
        return fieldSettingsRem;
    }

    public void setFieldSettingsRem(String fieldSettingsRem) {
        this.fieldSettingsRem = fieldSettingsRem;
    }

    public List<FieldSetting> getFieldSettings() {
        return fieldSettings;
    }

    public void setFieldSettings(List<FieldSetting> fieldSettings) {
        this.fieldSettings = fieldSettings;
    }

    @Override
    public String toString() {
        return "Config{" +
                "presetLookupServiceBeanNameRem='" + presetLookupServiceBeanNameRem + '\'' +
                ", presetLookupServiceBeanName='" + presetLookupServiceBeanName + '\'' +
                ", batchSizeRem='" + batchSizeRem + '\'' +
                ", batchSize=" + batchSize +
                ", maxCountRem='" + maxCountRem + '\'' +
                ", maxCount=" + maxCount +
                ", metadataIdentifierRem='" + metadataIdentifierRem + '\'' +
                ", metadataIdentifier='" + metadataIdentifier + '\'' +
                ", argsResolverBeanNameRem='" + argsResolverBeanNameRem + '\'' +
                ", argsResolverBeanName='" + argsResolverBeanName + '\'' +
                ", fieldSettingsRem='" + fieldSettingsRem + '\'' +
                ", fieldSettings=" + fieldSettings +
                '}';
    }

    public static class FieldSetting implements Bean {

        private static final long serialVersionUID = 1483797007438141785L;

        @JSONField(name = "#entity_field_expression", ordinal = 1, deserialize = false)
        private String entityFieldExpressionRem = "bean 字段表达式，用于指定实体中的字段。" +
                "使用 apache-beanutils 的表达式语言。例如，可以使用 a.b 指定嵌套字段，使用 [n] 指定数组制定索引下的元素。";

        @JSONField(name = "entity_field_expression", ordinal = 2)
        private String entityFieldExpression;

        @JSONField(name = "#field_name", ordinal = 3, deserialize = false)
        private String fieldNameRem = "字段名称，用于指定字段的名称。";

        @JSONField(name = "field_name", ordinal = 4)
        private String fieldName;

        public FieldSetting() {
        }

        public FieldSetting(String entityFieldExpression, String fieldName) {
            this.entityFieldExpression = entityFieldExpression;
            this.fieldName = fieldName;
        }

        public String getEntityFieldExpressionRem() {
            return entityFieldExpressionRem;
        }

        public void setEntityFieldExpressionRem(String entityFieldExpressionRem) {
            this.entityFieldExpressionRem = entityFieldExpressionRem;
        }

        public String getEntityFieldExpression() {
            return entityFieldExpression;
        }

        public void setEntityFieldExpression(String entityFieldExpression) {
            this.entityFieldExpression = entityFieldExpression;
        }

        public String getFieldNameRem() {
            return fieldNameRem;
        }

        public void setFieldNameRem(String fieldNameRem) {
            this.fieldNameRem = fieldNameRem;
        }

        public String getFieldName() {
            return fieldName;
        }

        public void setFieldName(String fieldName) {
            this.fieldName = fieldName;
        }

        @Override
        public String toString() {
            return "FieldSetting{" +
                    "entityFieldExpressionRem='" + entityFieldExpressionRem + '\'' +
                    ", entityFieldExpression='" + entityFieldExpression + '\'' +
                    ", fieldNameRem='" + fieldNameRem + '\'' +
                    ", fieldName='" + fieldName + '\'' +
                    '}';
        }
    }
}
