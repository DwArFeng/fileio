package com.dwarfeng.fileio.stack.bean.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.io.InputStream;

/**
 * 文件流存储信息。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class FileStreamStoreInfo implements Dto {

    private static final long serialVersionUID = 1374387250599253766L;

    private String identifier;
    private String originName;
    private long length;
    private InputStream content;

    public FileStreamStoreInfo() {
    }

    public FileStreamStoreInfo(String identifier, String originName, long length, InputStream content) {
        this.identifier = identifier;
        this.originName = originName;
        this.length = length;
        this.content = content;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
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
        return "FileStreamStoreInfo{" +
                "identifier='" + identifier + '\'' +
                ", originName='" + originName + '\'' +
                ", length=" + length +
                ", content=" + content +
                '}';
    }
}
