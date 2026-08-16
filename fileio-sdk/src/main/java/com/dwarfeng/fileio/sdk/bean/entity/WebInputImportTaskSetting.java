package com.dwarfeng.fileio.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fileio.sdk.util.Constraints;
import com.dwarfeng.fileio.stack.bean.entity.ImportTaskSetting;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * WebInput 导入任务设置。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class WebInputImportTaskSetting implements Bean {

    private static final long serialVersionUID = -1571828517649646937L;

    public static ImportTaskSetting toStackBean(WebInputImportTaskSetting webInput) {
        if (Objects.isNull(webInput)) {
            return null;
        } else {
            return new ImportTaskSetting(
                    WebInputLongIdKey.toStackBean(webInput.getKey()),
                    webInput.isEnabled(),
                    webInput.getName(),
                    webInput.getRemark()
            );
        }
    }

    @JSONField(name = "key")
    @Valid
    private WebInputLongIdKey key;

    @JSONField(name = "enabled")
    private boolean enabled;

    @JSONField(name = "name")
    @NotNull
    @NotEmpty
    @Length(max = Constraints.LENGTH_NAME)
    private String name;

    @JSONField(name = "remark")
    @Length(max = Constraints.LENGTH_REMARK)
    private String remark;

    public WebInputImportTaskSetting() {
    }

    public WebInputLongIdKey getKey() {
        return key;
    }

    public void setKey(WebInputLongIdKey key) {
        this.key = key;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "WebInputImportTaskSetting{" +
                "key=" + key +
                ", enabled=" + enabled +
                ", name='" + name + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
