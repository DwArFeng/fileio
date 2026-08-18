package com.dwarfeng.fileio.stack.bean.dto;

import com.dwarfeng.fileio.stack.bean.key.TaskItemKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.io.InputStream;

/**
 * 文件流上传信息。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class FileStreamUploadInfo implements Dto {

    private static final long serialVersionUID = 6344656487242606993L;

    private TaskItemKey key;
    private String originName;
    private long length;
    private InputStream content;

    public FileStreamUploadInfo() {
    }

    public FileStreamUploadInfo(TaskItemKey key, String originName, long length, InputStream content) {
        this.key = key;
        this.originName = originName;
        this.length = length;
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

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public InputStream getContent() {
        return content;
    }

    public void setContent(InputStream content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "FileStreamUploadInfo{" +
                "key=" + key +
                ", originName='" + originName + '\'' +
                ", length=" + length +
                ", content=" + content +
                '}';
    }
}
