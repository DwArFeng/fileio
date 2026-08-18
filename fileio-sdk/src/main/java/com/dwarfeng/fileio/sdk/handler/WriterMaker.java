package com.dwarfeng.fileio.sdk.handler;

import com.dwarfeng.fileio.stack.exception.WriterException;
import com.dwarfeng.fileio.stack.handler.Writer;

/**
 * 写入器构造器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface WriterMaker {

    /**
     * 返回构造器是否支持指定的类型。
     *
     * @param type 指定的类型。
     * @return 构造器是否支持指定的类型。
     */
    boolean supportType(String type);

    /**
     * 根据指定的写入器信息生成一个写入器对象。
     *
     * <p>
     * 可以保证传入的写入器信息中的类型是支持的。
     *
     * @param type  写入器类型。
     * @param param 写入器参数。
     * @return 构造的写入器。
     * @throws WriterException 写入器异常。
     */
    Writer makeWriter(String type, String param) throws WriterException;
}
