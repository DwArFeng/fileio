package com.dwarfeng.fileio.stack.bean.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;

/**
 * 文件流获取信息。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class FileStreamRetrieveInfo implements Dto {

    private static final long serialVersionUID = -3114129665202897054L;

    private String identifier;

    public FileStreamRetrieveInfo() {
    }

    public FileStreamRetrieveInfo(String identifier) {
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
        return "FileStreamRetrieveInfo{" +
                "identifier='" + identifier + '\'' +
                '}';
    }
}
