package com.dwarfeng.fileio.stack.handler;

import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.handler.Handler;

/**
 * 支持处理器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface SupportHandler extends Handler {

    /**
     * 重置导出器。
     *
     * @throws HandlerException 处理器异常。
     */
    void resetExporter() throws HandlerException;

    /**
     * 重置导入器。
     *
     * @throws HandlerException 处理器异常。
     */
    void resetImporter() throws HandlerException;

    /**
     * 重置读取器。
     *
     * @throws HandlerException 处理器异常。
     */
    void resetReader() throws HandlerException;

    /**
     * 重置读取器。
     *
     * @throws HandlerException 处理器异常。
     */
    void resetWriter() throws HandlerException;
}
