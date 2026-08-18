package com.dwarfeng.fileio.stack.handler;

import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.handler.Handler;

/**
 * 导入器处理器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface ImporterHandler extends Handler {

    /**
     * 根据指定的信息构造一个导入器。
     *
     * @param type  导入器类型。
     * @param param 导入器参数。
     * @return 构造的导入器。
     * @throws HandlerException 处理器异常。
     */
    Importer make(String type, String param) throws HandlerException;
}
