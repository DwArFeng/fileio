package com.dwarfeng.fileio.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fileio.sdk.bean.key.WebInputTaskItemKey;
import com.dwarfeng.fileio.stack.bean.dto.FileRemoveInfo;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * WebInput 文件移除信息。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class WebInputFileRemoveInfo implements Dto {

    private static final long serialVersionUID = -810537625848707800L;

    public static FileRemoveInfo toStackBean(WebInputFileRemoveInfo webInput) {
        if (Objects.isNull(webInput)) {
            return null;
        } else {
            return new FileRemoveInfo(
                    WebInputTaskItemKey.toStackBean(webInput.getKey())
            );
        }
    }

    @JSONField(name = "key")
    @NotNull
    @Valid
    private WebInputTaskItemKey key;

    public WebInputFileRemoveInfo() {
    }

    public WebInputTaskItemKey getKey() {
        return key;
    }

    public void setKey(WebInputTaskItemKey key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "WebInputFileRemoveInfo{" +
                "key=" + key +
                '}';
    }
}
