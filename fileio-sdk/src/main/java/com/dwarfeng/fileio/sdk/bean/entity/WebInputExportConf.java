package com.dwarfeng.fileio.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fileio.sdk.bean.key.WebInputTaskSettingItemKey;
import com.dwarfeng.fileio.sdk.util.Constraints;
import com.dwarfeng.fileio.stack.bean.entity.ExportConf;
import com.dwarfeng.subgrade.stack.bean.Bean;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * WebInput 导出配置。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class WebInputExportConf implements Bean {

    private static final long serialVersionUID = 7732423149604419043L;

    public static ExportConf toStackBean(WebInputExportConf webInput) {
        if (Objects.isNull(webInput)) {
            return null;
        } else {
            return new ExportConf(
                    WebInputTaskSettingItemKey.toStackBean(webInput.getKey()),
                    webInput.getContent(),
                    webInput.getRemark()
            );
        }
    }

    @JSONField(name = "key")
    @NotNull
    @Valid
    private WebInputTaskSettingItemKey key;

    @JSONField(name = "content")
    private String content;

    @JSONField(name = "remark")
    @Length(max = Constraints.LENGTH_REMARK)
    private String remark;

    public WebInputExportConf() {
    }

    public WebInputTaskSettingItemKey getKey() {
        return key;
    }

    public void setKey(WebInputTaskSettingItemKey key) {
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
        return "WebInputExportConf{" +
                "key=" + key +
                ", content='" + content + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
