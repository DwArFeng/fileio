package com.dwarfeng.fileio.stack.handler;

import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.handler.Handler;

/**
 * 导出器处理器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface ExporterHandler extends Handler {

    /**
     * 根据指定的信息构造一个导出器。
     *
     * @param type  导出器类型。
     * @param param 导出器参数。
     * @return 构造的导出器。
     * @throws HandlerException 处理器异常。
     */
    Exporter make(String type, String param) throws HandlerException;
}
