package com.dwarfeng.fileio.impl.handler.importer.dct;

import com.alibaba.fastjson.JSON;
import com.dwarfeng.dct.stack.handler.ValueCodingHandler;
import com.dwarfeng.fileio.sdk.handler.importer.AbstractImporterRegistry;
import com.dwarfeng.fileio.stack.exception.ImporterException;
import com.dwarfeng.fileio.stack.exception.ImporterMakeException;
import com.dwarfeng.fileio.stack.handler.Importer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * 基于 DCT 框架的导入器注册。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component
public class DctImporterRegistry extends AbstractImporterRegistry {

    public static final String IMPORTER_TYPE = "dct_importer";

    private final ApplicationContext ctx;

    public DctImporterRegistry(ApplicationContext ctx) {
        super(IMPORTER_TYPE);
        this.ctx = ctx;
    }

    @Override
    public String provideLabel() {
        return "基于 DCT 框架的导入器";
    }

    @Override
    public String provideDescription() {
        return "使用 DCT 对数据进行序列化，并将序列化后的数据导入至文本文件。";
    }

    @Override
    public String provideExampleParam() {
        Config config = new Config("file_identifier", 200, "dctImporterRegistry.valueCodingHandler");
        return JSON.toJSONString(config, true);
    }

    @Override
    public String provideExampleMetadata() {
        return StringUtils.EMPTY;
    }

    @Override
    public Importer makeImporter(String type, String param) throws ImporterException {
        try {
            Config config = JSON.parseObject(param, Config.class);
            ValueCodingHandler valueCodingHandler = ctx.getBean(
                    config.getValueCodingHandlerBeanName(), ValueCodingHandler.class
            );
            return ctx.getBean(DctImporter.class, ctx, valueCodingHandler, config);
        } catch (Exception e) {
            throw new ImporterMakeException(e);
        }
    }

    @Override
    public String toString() {
        return "DctImporterRegistry{" +
                "ctx=" + ctx +
                ", importerType='" + importerType + '\'' +
                '}';
    }
}
