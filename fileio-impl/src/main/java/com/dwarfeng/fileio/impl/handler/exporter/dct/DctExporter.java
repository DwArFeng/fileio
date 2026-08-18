package com.dwarfeng.fileio.impl.handler.exporter.dct;

import com.dwarfeng.dct.stack.handler.ValueCodingHandler;
import com.dwarfeng.fileio.sdk.handler.exporter.AbstractExporter;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 基于 DCT 框架的导出器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component("dctExporterRegistry.dctExporter")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class DctExporter extends AbstractExporter {

    private final ApplicationContext ctx;

    private final ValueCodingHandler valueCodingHandler;

    private final Config config;

    public DctExporter(ApplicationContext ctx, ValueCodingHandler valueCodingHandler, Config config) {
        this.ctx = ctx;
        this.valueCodingHandler = valueCodingHandler;
        this.config = config;
    }

    @Override
    protected Executor doNewExecutor() {
        return ctx.getBean(DctExecutor.class, valueCodingHandler, config);
    }

    @Override
    public String toString() {
        return "DctExporter{" +
                "ctx=" + ctx +
                ", config=" + config +
                '}';
    }
}
