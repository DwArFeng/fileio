package com.dwarfeng.fileio.stack.bean.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.io.InputStream;

/**
 * 模板流。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class TemplateStream implements Dto {

    private static final long serialVersionUID = -2025710435337798246L;

    private String originName;
    private long length;
    private InputStream content;

    public TemplateStream() {
    }

    public TemplateStream(String originName, long length, InputStream content) {
        this.originName = originName;
        this.length = length;
        this.content = content;
    }

    public String getOriginName() {
        return originName;
    }

    public void setOriginName(String originName) {
        this.originName = originName;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public InputStream getContent() {
        return content;
    }

    public void setContent(InputStream content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "TemplateStream{" +
                "originName='" + originName + '\'' +
                ", length=" + length +
                ", content=" + content +
                '}';
    }
}
