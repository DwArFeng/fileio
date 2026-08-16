package com.dwarfeng.fileio.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fileio.stack.bean.entity.ImportTaskSetting;
import com.dwarfeng.subgrade.sdk.bean.key.JSFixedFastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import java.util.Objects;

/**
 * JSFixed FastJson 导入任务设置。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class JSFixedFastJsonImportTaskSetting implements Bean {

    private static final long serialVersionUID = -5808318085864129301L;

    public static JSFixedFastJsonImportTaskSetting of(ImportTaskSetting importTaskSetting) {
        if (Objects.isNull(importTaskSetting)) {
            return null;
        } else {
            return new JSFixedFastJsonImportTaskSetting(
                    JSFixedFastJsonLongIdKey.of(importTaskSetting.getKey()),
                    importTaskSetting.isEnabled(),
                    importTaskSetting.getName(),
                    importTaskSetting.getRemark()
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private JSFixedFastJsonLongIdKey key;

    @JSONField(name = "enabled", ordinal = 2)
    private boolean enabled;

    @JSONField(name = "name", ordinal = 3)
    private String name;

    @JSONField(name = "remark", ordinal = 4)
    private String remark;

    public JSFixedFastJsonImportTaskSetting() {
    }

    public JSFixedFastJsonImportTaskSetting(JSFixedFastJsonLongIdKey key, boolean enabled, String name, String remark) {
        this.key = key;
        this.enabled = enabled;
        this.name = name;
        this.remark = remark;
    }

    public JSFixedFastJsonLongIdKey getKey() {
        return key;
    }

    public void setKey(JSFixedFastJsonLongIdKey key) {
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
        return "JSFixedFastJsonImportTaskSetting{" +
                "key=" + key +
                ", enabled=" + enabled +
                ", name='" + name + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
