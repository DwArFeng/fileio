package com.dwarfeng.fileio.impl.handler.reader.mock;

import com.dwarfeng.fileio.sdk.handler.reader.AbstractReader;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 模拟导入器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component("mockReaderRegistry.mockReader")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MockReader extends AbstractReader {

    private final ApplicationContext ctx;

    private final Config config;

    public MockReader(ApplicationContext ctx, Config config) {
        this.ctx = ctx;
        this.config = config;
    }

    @Override
    protected Executor doNewExecutor() {
        return ctx.getBean(MockExecutor.class, config);
    }

    @Override
    public String toString() {
        return "MockReader{" +
                "ctx=" + ctx +
                ", config=" + config +
                '}';
    }
}
