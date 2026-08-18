package com.dwarfeng.fileio.impl.handler.writer.log;

import com.dwarfeng.fileio.sdk.handler.writer.AbstractWriterRegistry;
import com.dwarfeng.fileio.stack.exception.WriterException;
import com.dwarfeng.fileio.stack.exception.WriterMakeException;
import com.dwarfeng.fileio.stack.handler.Writer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * 日志写入器注册。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component
public class LogWriterRegistry extends AbstractWriterRegistry {

    public static final String WRITER_TYPE = "log_writer";

    private final ApplicationContext ctx;

    public LogWriterRegistry(ApplicationContext ctx) {
        super(WRITER_TYPE);
        this.ctx = ctx;
    }

    @Override
    public String provideLabel() {
        return "日志写入器";
    }

    @Override
    public String provideDescription() {
        return "将数据写入至日志的写入器。";
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
    public Writer makeWriter(String type, String param) throws WriterException {
        try {
            String logLevel = parseLogLevel(param);
            return ctx.getBean(LogWriter.class, ctx, logLevel);
        } catch (Exception e) {
            throw new WriterMakeException(e);
        }
    }

    private String parseLogLevel(String param) {
        return StringUtils.upperCase(param);
    }

    @Override
    public String toString() {
        return "LogWriterRegistry{" +
                "ctx=" + ctx +
                ", writerType='" + writerType + '\'' +
                '}';
    }
}
