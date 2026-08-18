package com.dwarfeng.fileio.impl.handler.exporter.excel;

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

    private static final long serialVersionUID = -4905142150840136145L;

    @JSONField(name = "#template_identifier", ordinal = 1, deserialize = false)
    private String templateIdentifierRem = "模板标识符，指定写入的模板标识符。" +
            "模板文件是一个 excel 文件，其中包含了写入的格式，excel 文件可以是 xls 或 xlsx 格式。" +
            "模板文件的格式决定了导出文件的格式。";

    @JSONField(name = "template_identifier", ordinal = 2)
    private String templateIdentifier;

    @JSONField(name = "#file_identifier", ordinal = 3, deserialize = false)
    private String fileIdentifierRem = "文件标识符，指定写入的文件标识符。";

    @JSONField(name = "file_identifier", ordinal = 4)
    private String fileIdentifier;

    @JSONField(name = "#file_name", ordinal = 5, deserialize = false)
    private String fileNameRem = "文件名，指定写入的文件名。";

    @JSONField(name = "file_name", ordinal = 6)
    private String fileName;

    @JSONField(name = "#sheet_setting", ordinal = 7, deserialize = false)
    private String sheetSettingRem = "工作簿设置，定义每个工作簿的导出规则。";

    @JSONField(name = "sheet_settings", ordinal = 8)
    private List<SheetSetting> sheetSettings;

    public Config() {
    }

    public Config(
            String templateIdentifier, String fileIdentifier, String fileName, List<SheetSetting> sheetSettings
    ) {
        this.templateIdentifier = templateIdentifier;
        this.fileIdentifier = fileIdentifier;
        this.fileName = fileName;
        this.sheetSettings = sheetSettings;
    }

    public String getTemplateIdentifierRem() {
        return templateIdentifierRem;
    }

    public void setTemplateIdentifierRem(String templateIdentifierRem) {
        this.templateIdentifierRem = templateIdentifierRem;
    }

    public String getTemplateIdentifier() {
        return templateIdentifier;
    }

    public void setTemplateIdentifier(String templateIdentifier) {
        this.templateIdentifier = templateIdentifier;
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

    public String getSheetSettingRem() {
        return sheetSettingRem;
    }

    public void setSheetSettingRem(String sheetSettingRem) {
        this.sheetSettingRem = sheetSettingRem;
    }

    public List<SheetSetting> getSheetSettings() {
        return sheetSettings;
    }

    public void setSheetSettings(List<SheetSetting> sheetSettings) {
        this.sheetSettings = sheetSettings;
    }

    @Override
    public String toString() {
        return "Config{" +
                "templateIdentifierRem='" + templateIdentifierRem + '\'' +
                ", templateIdentifier='" + templateIdentifier + '\'' +
                ", fileIdentifierRem='" + fileIdentifierRem + '\'' +
                ", fileIdentifier='" + fileIdentifier + '\'' +
                ", fileNameRem='" + fileNameRem + '\'' +
                ", fileName='" + fileName + '\'' +
                ", sheetSettingRem='" + sheetSettingRem + '\'' +
                ", sheetSettings=" + sheetSettings +
                '}';
    }

    public static class SheetSetting implements Bean {

        private static final long serialVersionUID = 8716941343580386607L;

        @JSONField(name = "#reader_identifier", ordinal = 1, deserialize = false)
        private String readerIdentifierRem = "读取器的标识符，只有当读取器的标识符与该值相同时，才会执行写入操作。";

        @JSONField(name = "reader_identifier", ordinal = 2)
        private String readerIdentifier;

        @JSONField(name = "#reader_topic", ordinal = 3, deserialize = false)
        private String readerTopicRem = "读取器的主题，只有当读取器的主题与该值相同时，才会执行写入操作。";

        @JSONField(name = "reader_topic", ordinal = 4)
        private String readerTopic;

        @JSONField(name = "#sheet_index", ordinal = 5, deserialize = false)
        private String sheetIndexRem = "工作簿的索引，以 0 开始。";

        @JSONField(name = "sheet_index", ordinal = 6)
        private int sheetIndex;

        @JSONField(name = "#first_data_row", ordinal = 7, deserialize = false)
        private String firstDataRowRem = "数据的起始行，即第一条数据所在的行，以 0 开始。";

        @JSONField(name = "first_data_row", ordinal = 8)
        private int firstDataRow;

        @JSONField(name = "#column_settings", ordinal = 9, deserialize = false)
        private String columnSettingsRem = "列设置，定义每一列的导出规则。";

        @JSONField(name = "column_settings", ordinal = 10)
        private List<ColumnSetting> columnSettings;

        public SheetSetting() {
        }

        public SheetSetting(
                String readerIdentifier, String readerTopic, int sheetIndex, int firstDataRow,
                List<ColumnSetting> columnSettings
        ) {
            this.readerIdentifier = readerIdentifier;
            this.readerTopic = readerTopic;
            this.sheetIndex = sheetIndex;
            this.firstDataRow = firstDataRow;
            this.columnSettings = columnSettings;
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

        public String getSheetIndexRem() {
            return sheetIndexRem;
        }

        public void setSheetIndexRem(String sheetIndexRem) {
            this.sheetIndexRem = sheetIndexRem;
        }

        public int getSheetIndex() {
            return sheetIndex;
        }

        public void setSheetIndex(int sheetIndex) {
            this.sheetIndex = sheetIndex;
        }

        public String getFirstDataRowRem() {
            return firstDataRowRem;
        }

        public void setFirstDataRowRem(String firstDataRowRem) {
            this.firstDataRowRem = firstDataRowRem;
        }

        public int getFirstDataRow() {
            return firstDataRow;
        }

        public void setFirstDataRow(int firstDataRow) {
            this.firstDataRow = firstDataRow;
        }

        public String getColumnSettingsRem() {
            return columnSettingsRem;
        }

        public void setColumnSettingsRem(String columnSettingsRem) {
            this.columnSettingsRem = columnSettingsRem;
        }

        public List<ColumnSetting> getColumnSettings() {
            return columnSettings;
        }

        public void setColumnSettings(List<ColumnSetting> columnSettings) {
            this.columnSettings = columnSettings;
        }

        @Override
        public String toString() {
            return "SheetSetting{" +
                    "readerIdentifierRem='" + readerIdentifierRem + '\'' +
                    ", readerIdentifier='" + readerIdentifier + '\'' +
                    ", readerTopicRem='" + readerTopicRem + '\'' +
                    ", readerTopic='" + readerTopic + '\'' +
                    ", sheetIndexRem='" + sheetIndexRem + '\'' +
                    ", sheetIndex=" + sheetIndex +
                    ", firstDataRowRem='" + firstDataRowRem + '\'' +
                    ", firstDataRow=" + firstDataRow +
                    ", columnSettingsRem='" + columnSettingsRem + '\'' +
                    ", columnSettings=" + columnSettings +
                    '}';
        }
    }

    public static class ColumnSetting implements Bean {

        private static final long serialVersionUID = -6353004220683464195L;

        @JSONField(name = "#field_name", ordinal = 1, deserialize = false)
        private String fieldNameRem = "字段名，指从数据中取值的字段名。";

        @JSONField(name = "field_name", ordinal = 2)
        private String fieldName;

        @JSONField(name = "#column_name", ordinal = 3, deserialize = false)
        private String columnNameRem = "列名，指向目标工作簿写入的列名。" +
                "该值与 Excel 中的列名对应，即 A、B、C、AA、AB 等。";

        @JSONField(name = "column_name", ordinal = 4)
        private String columnName;

        @JSONField(name = "#column_type", ordinal = 5, deserialize = false)
        private String columnTypeRem = "列类型，指定写入的列的类型。" +
                "该值决定了写入的数据的格式。" +
                "可选值有：0 - 布尔；1 - 日期；2 - 双精度浮点数；3 - 本地日期；4 - 本地日期时间；5 - 文本。";

        @JSONField(name = "column_type", ordinal = 6)
        private int columnType;

        @JSONField(name = "#value_mapper_bean_name", ordinal = 7, deserialize = false)
        private String valueMapperBeanNameRem = "ValueMapper Bean 的名称。用于将字段值转换为目标单元格类型的映射器。";

        @JSONField(name = "value_mapper_bean_name", ordinal = 8)
        private String valueMapperBeanName;

        public ColumnSetting() {
        }

        public ColumnSetting(String fieldName, String columnName, int columnType, String valueMapperBeanName) {
            this.fieldName = fieldName;
            this.columnName = columnName;
            this.columnType = columnType;
            this.valueMapperBeanName = valueMapperBeanName;
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

        public String getColumnTypeRem() {
            return columnTypeRem;
        }

        public void setColumnTypeRem(String columnTypeRem) {
            this.columnTypeRem = columnTypeRem;
        }

        public int getColumnType() {
            return columnType;
        }

        public void setColumnType(int columnType) {
            this.columnType = columnType;
        }

        public String getValueMapperBeanNameRem() {
            return valueMapperBeanNameRem;
        }

        public void setValueMapperBeanNameRem(String valueMapperBeanNameRem) {
            this.valueMapperBeanNameRem = valueMapperBeanNameRem;
        }

        public String getValueMapperBeanName() {
            return valueMapperBeanName;
        }

        public void setValueMapperBeanName(String valueMapperBeanName) {
            this.valueMapperBeanName = valueMapperBeanName;
        }

        @Override
        public String toString() {
            return "ColumnSetting{" +
                    "fieldNameRem='" + fieldNameRem + '\'' +
                    ", fieldName='" + fieldName + '\'' +
                    ", columnNameRem='" + columnNameRem + '\'' +
                    ", columnName='" + columnName + '\'' +
                    ", columnTypeRem='" + columnTypeRem + '\'' +
                    ", columnType=" + columnType +
                    ", valueMapperBeanNameRem='" + valueMapperBeanNameRem + '\'' +
                    ", valueMapperBeanName='" + valueMapperBeanName + '\'' +
                    '}';
        }
    }
}
