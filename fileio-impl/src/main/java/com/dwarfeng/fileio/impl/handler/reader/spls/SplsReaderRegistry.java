package com.dwarfeng.fileio.impl.handler.reader.spls;

import com.alibaba.fastjson.JSON;
import com.dwarfeng.fileio.sdk.handler.reader.AbstractReaderRegistry;
import com.dwarfeng.fileio.stack.exception.ReaderException;
import com.dwarfeng.fileio.stack.handler.Reader;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Subgrade PresetLookupService 读取器注册。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component
public class SplsReaderRegistry extends AbstractReaderRegistry {

    public static final String READER_TYPE = "spls_reader";

    private final ApplicationContext ctx;

    public SplsReaderRegistry(ApplicationContext ctx) {
        super(READER_TYPE);
        this.ctx = ctx;
    }

    @Override
    public String provideLabel() {
        return "Subgrade PresetLookupService 读取器";
    }

    @Override
    public String provideDescription() {
        return "使用 Subgrade PresetLookupService 读取数据。";
    }

    @Override
    public String provideExampleParam() {
        List<Config.FieldSetting> fieldSettings = new ArrayList<>();
        fieldSettings.add(new Config.FieldSetting("entity-field-expression-1", "field-name-1"));
        fieldSettings.add(new Config.FieldSetting("entity-field-expression-2", "field-name-2"));
        fieldSettings.add(new Config.FieldSetting("entity-field-expression-3", "field-name-3"));
        Config config = new Config(
                "preset-lookup-service-bean-name-here", 200, 60000, "metadata-identifier-here",
                "splsReaderRegistry.argsResolver.fastJsonArgsResolver", fieldSettings
        );
        return JSON.toJSONString(config, true);
    }

    @Override
    public String provideExampleMetadata() {
        Metadata metadata = new Metadata("preset-here", "args-string-here");
        return JSON.toJSONString(metadata, true);
    }

    @Override
    public Reader makeReader(String type, String param) throws ReaderException {
        try {
            Config config = JSON.parseObject(param, Config.class);
            return ctx.getBean(SplsReader.class, ctx, config);
        } catch (Exception e) {
            throw new ReaderException(e);
        }
    }

    @Override
    public String toString() {
        return "SplsReaderRegistry{" +
                "ctx=" + ctx +
                ", readerType='" + readerType + '\'' +
                '}';
    }
}
