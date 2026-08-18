package com.dwarfeng.fileio.sdk.handler.importer;

import static com.dwarfeng.fileio.stack.handler.Importer.Context;
import static com.dwarfeng.fileio.stack.handler.Importer.Executor;

/**
 * 导入器执行器的抽象实现。
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
