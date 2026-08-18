package com.dwarfeng.fileio.impl.handler.reader.jdbc;

import com.dwarfeng.dct.stack.handler.ValueCodingHandler;
import com.dwarfeng.fileio.sdk.handler.reader.AbstractReader;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * JDBC 读取器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component("jdbcReaderRegistry.jdbcReader")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class JdbcReader extends AbstractReader {

    private final ApplicationContext ctx;

    private final Config config;

    private final ValueCodingHandler valueCodingHandler;

    public JdbcReader(ApplicationContext ctx, Config config, ValueCodingHandler valueCodingHandler) {
        this.ctx = ctx;
        this.config = config;
        this.valueCodingHandler = valueCodingHandler;
    }

    @Override
    protected Executor doNewExecutor() {
        return ctx.getBean(JdbcExecutor.class, config, valueCodingHandler);
    }

    @Override
    public String toString() {
        return "JdbcReader{" +
                "ctx=" + ctx +
                ", config=" + config +
                ", valueCodingHandler=" + valueCodingHandler +
                '}';
    }
}
