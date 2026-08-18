package com.dwarfeng.fileio.impl.handler.exporter.csv;

import com.alibaba.fastjson.JSON;
import com.dwarfeng.fileio.sdk.handler.exporter.AbstractExporterRegistry;
import com.dwarfeng.fileio.stack.exception.ExporterException;
import com.dwarfeng.fileio.stack.handler.Exporter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * CSV 导出器注册。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component
public class CsvExporterRegistry extends AbstractExporterRegistry {

    public static final String EXPORTER_TYPE = "csv_exporter";

    private final ApplicationContext ctx;

    public CsvExporterRegistry(ApplicationContext ctx) {
        super(EXPORTER_TYPE);
        this.ctx = ctx;
    }

    @Override
    public String provideLabel() {
        return "CSV 导出器";
    }

    @Override
    public String provideDescription() {
        return "将数据导出至 CSV 文件。";
    }

    @Override
    public String provideExampleParam() {
        List<Config.Column> columns = new ArrayList<>();
        columns.add(new Config.Column("column_name_1", "field_name_1"));
        columns.add(new Config.Column("column_name_2", "field_name_2"));
        columns.add(new Config.Column("column_name_3", "field_name_3"));
        Config config = new Config(
                "reader_identifier", "reader_topic", "csvExporterRegistry.dctValueSerializer", columns,
                "file_identifier", "file_name"
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
            return ctx.getBean(CsvExporter.class, ctx, config);
        } catch (Exception e) {
            throw new ExporterException(e);
        }
    }

    @Override
    public String toString() {
        return "CsvExporterRegistry{" +
                "ctx=" + ctx +
                ", exporterType='" + exporterType + '\'' +
                '}';
    }
}
