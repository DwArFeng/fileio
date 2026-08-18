package com.dwarfeng.fileio.impl.handler.exporter.dct;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.subgrade.stack.bean.Bean;

/**
 * 配置。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class Config implements Bean {

    private static final long serialVersionUID = -6565684303304191787L;

    @JSONField(name = "#reader_identifier", ordinal = 1, deserialize = false)
    private String readerIdentifierRem = "读取器的标识符，只有当读取器的标识符与该值相同时，才会执行写入操作。";

    @JSONField(name = "reader_identifier", ordinal = 2)
    private String readerIdentifier;

    @JSONField(name = "#reader_topic", ordinal = 3, deserialize = false)
    private String readerTopicRem = "读取器的主题，只有当读取器的主题与该值相同时，才会执行写入操作。";

    @JSONField(name = "reader_topic", ordinal = 4)
    private String readerTopic;

    @JSONField(name = "#file_identifier", ordinal = 5, deserialize = false)
    private String fileIdentifierRem = "文件标识符，指定写入的文件标识符。";

    @JSONField(name = "file_identifier", ordinal = 6)
    private String fileIdentifier;

    @JSONField(name = "#file_name", ordinal = 7, deserialize = false)
    private String fileNameRem = "文件名，指定写入的文件名。";

    @JSONField(name = "file_name", ordinal = 8)
    private String fileName;

    @JSONField(name = "#value_coding_handler_bean_name", ordinal = 9, deserialize = false)
    private String valueCodingHandlerBeanNameRem = "ValueCodingHandler Bean 的名称。用于将读取结果编码为 DCT 文本的处理器。";

    @JSONField(name = "value_coding_handler_bean_name", ordinal = 10)
    private String valueCodingHandlerBeanName;

    public Config() {
    }

    public Config(
            String readerIdentifier, String readerTopic, String fileIdentifier, String fileName,
            String valueCodingHandlerBeanName
    ) {
        this.readerIdentifier = readerIdentifier;
        this.readerTopic = readerTopic;
        this.fileIdentifier = fileIdentifier;
        this.fileName = fileName;
        this.valueCodingHandlerBeanName = valueCodingHandlerBeanName;
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
                "readerIdentifierRem='" + readerIdentifierRem + '\'' +
                ", readerIdentifier='" + readerIdentifier + '\'' +
                ", readerTopicRem='" + readerTopicRem + '\'' +
                ", readerTopic='" + readerTopic + '\'' +
                ", fileIdentifierRem='" + fileIdentifierRem + '\'' +
                ", fileIdentifier='" + fileIdentifier + '\'' +
                ", fileNameRem='" + fileNameRem + '\'' +
                ", fileName='" + fileName + '\'' +
                ", valueCodingHandlerBeanNameRem='" + valueCodingHandlerBeanNameRem + '\'' +
                ", valueCodingHandlerBeanName='" + valueCodingHandlerBeanName + '\'' +
                '}';
    }
}
