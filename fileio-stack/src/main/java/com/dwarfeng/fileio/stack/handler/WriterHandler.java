package com.dwarfeng.fileio.stack.handler;

import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.handler.Handler;

/**
 * 写入器处理器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface WriterHandler extends Handler {

    /**
     * 根据指定的信息构造一个写入器。
     *
     * @param type  写入器类型。
     * @param param 写入器参数。
     * @return 构造的写入器。
     * @throws HandlerException 处理器异常。
     */
    Writer make(String type, String param) throws HandlerException;
}
