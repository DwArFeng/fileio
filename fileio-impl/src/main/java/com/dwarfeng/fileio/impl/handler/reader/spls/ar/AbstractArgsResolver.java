package com.dwarfeng.fileio.impl.handler.reader.spls.ar;

import com.dwarfeng.fileio.impl.handler.reader.spls.ArgsResolver;

/**
 * 参数解析器的抽象实现。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public abstract class AbstractArgsResolver implements ArgsResolver {

    protected final String name;

    public AbstractArgsResolver(String name) {
        this.name = name;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String toString() {
        return "AbstractArgsResolver{" +
                "name='" + name + '\'' +
                '}';
    }
}
