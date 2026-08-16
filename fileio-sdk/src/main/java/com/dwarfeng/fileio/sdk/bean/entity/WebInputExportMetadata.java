package com.dwarfeng.fileio.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fileio.sdk.bean.key.WebInputTaskItemKey;
import com.dwarfeng.fileio.sdk.util.Constraints;
import com.dwarfeng.fileio.stack.bean.entity.ExportMetadata;
import com.dwarfeng.subgrade.stack.bean.Bean;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * WebInput 导出元数据。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class WebInputExportMetadata implements Bean {

    private static final long serialVersionUID = -4752341884036649655L;

    public static ExportMetadata toStackBean(WebInputExportMetadata webInput) {
        if (Objects.isNull(webInput)) {
            return null;
        } else {
            return new ExportMetadata(
                    WebInputTaskItemKey.toStackBean(webInput.getKey()),
                    webInput.getContent(),
                    webInput.getRemark()
            );
        }
    }

    @JSONField(name = "key")
    @NotNull
    @Valid
    private WebInputTaskItemKey key;

    @JSONField(name = "content")
    private String content;

    @JSONField(name = "remark")
    @Length(max = Constraints.LENGTH_REMARK)
    private String remark;

    public WebInputExportMetadata() {
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "WebInputExportMetadata{" +
                "key=" + key +
                ", content='" + content + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
