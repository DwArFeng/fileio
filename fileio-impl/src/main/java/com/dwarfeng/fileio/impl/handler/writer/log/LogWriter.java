package com.dwarfeng.fileio.impl.handler.writer.log;

import com.dwarfeng.fileio.sdk.handler.writer.AbstractWriter;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 日志写入器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component("logWriterRegistry.logWriter")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class LogWriter extends AbstractWriter {

    private final ApplicationContext ctx;

    private final String logLevel;

    public LogWriter(ApplicationContext ctx, String logLevel) {
        this.ctx = ctx;
        this.logLevel = logLevel;
    }

    @Override
    protected Executor doNewExecutor() {
        return ctx.getBean(LogExecutor.class, logLevel);
    }

    @Override
    public String toString() {
        return "LogWriter{" +
                "ctx=" + ctx +
                ", logLevel='" + logLevel + '\'' +
                '}';
    }
}
