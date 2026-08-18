package com.dwarfeng.fileio.stack.handler;

import com.dwarfeng.fileio.stack.bean.dto.*;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.handler.Handler;

import java.util.concurrent.CompletableFuture;

/**
 * 导出处理器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface ExportHandler extends Handler {

    /**
     * 创建任务。
     *
     * @param createInfo 创建信息。
     * @return 创建的任务的主键。
     * @throws HandlerException 处理器异常。
     */
    LongIdKey createTask(TaskCreateInfo createInfo) throws HandlerException;

    /**
     * 插入/更新任务元数据。
     *
     * @param upsertInfo 插入/更新信息。
     * @throws HandlerException 处理器异常。
     */
    void upsertTaskMetadata(MetadataUpsertInfo upsertInfo) throws HandlerException;

    /**
     * 执行任务。
     *
     * <p>
     * 该方法调用后，会等待任务执行完成，然后返回。
     *
     * @param executeInfo 执行信息。
     * @throws HandlerException 处理器异常。
     */
    void executeTask(TaskExecuteInfo executeInfo) throws HandlerException;

    /**
     * 异步执行任务。
     *
     * <p>
     * 该方法调用后，会立即返回一个 <code>CompletableFuture</code>。
     * 返回的 <code>CompletableFuture</code> 会在任务执行完成后完成。
     *
     * @param executeInfo 执行信息。
     * @return 任务执行的结果。
     * @throws HandlerException 处理器异常。
     */
    CompletableFuture<Void> executeTaskAsync(TaskExecuteInfo executeInfo) throws HandlerException;

    /**
     * 下载文件。
     *
     * @param downloadInfo 文件下载信息。
     * @return 文件。
     * @throws HandlerException 处理器异常。
     */
    File downloadFile(FileDownloadInfo downloadInfo) throws HandlerException;

    /**
     * 下载文件流。
     *
     * @param downloadInfo 文件流下载信息。
     * @return 文件流。
     * @throws HandlerException 处理器异常。
     */
    FileStream downloadFileStream(FileStreamDownloadInfo downloadInfo) throws HandlerException;
}
