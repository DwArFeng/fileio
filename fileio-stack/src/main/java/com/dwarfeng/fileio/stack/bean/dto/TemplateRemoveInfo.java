package com.dwarfeng.fileio.stack.bean.dto;

import com.dwarfeng.fileio.stack.bean.key.TaskSettingItemKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

/**
 * 模板移除信息。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class TemplateRemoveInfo implements Dto {

    private static final long serialVersionUID = 8082032931795407031L;

    private TaskSettingItemKey key;

    public TemplateRemoveInfo() {
    }

    public TemplateRemoveInfo(TaskSettingItemKey key) {
        this.key = key;
    }

    public TaskSettingItemKey getKey() {
        return key;
    }

    public void setKey(TaskSettingItemKey key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "TemplateRemoveInfo{" +
                "key=" + key +
                '}';
    }
}
