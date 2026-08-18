package com.dwarfeng.fileio.impl.handler.importer.dct;

import com.dwarfeng.dct.stack.handler.ValueCodingHandler;
import com.dwarfeng.fileio.sdk.handler.importer.AbstractImporter;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 基于 DCT 框架的导入器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component("dctImporterRegistry.dctImporter")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class DctImporter extends AbstractImporter {

    private final ApplicationContext ctx;

    private final ValueCodingHandler valueCodingHandler;

    private final Config config;

    public DctImporter(ApplicationContext ctx, ValueCodingHandler valueCodingHandler, Config config) {
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
        return "DctImporter{" +
                "ctx=" + ctx +
                ", config=" + config +
                '}';
    }
}
