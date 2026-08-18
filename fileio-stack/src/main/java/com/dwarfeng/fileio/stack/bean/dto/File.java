package com.dwarfeng.fileio.stack.bean.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Arrays;

/**
 * 文件。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class File implements Dto {

    private static final long serialVersionUID = -6516371845685109218L;

    private String originName;
    private byte[] content;

    public File() {
    }

    public File(String originName, byte[] content) {
        this.originName = originName;
        this.content = content;
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
        return "File{" +
                "originName='" + originName + '\'' +
                ", content=" + Arrays.toString(content) +
                '}';
    }
}
