package com.dwarfeng.fileio.impl.handler.reader.spls;

import com.dwarfeng.fileio.sdk.handler.reader.AbstractReader;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Subgrade PresetLookupService 读取器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component("splsReaderRegistry.splsReader")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class SplsReader extends AbstractReader {

    private final ApplicationContext ctx;

    private final Config config;

    public SplsReader(ApplicationContext ctx, Config config) {
        this.ctx = ctx;
        this.config = config;
    }

    @Override
    protected Executor doNewExecutor() {
        return ctx.getBean(SplsExecutor.class, ctx, config);
    }

    @Override
    public String toString() {
        return "SplsReader{" +
                "ctx=" + ctx +
                ", config=" + config +
                '}';
    }
}
