package com.dwarfeng.fileio.impl.handler.importer.dct;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.subgrade.stack.bean.Bean;

/**
 * 配置。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class Config implements Bean {

    private static final long serialVersionUID = 602836467895614075L;

    @JSONField(name = "#file_identifier", ordinal = 1, deserialize = false)
    private String fileIdentifierRem = "文件标识符，指定导入的文件标识符。";

    @JSONField(name = "file_identifier", ordinal = 2)
    private String fileIdentifier;

    @JSONField(name = "#batch_size", ordinal = 3, deserialize = false)
    private String batchSizeRem = "批量大小, 用于指定每次生成的数据的数量。";

    @JSONField(name = "batch_size", ordinal = 4)
    private int batchSize;

    @JSONField(name = "#value_coding_handler_bean_name", ordinal = 5, deserialize = false)
    private String valueCodingHandlerBeanNameRem = "ValueCodingHandler Bean 的名称。" +
            "用于将 DCT 文件内容解码为 Java 对象的处理器。";

    @JSONField(name = "value_coding_handler_bean_name", ordinal = 6)
    private String valueCodingHandlerBeanName;

    public Config() {
    }

    public Config(String fileIdentifier, int batchSize, String valueCodingHandlerBeanName) {
        this.fileIdentifier = fileIdentifier;
        this.batchSize = batchSize;
        this.valueCodingHandlerBeanName = valueCodingHandlerBeanName;
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

    public String getValueCodingHandlerBeanNameRem() {
        return valueCodingHandlerBeanNameRem;
    }

    public void setValueCodingHandlerBeanNameRem(String valueCodingHandlerBeanNameRem) {
        this.valueCodingHandlerBeanNameRem = valueCodingHandlerBeanNameRem;
    }

    public String getValueCodingHandlerBeanName() {
        return valueCodingHandlerBeanName;
    }

    public void setValueCodingHandlerBeanName(String valueCodingHandlerBeanName) {
        this.valueCodingHandlerBeanName = valueCodingHandlerBeanName;
    }

    @Override
    public String toString() {
        return "Config{" +
                "fileIdentifierRem='" + fileIdentifierRem + '\'' +
                ", fileIdentifier='" + fileIdentifier + '\'' +
                ", batchSizeRem='" + batchSizeRem + '\'' +
                ", batchSize=" + batchSize +
                ", valueCodingHandlerBeanNameRem='" + valueCodingHandlerBeanNameRem + '\'' +
                ", valueCodingHandlerBeanName='" + valueCodingHandlerBeanName + '\'' +
                '}';
    }
}
