package com.dwarfeng.fileio.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fileio.sdk.bean.key.WebInputTaskSettingItemKey;
import com.dwarfeng.fileio.stack.bean.dto.TemplateUploadInfo;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Objects;

/**
 * WebInput 模板上传信息。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class WebInputTemplateUploadInfo implements Dto {

    private static final long serialVersionUID = 697839364977306099L;

    public static TemplateUploadInfo toStackBean(WebInputTemplateUploadInfo webInput) {
        if (Objects.isNull(webInput)) {
            return null;
        } else {
            return new TemplateUploadInfo(
                    WebInputTaskSettingItemKey.toStackBean(webInput.getKey()),
                    webInput.getOriginName(),
                    webInput.getContent()
            );
        }
    }

    @JSONField(name = "key")
    @NotNull
    @Valid
    private WebInputTaskSettingItemKey key;

    @JSONField(name = "origin_name")
    @NotNull
    @NotEmpty
    private String originName;

    @JSONField(name = "content")
    @NotNull
    private byte[] content;

    public WebInputTemplateUploadInfo() {
    }

    public WebInputTaskSettingItemKey getKey() {
        return key;
    }

    public void setKey(WebInputTaskSettingItemKey key) {
        this.key = key;
    }

    public String getOriginName() {
        return originName;
    }

    public void setOriginName(String originName) {
        this.originName = originName;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "WebInputTemplateUploadInfo{" +
                "key=" + key +
                ", originName='" + originName + '\'' +
                ", content=" + Arrays.toString(content) +
                '}';
    }
}
