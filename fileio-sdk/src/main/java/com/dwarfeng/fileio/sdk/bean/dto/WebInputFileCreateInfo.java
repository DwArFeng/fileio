package com.dwarfeng.fileio.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fileio.sdk.util.Constraints;
import com.dwarfeng.fileio.stack.bean.dto.FileCreateInfo;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputLongIdKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * WebInput 文件创建信息。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class WebInputFileCreateInfo implements Dto {

    private static final long serialVersionUID = -8370768492114826617L;

    public static FileCreateInfo toStackBean(WebInputFileCreateInfo webInput) {
        if (Objects.isNull(webInput)) {
            return null;
        } else {
            return new FileCreateInfo(
                    WebInputLongIdKey.toStackBean(webInput.getTaskKey()),
                    webInput.getIdentifier(),
                    webInput.getOriginName()
            );
        }
    }

    @JSONField(name = "task_key")
    @Valid
    private WebInputLongIdKey taskKey;

    @JSONField(name = "identifier")
    @NotNull
    @NotEmpty
    @Length(max = Constraints.LENGTH_IDENTIFIER)
    private String identifier;

    @JSONField(name = "origin_name")
    private String originName;

    public WebInputFileCreateInfo() {
    }

    public WebInputLongIdKey getTaskKey() {
        return taskKey;
    }

    public void setTaskKey(WebInputLongIdKey taskKey) {
        this.taskKey = taskKey;
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
        return "WebInputFileCreateInfo{" +
                "taskKey=" + taskKey +
                ", identifier='" + identifier + '\'' +
                ", originName='" + originName + '\'' +
                '}';
    }
}
