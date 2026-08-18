package com.dwarfeng.fileio.stack.bean.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

/**
 * 模板创建信息。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class TemplateCreateInfo implements Dto {

    private static final long serialVersionUID = -4904925803743227181L;

    private LongIdKey taskSettingKey;
    private String identifier;
    private String originName;

    public TemplateCreateInfo() {
    }

    public TemplateCreateInfo(LongIdKey taskSettingKey, String identifier, String originName) {
        this.taskSettingKey = taskSettingKey;
        this.identifier = identifier;
        this.originName = originName;
    }

    public LongIdKey getTaskSettingKey() {
        return taskSettingKey;
    }

    public void setTaskSettingKey(LongIdKey taskSettingKey) {
        this.taskSettingKey = taskSettingKey;
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

    @Override
    public String toString() {
        return "TemplateCreateInfo{" +
                "taskSettingKey=" + taskSettingKey +
                ", identifier='" + identifier + '\'' +
                ", originName='" + originName + '\'' +
                '}';
    }
}
