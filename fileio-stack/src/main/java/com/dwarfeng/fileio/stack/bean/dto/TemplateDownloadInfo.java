package com.dwarfeng.fileio.stack.bean.dto;

import com.dwarfeng.fileio.stack.bean.key.TaskSettingItemKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

/**
 * 模板下载信息。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class TemplateDownloadInfo implements Dto {

    private static final long serialVersionUID = -1125909382881799342L;

    private TaskSettingItemKey key;

    public TemplateDownloadInfo() {
    }

    public TemplateDownloadInfo(TaskSettingItemKey key) {
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
        return "TemplateDownloadInfo{" +
                "key=" + key +
                '}';
    }
}
