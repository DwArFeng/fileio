package com.dwarfeng.fileio.stack.bean.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Arrays;

/**
 * 模板。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class Template implements Dto {

    private static final long serialVersionUID = 3673603109301587019L;

    private String originName;
    private byte[] content;

    public Template() {
    }

    public Template(String originName, byte[] content) {
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
        return "Template{" +
                "originName='" + originName + '\'' +
                ", content=" + Arrays.toString(content) +
                '}';
    }
}
