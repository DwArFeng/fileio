package com.dwarfeng.fileio.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fileio.sdk.bean.key.WebInputTaskSettingItemKey;
import com.dwarfeng.fileio.stack.bean.dto.TemplateDownloadInfo;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * WebInput 模板下载信息。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class WebInputTemplateDownloadInfo implements Dto {

    private static final long serialVersionUID = 2954301987948415598L;

    public static TemplateDownloadInfo toStackBean(WebInputTemplateDownloadInfo webInput) {
        if (Objects.isNull(webInput)) {
            return null;
        } else {
            return new TemplateDownloadInfo(
                    WebInputTaskSettingItemKey.toStackBean(webInput.getKey())
            );
        }
    }

    @JSONField(name = "key")
    @NotNull
    @Valid
    private WebInputTaskSettingItemKey key;

    public WebInputTemplateDownloadInfo() {
    }

    public WebInputTaskSettingItemKey getKey() {
        return key;
    }

    public void setKey(WebInputTaskSettingItemKey key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "WebInputTemplateDownloadInfo{" +
                "key=" + key +
                '}';
    }
}
