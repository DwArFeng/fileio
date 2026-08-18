package com.dwarfeng.fileio.impl.handler.reader.spls;

/**
 * 参数解析器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface ArgsResolver {

    String name();

    Object[] resolve(String value) throws Exception;
}
