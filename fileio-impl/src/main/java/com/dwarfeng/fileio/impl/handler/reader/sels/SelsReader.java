package com.dwarfeng.fileio.impl.handler.reader.sels;

import com.dwarfeng.fileio.sdk.handler.reader.AbstractReader;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Subgrade EntireLookupService 读取器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component("selsReaderRegistry.selsReader")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class SelsReader extends AbstractReader {

    private final ApplicationContext ctx;

    private final Config config;

    public SelsReader(ApplicationContext ctx, Config config) {
        this.ctx = ctx;
        this.config = config;
    }

    @Override
    protected Executor doNewExecutor() {
        return ctx.getBean(SelsExecutor.class, ctx, config);
    }

    @Override
    public String toString() {
        return "SelsReader{" +
                "ctx=" + ctx +
                ", config=" + config +
                '}';
    }
}
