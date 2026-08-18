package com.dwarfeng.fileio.stack.handler;

import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.handler.Handler;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * 临时文件处理器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface TemporaryFileHandler extends Handler {

    /**
     * 获取指定的临时文件的长度。
     *
     * @param type    临时文件类型。
     * @param taskKey 任务主键。
     * @param name    文件名。
     * @return 指定的临时文件的长度。
     * @throws HandlerException 处理器异常。
     * @since 1.0.0
     */
    long length(TemporaryFileType type, LongIdKey taskKey, String name) throws HandlerException;

    /**
     * 创建临时文件。
     *
     * <p>
     * 如果该文件已经存在，则覆盖原有文件。
     *
     * @param type    临时文件类型。
     * @param taskKey 任务主键。
     * @param name    文件名。
     * @throws HandlerException 处理器异常。
     */
    void create(TemporaryFileType type, LongIdKey taskKey, String name) throws HandlerException;

    /**
     * 打开指定的临时文件的输入流。
     *
     * <p>
     * 调用者必须在使用完毕后关闭输入流。
     *
     * @param type    临时文件类型。
     * @param taskKey 任务主键。
     * @param name    文件名。
     * @return 指定的临时文件的输入流。
     * @throws HandlerException 处理器异常。
     */
    InputStream openInputStream(TemporaryFileType type, LongIdKey taskKey, String name) throws HandlerException;

    /**
     * 打开指定的临时文件的输出流。
     *
     * <p>
     * 调用者必须在使用完毕后关闭输出流。
     *
     * @param type    临时文件类型。
     * @param taskKey 任务主键。
     * @param name    文件名。
     * @return 指定的临时文件的输出流。
     * @throws HandlerException 处理器异常。
     */
    OutputStream openOutputStream(TemporaryFileType type, LongIdKey taskKey, String name) throws HandlerException;

    /**
     * 删除指定的临时文件。
     *
     * <p>
     * 如果指定的临时文件不存在，则不进行任何操作。
     *
     * @param type    临时文件类型。
     * @param taskKey 任务主键。
     * @param name    文件名。
     * @throws HandlerException 处理器异常。
     */
    void delete(TemporaryFileType type, LongIdKey taskKey, String name) throws HandlerException;

    /**
     * 临时文件类型。
     *
     * @author DwArFeng
     * @since 1.0.0
     */
    enum TemporaryFileType {

        /**
         * 导出任务临时文件。
         */
        EXPORT_TASK_TEMPORARY_FILE,

        /**
         * 导入任务临时文件。
         */
        IMPORT_TASK_TEMPORARY_FILE
    }
}
