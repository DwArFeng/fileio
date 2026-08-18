package com.dwarfeng.fileio.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fileio.sdk.bean.key.WebInputTaskSettingItemKey;
import com.dwarfeng.fileio.stack.bean.dto.TemplateRemoveInfo;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * WebInput 模板移除信息。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class WebInputTemplateRemoveInfo implements Dto {

    private static final long serialVersionUID = -3263343836637953081L;

    public static TemplateRemoveInfo toStackBean(WebInputTemplateRemoveInfo webInput) {
        if (Objects.isNull(webInput)) {
            return null;
        } else {
            return new TemplateRemoveInfo(
                    WebInputTaskSettingItemKey.toStackBean(webInput.getKey())
            );
        }
    }

    @JSONField(name = "key")
    @NotNull
    @Valid
    private WebInputTaskSettingItemKey key;

    public WebInputTemplateRemoveInfo() {
    }

    public WebInputTaskSettingItemKey getKey() {
        return key;
    }

    public void setKey(WebInputTaskSettingItemKey key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "WebInputTemplateRemoveInfo{" +
                "key=" + key +
                '}';
    }
}
