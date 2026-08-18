package com.dwarfeng.fileio.impl.handler.exporter.dct;

import com.alibaba.fastjson.JSON;
import com.dwarfeng.dct.stack.handler.ValueCodingHandler;
import com.dwarfeng.fileio.sdk.handler.exporter.AbstractExporterRegistry;
import com.dwarfeng.fileio.stack.exception.ExporterException;
import com.dwarfeng.fileio.stack.exception.ExporterMakeException;
import com.dwarfeng.fileio.stack.handler.Exporter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * 基于 DCT 框架的导出器注册。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component
public class DctExporterRegistry extends AbstractExporterRegistry {

    public static final String EXPORTER_TYPE = "dct_exporter";

    private final ApplicationContext ctx;

    public DctExporterRegistry(ApplicationContext ctx) {
        super(EXPORTER_TYPE);
        this.ctx = ctx;
    }

    @Override
    public String provideLabel() {
        return "基于 DCT 框架的导出器";
    }

    @Override
    public String provideDescription() {
        return "使用 DCT 对数据进行序列化，并将序列化后的数据导出至文本文件。";
    }

    @Override
    public String provideExampleParam() {
        Config config = new Config(
                "reader_identifier", "reader_topic", "file_identifier", "file_name",
                "dctExporterRegistry.valueCodingHandler"
        );
        return JSON.toJSONString(config, true);
    }

    @Override
    public String provideExampleMetadata() {
        return StringUtils.EMPTY;
    }

    @Override
    public Exporter makeExporter(String type, String param) throws ExporterException {
        try {
            Config config = JSON.parseObject(param, Config.class);
            ValueCodingHandler valueCodingHandler = ctx.getBean(
                    config.getValueCodingHandlerBeanName(), ValueCodingHandler.class
            );
            return ctx.getBean(DctExporter.class, ctx, valueCodingHandler, config);
        } catch (Exception e) {
            throw new ExporterMakeException(e);
        }
    }

    @Override
    public String toString() {
        return "DctExporterRegistry{" +
                "ctx=" + ctx +
                ", exporterType='" + exporterType + '\'' +
                '}';
    }
}
