package com.dwarfeng.fileio.stack.bean.dto;

import com.dwarfeng.fileio.stack.bean.key.TaskItemKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

/**
 * 元数据插入/更新信息。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class MetadataUpsertInfo implements Dto {

    private static final long serialVersionUID = 4429424537913363536L;

    private TaskItemKey key;
    private String content;

    public MetadataUpsertInfo() {
    }

    public MetadataUpsertInfo(TaskItemKey key, String content) {
        this.key = key;
        this.content = content;
    }

    public TaskItemKey getKey() {
        return key;
    }

    public void setKey(TaskItemKey key) {
        this.key = key;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "MetadataUpsertInfo{" +
                "key=" + key +
                ", content='" + content + '\'' +
                '}';
    }
}
