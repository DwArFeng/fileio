package com.dwarfeng.fileio.stack.bean.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;

/**
 * 临时文件到文件拷贝信息。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class TemporaryToFileCopyInfo implements Dto {

    private static final long serialVersionUID = 6429509618603052929L;

    private String temporaryName;
    private String fileIdentifier;
    private String fileOriginName;

    public TemporaryToFileCopyInfo() {
    }

    public TemporaryToFileCopyInfo(String temporaryName, String fileIdentifier, String fileOriginName) {
        this.temporaryName = temporaryName;
        this.fileIdentifier = fileIdentifier;
        this.fileOriginName = fileOriginName;
    }

    public String getTemporaryName() {
        return temporaryName;
    }

    public void setTemporaryName(String temporaryName) {
        this.temporaryName = temporaryName;
    }

    public String getFileIdentifier() {
        return fileIdentifier;
    }

    public void setFileIdentifier(String fileIdentifier) {
        this.fileIdentifier = fileIdentifier;
    }

    public String getFileOriginName() {
        return fileOriginName;
    }

    public void setFileOriginName(String fileOriginName) {
        this.fileOriginName = fileOriginName;
    }

    @Override
    public String toString() {
        return "TemporaryToFileCopyInfo{" +
                "temporaryName='" + temporaryName + '\'' +
                ", fileIdentifier='" + fileIdentifier + '\'' +
                ", fileOriginName='" + fileOriginName + '\'' +
                '}';
    }
}
