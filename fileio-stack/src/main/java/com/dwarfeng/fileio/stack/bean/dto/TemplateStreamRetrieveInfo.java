package com.dwarfeng.fileio.stack.bean.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;

/**
 * 模板获取信息。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class TemplateStreamRetrieveInfo implements Dto {

    private static final long serialVersionUID = 4341011117057770510L;

    private String identifier;

    public TemplateStreamRetrieveInfo() {
    }

    public TemplateStreamRetrieveInfo(String identifier) {
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
        return "TemplateStreamRetrieveInfo{" +
                "identifier='" + identifier + '\'' +
                '}';
    }
}
