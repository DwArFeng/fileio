package com.dwarfeng.fileio.stack.handler;

import com.dwarfeng.fileio.stack.bean.dto.*;
import com.dwarfeng.fileio.stack.bean.key.TaskItemKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.handler.Handler;

/**
 * 导出文件操作处理器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface ExportFileOperateHandler extends Handler {

    /**
     * 创建文件。
     *
     * @param createInfo 文件创建信息。
     * @param operator   操作者。
     * @return 文件。
     * @throws HandlerException 处理器异常。
     */
    TaskItemKey create(FileCreateInfo createInfo, Operator operator) throws HandlerException;

    /**
     * 下载文件。
     *
     * @param downloadInfo 文件下载信息。
     * @param operator     操作者。
     * @return 文件。
     * @throws HandlerException 处理器异常。
     */
    File download(FileDownloadInfo downloadInfo, Operator operator) throws HandlerException;

    /**
     * 下载文件流。
     *
     * @param downloadInfo 文件流下载信息。
     * @param operator     操作者。
     * @return 文件流。
     * @throws HandlerException 处理器异常。
     */
    FileStream downloadStream(FileStreamDownloadInfo downloadInfo, Operator operator) throws HandlerException;

    /**
     * 上传文件。
     *
     * @param uploadInfo 文件上传信息。
     * @param operator   操作者。
     * @throws HandlerException 处理器异常。
     */
    void upload(FileUploadInfo uploadInfo, Operator operator) throws HandlerException;

    /**
     * 上传文件流。
     *
     * @param uploadInfo 文件流上传信息。
     * @param operator   操作者。
     * @throws HandlerException 处理器异常。
     */
    void uploadStream(FileStreamUploadInfo uploadInfo, Operator operator) throws HandlerException;

    /**
     * 删除文件。
     *
     * @param removeInfo 文件删除信息。
     * @param operator   操作者。
     * @throws HandlerException 处理器异常。
     */
    void remove(FileRemoveInfo removeInfo, Operator operator) throws HandlerException;

    /**
     * 操作者枚举。
     *
     * @author DwArFeng
     * @since 1.0.0
     */
    enum Operator {
        EXPORTER_CONTEXT, READER_CONTEXT, SERVICE
    }
}
