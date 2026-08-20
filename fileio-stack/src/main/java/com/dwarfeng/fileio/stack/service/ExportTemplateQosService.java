package com.dwarfeng.fileio.stack.service;

import com.dwarfeng.fileio.stack.bean.dto.*;
import com.dwarfeng.fileio.stack.bean.key.TaskSettingItemKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 导出模板 QOS 服务。
 *
 * @author DwArFeng
 * @since 1.0.1
 */
public interface ExportTemplateQosService extends Service {

    /**
     * 创建模板。
     *
     * @param createInfo 模板创建信息。
     * @return 模板主键。
     * @throws ServiceException 服务异常。
     */
    TaskSettingItemKey create(TemplateCreateInfo createInfo) throws ServiceException;

    /**
     * 下载模板。
     *
     * @param downloadInfo 模板下载信息。
     * @return 模板。
     * @throws ServiceException 服务异常。
     */
    Template download(TemplateDownloadInfo downloadInfo) throws ServiceException;

    /**
     * 下载模板流。
     *
     * <p>
     * 如果返回的 <code>TemplateStream</code> 不为 <code>null</code>，则调用者有义务关闭
     * <code>TemplateStream</code> 中的输入流，即其中的 <code>TemplateStream.content</code>。
     *
     * @param downloadInfo 模板流下载信息。
     * @return 模板流。
     * @throws ServiceException 服务异常。
     */
    TemplateStream downloadStream(TemplateStreamDownloadInfo downloadInfo) throws ServiceException;

    /**
     * 上传模板。
     *
     * @param uploadInfo 模板上传信息。
     * @throws ServiceException 服务异常。
     */
    void upload(TemplateUploadInfo uploadInfo) throws ServiceException;

    /**
     * 上传模板流。
     *
     * <p>
     * 如果返回的 <code>TemplateStream</code> 不为 <code>null</code>，则调用者有义务关闭
     * <code>TemplateStream</code> 中的输入流，即其中的 <code>TemplateStream.content</code>。
     *
     * @param uploadInfo 模板流上传信息。
     * @throws ServiceException 服务异常。
     */
    void uploadStream(TemplateStreamUploadInfo uploadInfo) throws ServiceException;

    /**
     * 删除模板。
     *
     * @param removeInfo 模板删除信息。
     * @throws ServiceException 服务异常。
     */
    void remove(TemplateRemoveInfo removeInfo) throws ServiceException;
}
