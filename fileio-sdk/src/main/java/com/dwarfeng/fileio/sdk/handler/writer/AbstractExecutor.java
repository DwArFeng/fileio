package com.dwarfeng.fileio.sdk.handler.writer;

import static com.dwarfeng.fileio.stack.handler.Writer.Context;
import static com.dwarfeng.fileio.stack.handler.Writer.Executor;

/**
 * 写入器执行器的抽象实现。
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
