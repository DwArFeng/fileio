package com.dwarfeng.fileio.impl.handler.exporter.log;

import com.dwarfeng.fileio.sdk.handler.exporter.AbstractExporter;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 日志导出器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component("logExporterRegistry.logExporter")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class LogExporter extends AbstractExporter {

    private final ApplicationContext ctx;

    private final String logLevel;

    public LogExporter(ApplicationContext ctx, String logLevel) {
        this.ctx = ctx;
        this.logLevel = logLevel;
    }

    @Override
    protected Executor doNewExecutor() {
        return ctx.getBean(LogExecutor.class, logLevel);
    }

    @Override
    public String toString() {
        return "LogExporter{" +
                "ctx=" + ctx +
                ", logLevel='" + logLevel + '\'' +
                '}';
    }
}
