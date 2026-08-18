package com.dwarfeng.fileio.impl.handler.exporter.csv;

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

    private static final long serialVersionUID = -5513966849122588435L;

    @JSONField(name = "#reader_identifier", ordinal = 1, deserialize = false)
    private String readerIdentifierRem = "读取器的标识符，只有当读取器的标识符与该值相同时，才会执行写入操作。";

    @JSONField(name = "reader_identifier", ordinal = 2)
    private String readerIdentifier;

    @JSONField(name = "#reader_topic", ordinal = 3, deserialize = false)
    private String readerTopicRem = "读取器的主题，只有当读取器的主题与该值相同时，才会执行写入操作。";

    @JSONField(name = "reader_topic", ordinal = 4)
    private String readerTopic;

    @JSONField(name = "#value_serializer_bean_name", ordinal = 5, deserialize = false)
    private String valueSerializerBeanNameRem = "ValueSerializer Bean 的名称。用于将字段值序列化为 CSV 文本的处理器。";

    @JSONField(name = "value_serializer_bean_name", ordinal = 6)
    private String valueSerializerBeanName;

    @JSONField(name = "#columns", ordinal = 7, deserialize = false)
    private String columnsRem = "列名，指定写入的列名及对应的字段名。";

    @JSONField(name = "columns", ordinal = 8)
    private List<Column> columns;

    @JSONField(name = "#file_identifier", ordinal = 9, deserialize = false)
    private String fileIdentifierRem = "文件标识符，指定写入的文件标识符。";

    @JSONField(name = "file_identifier", ordinal = 10)
    private String fileIdentifier;

    @JSONField(name = "#file_name", ordinal = 11, deserialize = false)
    private String fileNameRem = "文件名，指定写入的文件名。";

    @JSONField(name = "file_name", ordinal = 12)
    private String fileName;

    public Config() {
    }

    public Config(
            String readerIdentifier, String readerTopic, String valueSerializerBeanName, List<Column> columns,
            String fileIdentifier, String fileName
    ) {
        this.readerIdentifier = readerIdentifier;
        this.readerTopic = readerTopic;
        this.valueSerializerBeanName = valueSerializerBeanName;
        this.columns = columns;
        this.fileIdentifier = fileIdentifier;
        this.fileName = fileName;
    }

    public String getReaderIdentifierRem() {
        return readerIdentifierRem;
    }

    public void setReaderIdentifierRem(String readerIdentifierRem) {
        this.readerIdentifierRem = readerIdentifierRem;
    }

    public String getReaderIdentifier() {
        return readerIdentifier;
    }

    public void setReaderIdentifier(String readerIdentifier) {
        this.readerIdentifier = readerIdentifier;
    }

    public String getReaderTopicRem() {
        return readerTopicRem;
    }

    public void setReaderTopicRem(String readerTopicRem) {
        this.readerTopicRem = readerTopicRem;
    }

    public String getReaderTopic() {
        return readerTopic;
    }

    public void setReaderTopic(String readerTopic) {
        this.readerTopic = readerTopic;
    }

    public String getValueSerializerBeanNameRem() {
        return valueSerializerBeanNameRem;
    }

    public void setValueSerializerBeanNameRem(String valueSerializerBeanNameRem) {
        this.valueSerializerBeanNameRem = valueSerializerBeanNameRem;
    }

    public String getValueSerializerBeanName() {
        return valueSerializerBeanName;
    }

    public void setValueSerializerBeanName(String valueSerializerBeanName) {
        this.valueSerializerBeanName = valueSerializerBeanName;
    }

    public String getColumnsRem() {
        return columnsRem;
    }

    public void setColumnsRem(String columnsRem) {
        this.columnsRem = columnsRem;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    public String getFileIdentifierRem() {
        return fileIdentifierRem;
    }

    public void setFileIdentifierRem(String fileIdentifierRem) {
        this.fileIdentifierRem = fileIdentifierRem;
    }

    public String getFileIdentifier() {
        return fileIdentifier;
    }

    public void setFileIdentifier(String fileIdentifier) {
        this.fileIdentifier = fileIdentifier;
    }

    public String getFileNameRem() {
        return fileNameRem;
    }

    public void setFileNameRem(String fileNameRem) {
        this.fileNameRem = fileNameRem;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        return "Config{" +
                "readerIdentifierRem='" + readerIdentifierRem + '\'' +
                ", readerIdentifier='" + readerIdentifier + '\'' +
                ", readerTopicRem='" + readerTopicRem + '\'' +
                ", readerTopic='" + readerTopic + '\'' +
                ", valueSerializerBeanNameRem='" + valueSerializerBeanNameRem + '\'' +
                ", valueSerializerBeanName='" + valueSerializerBeanName + '\'' +
                ", columnsRem='" + columnsRem + '\'' +
                ", columns=" + columns +
                ", fileIdentifierRem='" + fileIdentifierRem + '\'' +
                ", fileIdentifier='" + fileIdentifier + '\'' +
                ", fileNameRem='" + fileNameRem + '\'' +
                ", fileName='" + fileName + '\'' +
                '}';
    }

    /**
     * 列。
     *
     * @author DwArFeng
     * @since 1.0.0
     */
    public static class Column implements Bean {

        private static final long serialVersionUID = 2155810296489400782L;

        @JSONField(name = "#column_name", ordinal = 1, deserialize = false)
        private String columnNameRem = "列名，指定写入的列名，即 CSV Header 中的列名。";

        @JSONField(name = "column_name", ordinal = 2)
        private String columnName;

        @JSONField(name = "#field_name", ordinal = 3, deserialize = false)
        private String fieldNameRem = "字段名，指从数据中取值的字段名。";

        @JSONField(name = "field_name", ordinal = 4)
        private String fieldName;

        public Column() {
        }

        public Column(String columnName, String fieldName) {
            this.columnName = columnName;
            this.fieldName = fieldName;
        }

        public String getColumnNameRem() {
            return columnNameRem;
        }

        public void setColumnNameRem(String columnNameRem) {
            this.columnNameRem = columnNameRem;
        }

        public String getColumnName() {
            return columnName;
        }

        public void setColumnName(String columnName) {
            this.columnName = columnName;
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
            return "Column{" +
                    "columnNameRem='" + columnNameRem + '\'' +
                    ", columnName='" + columnName + '\'' +
                    ", fieldNameRem='" + fieldNameRem + '\'' +
                    ", fieldName='" + fieldName + '\'' +
                    '}';
        }
    }
}
