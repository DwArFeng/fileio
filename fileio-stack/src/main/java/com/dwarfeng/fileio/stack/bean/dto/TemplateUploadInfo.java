package com.dwarfeng.fileio.stack.bean.dto;

import com.dwarfeng.fileio.stack.bean.key.TaskSettingItemKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Arrays;

/**
 * 模板上传信息。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class TemplateUploadInfo implements Dto {

    private static final long serialVersionUID = 2150091265474016947L;

    private TaskSettingItemKey key;
    private String originName;
    private byte[] content;

    public TemplateUploadInfo() {
    }

    public TemplateUploadInfo(TaskSettingItemKey key, String originName, byte[] content) {
        this.key = key;
        this.originName = originName;
        this.content = content;
    }

    public TaskSettingItemKey getKey() {
        return key;
    }

    public void setKey(TaskSettingItemKey key) {
        this.key = key;
    }

    public String getOriginName() {
        return originName;
    }

    public void setOriginName(String originName) {
        this.originName = originName;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "TemplateUploadInfo{" +
                "key=" + key +
                ", originName='" + originName + '\'' +
                ", content=" + Arrays.toString(content) +
                '}';
    }
}
