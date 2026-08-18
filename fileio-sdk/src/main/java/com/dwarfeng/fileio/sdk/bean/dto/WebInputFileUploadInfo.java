package com.dwarfeng.fileio.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fileio.sdk.bean.key.WebInputTaskItemKey;
import com.dwarfeng.fileio.stack.bean.dto.FileUploadInfo;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Objects;

/**
 * WebInput 文件上传信息。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class WebInputFileUploadInfo implements Dto {

    private static final long serialVersionUID = 5809505027037520496L;

    public static FileUploadInfo toStackBean(WebInputFileUploadInfo webInput) {
        if (Objects.isNull(webInput)) {
            return null;
        } else {
            return new FileUploadInfo(
                    WebInputTaskItemKey.toStackBean(webInput.getKey()),
                    webInput.getOriginName(),
                    webInput.getContent()
            );
        }
    }

    @JSONField(name = "key")
    @NotNull
    @Valid
    private WebInputTaskItemKey key;

    @JSONField(name = "origin_name")
    @NotNull
    @NotEmpty
    private String originName;

    @JSONField(name = "content")
    @NotNull
    private byte[] content;

    public WebInputFileUploadInfo() {
    }

    public WebInputTaskItemKey getKey() {
        return key;
    }

    public void setKey(WebInputTaskItemKey key) {
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
        return "WebInputFileUploadInfo{" +
                "key=" + key +
                ", originName='" + originName + '\'' +
                ", content=" + Arrays.toString(content) +
                '}';
    }
}
