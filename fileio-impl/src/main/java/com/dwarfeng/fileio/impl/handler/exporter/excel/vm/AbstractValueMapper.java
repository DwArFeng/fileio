package com.dwarfeng.fileio.impl.handler.exporter.excel.vm;

import com.dwarfeng.fileio.impl.handler.exporter.excel.ValueMapper;

/**
 * 值映射器的抽象实现。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public abstract class AbstractValueMapper<T> implements ValueMapper<T> {

    protected final String name;

    public AbstractValueMapper(String name) {
        this.name = name;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String toString() {
        return "AbstractValueMapper{" +
                "name='" + name + '\'' +
                '}';
    }
}
