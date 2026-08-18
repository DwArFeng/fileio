package com.dwarfeng.fileio.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fileio.sdk.bean.key.WebInputTaskItemKey;
import com.dwarfeng.fileio.stack.bean.dto.MetadataUpsertInfo;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * WebInput 元数据插入/更新信息。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class WebInputMetadataUpsertInfo implements Dto {

    private static final long serialVersionUID = 7593293994194172457L;

    public static MetadataUpsertInfo toStackBean(WebInputMetadataUpsertInfo webInput) {
        if (Objects.isNull(webInput)) {
            return null;
        } else {
            return new MetadataUpsertInfo(
                    WebInputTaskItemKey.toStackBean(webInput.getKey()),
                    webInput.getContent()
            );
        }
    }

    @JSONField(name = "key")
    @NotNull
    @Valid
    private WebInputTaskItemKey key;

    @JSONField(name = "content")
    private String content;

    public WebInputMetadataUpsertInfo() {
    }

    public WebInputTaskItemKey getKey() {
        return key;
    }

    public void setKey(WebInputTaskItemKey key) {
        this.key = key;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "WebInputMetadataUpsertInfo{" +
                "key=" + key +
                ", content='" + content + '\'' +
                '}';
    }
}
