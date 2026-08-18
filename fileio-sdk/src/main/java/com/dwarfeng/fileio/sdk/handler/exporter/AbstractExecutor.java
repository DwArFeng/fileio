package com.dwarfeng.fileio.sdk.handler.exporter;

import static com.dwarfeng.fileio.stack.handler.Exporter.Context;
import static com.dwarfeng.fileio.stack.handler.Exporter.Executor;

/**
 * 导出器执行器的抽象实现。
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

    public String toString() {
        return "AbstractExecutor{" +
                "context=" + context +
                '}';
    }
}
