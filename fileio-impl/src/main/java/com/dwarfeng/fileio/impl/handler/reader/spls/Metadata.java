package com.dwarfeng.fileio.impl.handler.reader.spls;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.subgrade.stack.bean.Bean;

/**
 * 元数据。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class Metadata implements Bean {

    private static final long serialVersionUID = 3879974806911693679L;

    @JSONField(name = "#preset", ordinal = 1, deserialize = false)
    private String presetRem = "预设查询的预设名。";

    @JSONField(name = "preset", ordinal = 2)
    private String preset;

    @JSONField(name = "#args_string", ordinal = 3, deserialize = false)
    private String argsStringRem = "预设查询的参数数组字符串，该值将会被配置中指定的参数解析器解析为参数数组。";

    @JSONField(name = "args_string", ordinal = 4)
    private String argsString;

    public Metadata() {
    }

    public Metadata(String preset, String argsString) {
        this.preset = preset;
        this.argsString = argsString;
    }

    public String getPresetRem() {
        return presetRem;
    }

    public void setPresetRem(String presetRem) {
        this.presetRem = presetRem;
    }

    public String getPreset() {
        return preset;
    }

    public void setPreset(String preset) {
        this.preset = preset;
    }

    public String getArgsStringRem() {
        return argsStringRem;
    }

    public void setArgsStringRem(String argsStringRem) {
        this.argsStringRem = argsStringRem;
    }

    public String getArgsString() {
        return argsString;
    }

    public void setArgsString(String argsString) {
        this.argsString = argsString;
    }

    @Override
    public String toString() {
        return "Metadata{" +
                "presetRem='" + presetRem + '\'' +
                ", preset='" + preset + '\'' +
                ", argsStringRem='" + argsStringRem + '\'' +
                ", argsString='" + argsString + '\'' +
                '}';
    }
}
