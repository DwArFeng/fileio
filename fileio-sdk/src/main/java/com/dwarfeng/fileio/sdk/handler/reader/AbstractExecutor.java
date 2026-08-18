package com.dwarfeng.fileio.sdk.handler.reader;

import static com.dwarfeng.fileio.stack.handler.Reader.Context;
import static com.dwarfeng.fileio.stack.handler.Reader.Executor;

/**
 * 读取器执行器的抽象实现。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public abstract class AbstractExecutor implements Executor {

    protected Context context;

    @Override
    public void init(Context context) {
        this.context = context;
    }

    @Override
    public String toString() {
        return "AbstractExecutor{" +
                "context=" + context +
                '}';
    }
}
