package com.dwarfeng.fileio.impl.handler.reader.jdbc;

import com.alibaba.fastjson.JSON;
import com.dwarfeng.dct.stack.handler.ValueCodingHandler;
import com.dwarfeng.fileio.sdk.handler.reader.AbstractReaderRegistry;
import com.dwarfeng.fileio.stack.exception.ReaderException;
import com.dwarfeng.fileio.stack.handler.Reader;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * JDBC 读取器注册。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component
public class JdbcReaderRegistry extends AbstractReaderRegistry {

    public static final String READER_TYPE = "jdbc_reader";

    private final ApplicationContext ctx;

    public JdbcReaderRegistry(ApplicationContext ctx) {
        super(READER_TYPE);
        this.ctx = ctx;
    }

    @Override
    public String provideLabel() {
        return "JDBC 读取器";
    }

    @Override
    public String provideDescription() {
        return "使用 JDBC 读取数据。";
    }

    @Override
    public String provideExampleParam() {
        List<String> resultFields = new ArrayList<>();
        resultFields.add("c1");
        resultFields.add("c2");
        Config config = new Config(
                "your-jdbc-driver-here", "your-jdbc-url-here", "your-jdbc-username-here", "your-jdbc-password-here",
                "SELECT c1, c2 FROM table WHERE c1 > ? AND c2 < ?", 200, 60000, "jdbc_reader_metadata", resultFields,
                "jdbcReaderRegistry.valueCodingHandler"
        );
        return JSON.toJSONString(config, true);
    }

    @Override
    public String provideExampleMetadata() {
        List<String> queryParams = new ArrayList<>();
        queryParams.add("int:1000");
        queryParams.add("int:2000");
        Metadata metadata = new Metadata(queryParams);
        return JSON.toJSONString(metadata, true);
    }

    @Override
    public Reader makeReader(String type, String param) throws ReaderException {
        try {
            Config config = JSON.parseObject(param, Config.class);
            ValueCodingHandler valueCodingHandler = ctx.getBean(
                    config.getValueCodingHandlerBeanName(), ValueCodingHandler.class
            );
            return ctx.getBean(JdbcReader.class, ctx, config, valueCodingHandler);
        } catch (Exception e) {
            throw new ReaderException(e);
        }
    }

    @Override
    public String toString() {
        return "JdbcReaderRegistry{" +
                "ctx=" + ctx +
                ", readerType='" + readerType + '\'' +
                '}';
    }
}
