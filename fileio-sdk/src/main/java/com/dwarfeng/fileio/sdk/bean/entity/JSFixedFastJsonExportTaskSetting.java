package com.dwarfeng.fileio.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.subgrade.sdk.bean.key.JSFixedFastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;
import com.dwarfeng.fileio.stack.bean.entity.ExportTaskSetting;

import java.util.Objects;

/**
 * JSFixed FastJson 导出任务设置。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class JSFixedFastJsonExportTaskSetting implements Bean {

    private static final long serialVersionUID = 3561192086643449631L;

    public static JSFixedFastJsonExportTaskSetting of(ExportTaskSetting exportTaskSetting) {
        if (Objects.isNull(exportTaskSetting)) {
            return null;
        } else {
            return new JSFixedFastJsonExportTaskSetting(
                    JSFixedFastJsonLongIdKey.of(exportTaskSetting.getKey()),
                    exportTaskSetting.isEnabled(),
                    exportTaskSetting.getName(),
                    exportTaskSetting.getRemark()
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

    public JSFixedFastJsonExportTaskSetting() {
    }

    public JSFixedFastJsonExportTaskSetting(JSFixedFastJsonLongIdKey key, boolean enabled, String name, String remark) {
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
        return "JSFixedFastJsonExportTaskSetting{" +
                "key=" + key +
                ", enabled=" + enabled +
                ", name='" + name + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
