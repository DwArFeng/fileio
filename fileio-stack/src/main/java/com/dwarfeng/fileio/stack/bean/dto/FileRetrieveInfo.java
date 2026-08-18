package com.dwarfeng.fileio.stack.bean.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;

/**
 * 文件获取信息。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class FileRetrieveInfo implements Dto {

    private static final long serialVersionUID = -5714823639440158002L;

    private String identifier;

    public FileRetrieveInfo() {
    }

    public FileRetrieveInfo(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    @Override
    public String toString() {
        return "FileRetrieveInfo{" +
                "identifier='" + identifier + '\'' +
                '}';
    }
}
