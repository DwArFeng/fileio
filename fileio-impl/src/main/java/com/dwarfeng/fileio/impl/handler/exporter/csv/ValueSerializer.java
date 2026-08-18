package com.dwarfeng.fileio.impl.handler.exporter.csv;

/**
 * 值序列化器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface ValueSerializer {

    String name();

    String serialize(Object value) throws Exception;
}
