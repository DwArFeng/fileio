package com.dwarfeng.fileio.impl.handler.exporter.excel;

/**
 * @author DwArFeng
 * @since 1.0.0
 */
public interface ValueMapper<T> {

    String name();

    T map(Object value) throws Exception;
}
