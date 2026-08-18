package com.dwarfeng.fileio.stack.bean.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;

/**
 * 模板获取信息。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class TemplateRetrieveInfo implements Dto {

    private static final long serialVersionUID = -7019347638832997394L;

    private String identifier;

    public TemplateRetrieveInfo() {
    }

    public TemplateRetrieveInfo(String identifier) {
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
        return "TemplateRetrieveInfo{" +
                "identifier='" + identifier + '\'' +
                '}';
    }
}
