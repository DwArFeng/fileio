package com.dwarfeng.fileio.impl.handler.exporter.log;

import com.dwarfeng.fileio.sdk.handler.exporter.AbstractExporterRegistry;
import com.dwarfeng.fileio.stack.exception.ExporterException;
import com.dwarfeng.fileio.stack.exception.ExporterMakeException;
import com.dwarfeng.fileio.stack.handler.Exporter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * 日志导出器注册。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component
public class LogExporterRegistry extends AbstractExporterRegistry {

    public static final String EXPORTER_TYPE = "log_exporter";

    private final ApplicationContext ctx;

    public LogExporterRegistry(ApplicationContext ctx) {
        super(EXPORTER_TYPE);
        this.ctx = ctx;
    }

    @Override
    public String provideLabel() {
        return "日志导出器";
    }

    @Override
    public String provideDescription() {
        return "将数据导出至日志的导出器。";
    }

    @Override
    public String provideExampleParam() {
        return Constants.LEVEL_INFO;
    }

    @Override
    public String provideExampleMetadata() {
        return StringUtils.EMPTY;
    }

    @Override
    public Exporter makeExporter(String type, String param) throws ExporterException {
        try {
            String logLevel = parseLogLevel(param);
            return ctx.getBean(LogExporter.class, ctx, logLevel);
        } catch (Exception e) {
            throw new ExporterMakeException(e);
        }
    }

    private String parseLogLevel(String param) {
        return StringUtils.upperCase(param);
    }

    @Override
    public String toString() {
        return "LogExporterRegistry{" +
                "ctx=" + ctx +
                ", exporterType='" + exporterType + '\'' +
                '}';
    }
}
