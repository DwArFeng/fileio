package com.dwarfeng.fileio.impl.handler.exporter.csv.vs;

import com.dwarfeng.fileio.impl.handler.exporter.csv.ValueSerializer;

/**
 * 值序列化器的抽象实现。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public abstract class AbstractValueSerializer implements ValueSerializer {

    protected final String name;

    public AbstractValueSerializer(String name) {
        this.name = name;
    }

    @Override
    public String name() {
        return name;
    }
}
