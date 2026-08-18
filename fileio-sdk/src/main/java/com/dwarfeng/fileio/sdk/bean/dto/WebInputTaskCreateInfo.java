package com.dwarfeng.fileio.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fileio.sdk.util.Constraints;
import com.dwarfeng.fileio.stack.bean.dto.TaskCreateInfo;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputLongIdKey;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputStringIdKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * WebInput 任务创建信息。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class WebInputTaskCreateInfo implements Dto {

    private static final long serialVersionUID = 6776519561146668171L;

    public static TaskCreateInfo toStackBean(WebInputTaskCreateInfo webInput) {
        if (Objects.isNull(webInput)) {
            return null;
        } else {
            return new TaskCreateInfo(
                    WebInputLongIdKey.toStackBean(webInput.getSettingKey()),
                    WebInputStringIdKey.toStackBean(webInput.getUserKey()),
                    webInput.getMessage()
            );
        }
    }

    @JSONField(name = "setting_key")
    @NotNull
    @Valid
    private WebInputLongIdKey settingKey;

    @JSONField(name = "user_key")
    @Valid
    private WebInputStringIdKey userKey;

    @JSONField(name = "message")
    @Length(max = Constraints.LENGTH_MESSAGE)
    private String message;

    public WebInputTaskCreateInfo() {
    }

    public WebInputLongIdKey getSettingKey() {
        return settingKey;
    }

    public void setSettingKey(WebInputLongIdKey settingKey) {
        this.settingKey = settingKey;
    }

    public WebInputStringIdKey getUserKey() {
        return userKey;
    }

    public void setUserKey(WebInputStringIdKey userKey) {
        this.userKey = userKey;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "WebInputTaskCreateInfo{" +
                "settingKey=" + settingKey +
                ", userKey=" + userKey +
                ", message='" + message + '\'' +
                '}';
    }
}
