package com.dwarfeng.fileio.impl.handler.reader.mock;

import com.alibaba.fastjson.JSON;
import com.dwarfeng.fileio.sdk.handler.reader.AbstractReaderRegistry;
import com.dwarfeng.fileio.stack.exception.ReaderException;
import com.dwarfeng.fileio.stack.exception.ReaderMakeException;
import com.dwarfeng.fileio.stack.handler.Reader;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 模拟读取器注册。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component
public class MockReaderRegistry extends AbstractReaderRegistry {

    public static final String READER_TYPE = "mock_reader";

    private final ApplicationContext ctx;

    public MockReaderRegistry(ApplicationContext ctx) {
        super(READER_TYPE);
        this.ctx = ctx;
    }

    @Override
    public String provideLabel() {
        return "模拟读取器";
    }

    @Override
    public String provideDescription() {
        return "生成随机数据，模拟读取器的读取操作。";
    }

    @Override
    public String provideExampleParam() {
        List<Config.FieldItem> fieldItems = new ArrayList<>();
        fieldItems.add(new Config.FieldItem("field_name_1", "field_type_1"));
        fieldItems.add(new Config.FieldItem("field_name_2", "field_type_2"));
        fieldItems.add(new Config.FieldItem("field_name_3", "field_type_3"));
        Config config = new Config(12450L, fieldItems, 200, "mock_reader_metadata");
        return JSON.toJSONString(config, true);
    }

    @Override
    public String provideExampleMetadata() {
        Metadata metadata = new Metadata(1000);
        return JSON.toJSONString(metadata, true);
    }

    @Override
    public Reader makeReader(String type, String param) throws ReaderException {
        try {
            Config config = JSON.parseObject(param, Config.class);
            return ctx.getBean(MockReader.class, ctx, config);
        } catch (Exception e) {
            throw new ReaderMakeException(e);
        }
    }

    @Override
    public String toString() {
        return "MockReaderRegistry{" +
                "ctx=" + ctx +
                '}';
    }
}
