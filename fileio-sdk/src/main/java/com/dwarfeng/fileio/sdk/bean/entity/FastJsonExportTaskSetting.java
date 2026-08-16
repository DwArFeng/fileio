package com.dwarfeng.fileio.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fileio.stack.bean.entity.ExportTaskSetting;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import java.util.Objects;

/**
 * FastJson 导出任务设置。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class FastJsonExportTaskSetting implements Bean {

    private static final long serialVersionUID = 8644945026194797410L;

    public static FastJsonExportTaskSetting of(ExportTaskSetting exportTaskSetting) {
        if (Objects.isNull(exportTaskSetting)) {
            return null;
        } else {
            return new FastJsonExportTaskSetting(
                    FastJsonLongIdKey.of(exportTaskSetting.getKey()),
                    exportTaskSetting.isEnabled(),
                    exportTaskSetting.getName(),
                    exportTaskSetting.getRemark()
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private FastJsonLongIdKey key;

    @JSONField(name = "enabled", ordinal = 2)
    private boolean enabled;

    @JSONField(name = "name", ordinal = 3)
    private String name;

    @JSONField(name = "remark", ordinal = 4)
    private String remark;

    public FastJsonExportTaskSetting() {
    }

    public FastJsonExportTaskSetting(FastJsonLongIdKey key, boolean enabled, String name, String remark) {
        this.key = key;
        this.enabled = enabled;
        this.name = name;
        this.remark = remark;
    }

    public FastJsonLongIdKey getKey() {
        return key;
    }

    public void setKey(FastJsonLongIdKey key) {
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
        return "FastJsonExportTaskSetting{" +
                "key=" + key +
                ", enabled=" + enabled +
                ", name='" + name + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
