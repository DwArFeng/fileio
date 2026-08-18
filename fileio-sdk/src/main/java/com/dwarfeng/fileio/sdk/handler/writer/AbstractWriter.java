package com.dwarfeng.fileio.sdk.handler.writer;

import com.dwarfeng.fileio.stack.handler.Writer;
import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 写入器的抽象实现。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public abstract class AbstractWriter implements Writer {

    @Override
    public Executor newExecutor() throws HandlerException {
        try {
            return doNewExecutor();
        } catch (HandlerException e) {
            throw e;
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    protected abstract Executor doNewExecutor() throws Exception;

    @Override
    public String toString() {
        return "AbstractWriter{}";
    }
}
