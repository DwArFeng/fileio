package com.dwarfeng.fileio.sdk.handler;

import com.dwarfeng.fileio.stack.exception.ReaderException;
import com.dwarfeng.fileio.stack.handler.Reader;

/**
 * 读取器构造器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface ReaderMaker {

    /**
     * 返回构造器是否支持指定的类型。
     *
     * @param type 指定的类型。
     * @return 构造器是否支持指定的类型。
     */
    boolean supportType(String type);

    /**
     * 根据指定的读取器信息生成一个读取器对象。
     *
     * <p>
     * 可以保证传入的读取器信息中的类型是支持的。
     *
     * @param type  读取器类型。
     * @param param 读取器参数。
     * @return 构造的读取器。
     * @throws ReaderException 读取器异常。
     */
    Reader makeReader(String type, String param) throws ReaderException;
}
