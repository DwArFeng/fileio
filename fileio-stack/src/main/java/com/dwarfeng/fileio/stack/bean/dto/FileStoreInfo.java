package com.dwarfeng.fileio.stack.bean.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Arrays;

/**
 * 文件存储信息。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class FileStoreInfo implements Dto {

    private static final long serialVersionUID = -6286775810609643467L;

    private String identifier;
    private String originName;
    private byte[] content;

    public FileStoreInfo() {
    }

    public FileStoreInfo(String identifier, String originName, byte[] content) {
        this.identifier = identifier;
        this.originName = originName;
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

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "FileStoreInfo{" +
                "identifier='" + identifier + '\'' +
                ", originName='" + originName + '\'' +
                ", content=" + Arrays.toString(content) +
                '}';
    }
}
