package com.dwarfeng.fileio.impl.handler.reader.sels;

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
 * Subgrade EntireLookupService 读取器注册。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component
public class SelsReaderRegistry extends AbstractReaderRegistry {

    public static final String READER_TYPE = "sels_reader";

    private final ApplicationContext ctx;

    public SelsReaderRegistry(ApplicationContext ctx) {
        super(READER_TYPE);
        this.ctx = ctx;
    }

    @Override
    public String provideLabel() {
        return "Subgrade EntireLookupService 读取器";
    }

    @Override
    public String provideDescription() {
        return "使用 Subgrade EntireLookupService 读取数据。";
    }

    @Override
    public String provideExampleParam() {
        List<Config.FieldSetting> fieldSettings = new ArrayList<>();
        fieldSettings.add(new Config.FieldSetting("entity-field-expression-1", "field-name-1"));
        fieldSettings.add(new Config.FieldSetting("entity-field-expression-2", "field-name-2"));
        fieldSettings.add(new Config.FieldSetting("entity-field-expression-3", "field-name-3"));
        Config config = new Config("bean-name-here", 200, 60000, fieldSettings);
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
            return ctx.getBean(SelsReader.class, ctx, config);
        } catch (Exception e) {
            throw new ReaderException(e);
        }
    }

    @Override
    public String toString() {
        return "SelsReaderRegistry{" +
                "ctx=" + ctx +
                ", readerType='" + readerType + '\'' +
                '}';
    }
}
