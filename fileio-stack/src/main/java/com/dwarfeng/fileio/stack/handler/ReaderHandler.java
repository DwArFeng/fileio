package com.dwarfeng.fileio.stack.handler;

import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.handler.Handler;

/**
 * 读取器处理器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface ReaderHandler extends Handler {

    /**
     * 根据指定的信息构造一个读取器。
     *
     * @param type  读取器类型。
     * @param param 读取器参数。
     * @return 构造的读取器。
     * @throws HandlerException 处理器异常。
     */
    Reader make(String type, String param) throws HandlerException;
}
