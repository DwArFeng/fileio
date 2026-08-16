package com.dwarfeng.fileio.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fileio.sdk.bean.key.WebInputTaskSettingItemKey;
import com.dwarfeng.fileio.sdk.util.Constraints;
import com.dwarfeng.fileio.stack.bean.entity.ReaderInfo;
import com.dwarfeng.subgrade.stack.bean.Bean;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * WebInput 读取器信息。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class WebInputReaderInfo implements Bean {

    private static final long serialVersionUID = -1278155971898301124L;

    public static ReaderInfo toStackBean(WebInputReaderInfo webInput) {
        if (Objects.isNull(webInput)) {
            return null;
        } else {
            return new ReaderInfo(
                    WebInputTaskSettingItemKey.toStackBean(webInput.getKey()),
                    webInput.isEnabled(),
                    webInput.getType(),
                    webInput.getParam(),
                    webInput.getRemark()
            );
        }
    }

    @JSONField(name = "key")
    @NotNull
    @Valid
    private WebInputTaskSettingItemKey key;

    @JSONField(name = "enabled")
    private boolean enabled;

    @JSONField(name = "type")
    @NotNull
    @NotEmpty
    @Length(max = Constraints.LENGTH_TYPE)
    private String type;

    @JSONField(name = "param")
    private String param;

    @JSONField(name = "remark")
    @Length(max = Constraints.LENGTH_REMARK)
    private String remark;

    public WebInputReaderInfo() {
    }

    public WebInputTaskSettingItemKey getKey() {
        return key;
    }

    public void setKey(WebInputTaskSettingItemKey key) {
        this.key = key;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "WebInputReaderInfo{" +
                "key=" + key +
                ", enabled=" + enabled +
                ", type='" + type + '\'' +
                ", param='" + param + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
