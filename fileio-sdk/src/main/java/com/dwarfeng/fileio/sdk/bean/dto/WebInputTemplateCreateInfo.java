package com.dwarfeng.fileio.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fileio.sdk.util.Constraints;
import com.dwarfeng.fileio.stack.bean.dto.TemplateCreateInfo;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputLongIdKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * WebInput 模板创建信息。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class WebInputTemplateCreateInfo implements Dto {

    private static final long serialVersionUID = -8797787873761338455L;

    public static TemplateCreateInfo toStackBean(WebInputTemplateCreateInfo webInput) {
        if (Objects.isNull(webInput)) {
            return null;
        } else {
            return new TemplateCreateInfo(
                    WebInputLongIdKey.toStackBean(webInput.getTaskSettingKey()),
                    webInput.getIdentifier(),
                    webInput.getOriginName()
            );
        }
    }

    @JSONField(name = "task_setting_key")
    @Valid
    private WebInputLongIdKey taskSettingKey;

    @JSONField(name = "identifier")
    @NotNull
    @NotEmpty
    @Length(max = Constraints.LENGTH_IDENTIFIER)
    private String identifier;

    @JSONField(name = "origin_name")
    private String originName;

    public WebInputTemplateCreateInfo() {
    }

    public WebInputLongIdKey getTaskSettingKey() {
        return taskSettingKey;
    }

    public void setTaskSettingKey(WebInputLongIdKey taskSettingKey) {
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
        return "WebInputTemplateCreateInfo{" +
                "taskSettingKey=" + taskSettingKey +
                ", identifier='" + identifier + '\'' +
                ", originName='" + originName + '\'' +
                '}';
    }
}
