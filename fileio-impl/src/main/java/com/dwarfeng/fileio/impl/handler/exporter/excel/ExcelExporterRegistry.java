package com.dwarfeng.fileio.impl.handler.exporter.excel;

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
 * Excel 导出器注册。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component
public class ExcelExporterRegistry extends AbstractExporterRegistry {

    public static final String EXPORTER_TYPE = "excel_exporter";

    private final ApplicationContext ctx;

    public ExcelExporterRegistry(ApplicationContext ctx) {
        super(EXPORTER_TYPE);
        this.ctx = ctx;
    }

    @Override
    public String provideLabel() {
        return "Excel 导出器";
    }

    @Override
    public String provideDescription() {
        return "将数据导出至 Excel 文件。";
    }

    @Override
    public String provideExampleParam() {
        List<Config.ColumnSetting> columnSettings = new ArrayList<>();
        columnSettings.add(new Config.ColumnSetting(
                "field-name-1", "A", 0, "excelExporterRegistry.valueMapper.generalBooleanValueMapper"
        ));
        columnSettings.add(new Config.ColumnSetting(
                "field-name-2", "B", 1, "excelExporterRegistry.valueMapper.generalDateValueMapper"
        ));
        columnSettings.add(new Config.ColumnSetting(
                "field-name-3", "C", 2, "excelExporterRegistry.valueMapper.generalDoubleValueMapper"
        ));
        List<Config.SheetSetting> sheetSettings = new ArrayList<>();
        sheetSettings.add(new Config.SheetSetting("reader-identifier-1", "reader-topic-1", 0, 2, columnSettings));
        sheetSettings.add(new Config.SheetSetting("reader-identifier-2", "reader-topic-2", 1, 3, columnSettings));
        Config config = new Config("template-identifier", "file-identifier", "file-name", sheetSettings);
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
            return ctx.getBean(ExcelExporter.class, ctx, config);
        } catch (Exception e) {
            throw new ExporterException(e);
        }
    }

    @Override
    public String toString() {
        return "ExcelExporterRegistry{" +
                "ctx=" + ctx +
                ", exporterType='" + exporterType + '\'' +
                '}';
    }
}
