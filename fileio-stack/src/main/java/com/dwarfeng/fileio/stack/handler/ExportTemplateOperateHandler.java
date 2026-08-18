package com.dwarfeng.fileio.stack.handler;

import com.dwarfeng.fileio.stack.bean.dto.*;
import com.dwarfeng.fileio.stack.bean.key.TaskSettingItemKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.handler.Handler;

/**
 * 导出模板操作处理器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface ExportTemplateOperateHandler extends Handler {

    /**
     * 创建模板。
     *
     * @param createInfo 模板创建信息。
     * @return 模板主键。
     * @throws HandlerException 处理器异常。
     */
    TaskSettingItemKey create(TemplateCreateInfo createInfo) throws HandlerException;

    /**
     * 下载模板。
     *
     * @param downloadInfo 模板下载信息。
     * @return 模板。
     * @throws HandlerException 处理器异常。
     */
    Template download(TemplateDownloadInfo downloadInfo) throws HandlerException;

    /**
     * 下载模板流。
     *
     * @param downloadInfo 模板流下载信息。
     * @return 模板流。
     * @throws HandlerException 处理器异常。
     */
    TemplateStream downloadStream(TemplateStreamDownloadInfo downloadInfo) throws HandlerException;

    /**
     * 上传模板。
     *
     * @param uploadInfo 模板上传信息。
     * @throws HandlerException 处理器异常。
     */
    void upload(TemplateUploadInfo uploadInfo) throws HandlerException;

    /**
     * 上传模板流。
     *
     * @param uploadInfo 模板流上传信息。
     * @throws HandlerException 处理器异常。
     */
    void uploadStream(TemplateStreamUploadInfo uploadInfo) throws HandlerException;

    /**
     * 删除模板。
     *
     * @param removeInfo 模板删除信息。
     * @throws HandlerException 处理器异常。
     */
    void remove(TemplateRemoveInfo removeInfo) throws HandlerException;
}
