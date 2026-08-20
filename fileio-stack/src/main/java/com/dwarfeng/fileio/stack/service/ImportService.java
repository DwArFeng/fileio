package com.dwarfeng.fileio.stack.service;

import com.dwarfeng.fileio.stack.bean.dto.*;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

import java.util.concurrent.CompletableFuture;

/**
 * 导入服务。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface ImportService extends Service {

    /**
     * 创建任务。
     *
     * @param createInfo 创建信息。
     * @return 创建的任务的主键。
     * @throws ServiceException 服务异常。
     */
    LongIdKey createTask(TaskCreateInfo createInfo) throws ServiceException;

    /**
     * 插入/更新任务元数据。
     *
     * @param upsertInfo 插入/更新信息。
     * @throws ServiceException 服务异常。
     */
    void upsertTaskMetadata(MetadataUpsertInfo upsertInfo) throws ServiceException;

    /**
     * 上传文件。
     *
     * @param uploadInfo 文件上传信息。
     * @throws ServiceException 服务异常。
     */
    void uploadFile(FileUploadInfo uploadInfo) throws ServiceException;

    /**
     * 上传文件流。
     *
     * <p>
     * 调用者有义务关闭 <code>FileStreamUploadInfo</code> 中的输入流，
     * 即其中的 <code>FileStreamUploadInfo.content</code>。
     *
     * @param uploadInfo 文件流上传信息。
     * @throws ServiceException 服务异常。
     */
    void uploadFileStream(FileStreamUploadInfo uploadInfo) throws ServiceException;

    /**
     * 执行任务。
     *
     * <p>
     * 该方法调用后，会等待任务执行完成，然后返回。
     *
     * @param executeInfo 执行信息。
     * @throws ServiceException 服务异常。
     */
    void executeTask(TaskExecuteInfo executeInfo) throws ServiceException;

    /**
     * 异步执行任务。
     *
     * <p>
     * 该方法调用后，会立即返回一个 <code>CompletableFuture</code>。
     * 返回的 <code>CompletableFuture</code> 会在任务执行完成后完成。
     *
     * @param executeInfo 执行信息。
     * @return 任务执行的结果。
     * @throws ServiceException 服务异常。
     */
    CompletableFuture<Void> executeTaskAsync(TaskExecuteInfo executeInfo) throws ServiceException;

    /**
     * 下载文件。
     *
     * @param downloadInfo 文件下载信息。
     * @return 文件。
     * @throws ServiceException 服务异常。
     */
    File downloadFile(FileDownloadInfo downloadInfo) throws ServiceException;

    /**
     * 下载文件流。
     *
     * <p>
     * 如果返回的 <code>FileStream</code> 不为 <code>null</code>，则调用者有义务关闭
     * <code>FileStream</code> 中的输入流，即其中的 <code>FileStream.content</code>。
     *
     * @param downloadInfo 文件流下载信息。
     * @return 文件流。
     * @throws ServiceException 服务异常。
     */
    FileStream downloadFileStream(FileStreamDownloadInfo downloadInfo) throws ServiceException;
}
