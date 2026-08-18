package com.dwarfeng.fileio.stack.bean.dto;

import com.dwarfeng.fileio.stack.bean.key.TaskItemKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Arrays;

/**
 * 文件上传信息。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class FileUploadInfo implements Dto {

    private static final long serialVersionUID = 2067433579780245623L;

    private TaskItemKey key;
    private String originName;
    private byte[] content;

    public FileUploadInfo() {
    }

    public FileUploadInfo(TaskItemKey key, String originName, byte[] content) {
        this.key = key;
        this.originName = originName;
        this.content = content;
    }

    public TaskItemKey getKey() {
        return key;
    }

    public void setKey(TaskItemKey key) {
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
        return "FileUploadInfo{" +
                "key=" + key +
                ", originName='" + originName + '\'' +
                ", content=" + Arrays.toString(content) +
                '}';
    }
}
