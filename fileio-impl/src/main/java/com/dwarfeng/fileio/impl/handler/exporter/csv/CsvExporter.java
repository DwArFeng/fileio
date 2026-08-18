package com.dwarfeng.fileio.impl.handler.exporter.csv;

import com.dwarfeng.fileio.sdk.handler.exporter.AbstractExporter;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * CSV 导出器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component("csvExporterRegistry.csvExporter")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CsvExporter extends AbstractExporter {

    private final ApplicationContext ctx;

    private final Config config;

    public CsvExporter(ApplicationContext ctx, Config config) {
        this.ctx = ctx;
        this.config = config;
    }

    @Override
    protected Executor doNewExecutor() {
        return ctx.getBean(CsvExecutor.class, ctx, config);
    }

    @Override
    public String toString() {
        return "CsvExporter{" +
                "ctx=" + ctx +
                '}';
    }
}
