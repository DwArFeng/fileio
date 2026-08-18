package com.dwarfeng.fileio.stack.bean.dto;

import com.dwarfeng.fileio.stack.bean.key.TaskSettingItemKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

/**
 * 模板流下载信息。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class TemplateStreamDownloadInfo implements Dto {

    private static final long serialVersionUID = 5422595236900210744L;

    private TaskSettingItemKey key;

    public TemplateStreamDownloadInfo() {
    }

    public TemplateStreamDownloadInfo(TaskSettingItemKey key) {
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
        return "TemplateStreamDownloadInfo{" +
                "key=" + key +
                '}';
    }
}
