package com.dwarfeng.fileio.impl.handler.reader.filter;

import com.alibaba.fastjson.JSON;
import com.dwarfeng.fileio.sdk.handler.reader.AbstractReaderRegistry;
import com.dwarfeng.fileio.stack.exception.ReaderException;
import com.dwarfeng.fileio.stack.handler.Reader;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 过滤读取器注册。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component
public class FilterReaderRegistry extends AbstractReaderRegistry {

    public static final String READER_TYPE = "filter_reader";

    private final ApplicationContext ctx;

    public FilterReaderRegistry(ApplicationContext ctx) {
        super(READER_TYPE);
        this.ctx = ctx;
    }

    @Override
    public String provideLabel() {
        return "过滤读取器";
    }

    @Override
    public String provideDescription() {
        return "使用一个读取器读取数据，随后使用映射器链对数据进行映射。";
    }

    @Override
    public String provideExampleParam() {
        Config.ReaderMakerConfig readerMakerConfig = new Config.ReaderMakerConfig(
                "bean-name-here", "reader-type-here", "reader-param-here"
        );
        List<Config.MapperMakerConfig> mapperMakerConfigs = new ArrayList<>();
        mapperMakerConfigs.add(new Config.MapperMakerConfig("bean-name-here-1", "mapper-param-here-1"));
        mapperMakerConfigs.add(new Config.MapperMakerConfig("bean-name-here-2", "mapper-param-here-2"));
        mapperMakerConfigs.add(new Config.MapperMakerConfig("bean-name-here-3", "mapper-param-here-3"));
        Config config = new Config(readerMakerConfig, mapperMakerConfigs);
        return JSON.toJSONString(config, true);
    }

    @Override
    public String provideExampleMetadata() {
        return StringUtils.EMPTY;
    }

    @Override
    public Reader makeReader(String type, String param) throws ReaderException {
        try {
            Config config = JSON.parseObject(param, Config.class);
            return ctx.getBean(FilterReader.class, ctx, config);
        } catch (Exception e) {
            throw new ReaderException(e);
        }
    }
}
