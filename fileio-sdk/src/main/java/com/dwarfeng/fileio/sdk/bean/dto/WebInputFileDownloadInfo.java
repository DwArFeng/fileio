package com.dwarfeng.fileio.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fileio.sdk.bean.key.WebInputTaskItemKey;
import com.dwarfeng.fileio.stack.bean.dto.FileDownloadInfo;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * WebInput 文件下载信息。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class WebInputFileDownloadInfo implements Dto {

    private static final long serialVersionUID = -8663674119829878581L;

    public static FileDownloadInfo toStackBean(WebInputFileDownloadInfo webInput) {
        if (Objects.isNull(webInput)) {
            return null;
        } else {
            return new FileDownloadInfo(
                    WebInputTaskItemKey.toStackBean(webInput.getKey())
            );
        }
    }

    @JSONField(name = "key")
    @NotNull
    @Valid
    private WebInputTaskItemKey key;

    public WebInputFileDownloadInfo() {
    }

    public WebInputTaskItemKey getKey() {
        return key;
    }

    public void setKey(WebInputTaskItemKey key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "WebInputFileDownloadInfo{" +
                "key=" + key +
                '}';
    }
}
