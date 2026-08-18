package com.dwarfeng.fileio.impl.handler.exporter.excel;

import com.dwarfeng.fileio.sdk.handler.exporter.AbstractExporter;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Excel 导出器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component("excelExporterRegistry.excelExporter")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ExcelExporter extends AbstractExporter {

    private final ApplicationContext ctx;

    private final Config config;

    public ExcelExporter(ApplicationContext ctx, Config config) {
        this.ctx = ctx;
        this.config = config;
    }

    @Override
    protected Executor doNewExecutor() {
        return ctx.getBean(ExcelExecutor.class, ctx, config);
    }

    @Override
    public String toString() {
        return "ExcelExporter{" +
                "ctx=" + ctx +
                ", config=" + config +
                '}';
    }
}
