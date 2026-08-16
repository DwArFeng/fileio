package com.dwarfeng.fileio.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fileio.sdk.bean.key.FastJsonTaskSettingItemKey;
import com.dwarfeng.fileio.stack.bean.entity.WriterInfo;
import com.dwarfeng.subgrade.stack.bean.Bean;

import java.util.Objects;

/**
 * FastJson 写入器信息。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class FastJsonWriterInfo implements Bean {

    private static final long serialVersionUID = -2537693406565304239L;

    public static FastJsonWriterInfo of(WriterInfo writerInfo) {
        if (Objects.isNull(writerInfo)) {
            return null;
        } else {
            return new FastJsonWriterInfo(
                    FastJsonTaskSettingItemKey.of(writerInfo.getKey()),
                    writerInfo.isEnabled(),
                    writerInfo.getType(),
                    writerInfo.getParam(),
                    writerInfo.getRemark()
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private FastJsonTaskSettingItemKey key;

    @JSONField(name = "enabled", ordinal = 2)
    private boolean enabled;

    @JSONField(name = "type", ordinal = 3)
    private String type;

    @JSONField(name = "param", ordinal = 4)
    private String param;

    @JSONField(name = "remark", ordinal = 5)
    private String remark;

    public FastJsonWriterInfo() {
    }

    public FastJsonWriterInfo(
            FastJsonTaskSettingItemKey key, boolean enabled, String type, String param, String remark
    ) {
        this.key = key;
        this.enabled = enabled;
        this.type = type;
        this.param = param;
        this.remark = remark;
    }

    public FastJsonTaskSettingItemKey getKey() {
        return key;
    }

    public void setKey(FastJsonTaskSettingItemKey key) {
        this.key = key;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "FastJsonWriterInfo{" +
                "key=" + key +
                ", enabled=" + enabled +
                ", type='" + type + '\'' +
                ", param='" + param + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
