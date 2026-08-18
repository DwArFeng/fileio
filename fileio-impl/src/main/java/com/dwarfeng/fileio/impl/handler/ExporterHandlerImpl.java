package com.dwarfeng.fileio.impl.handler;

import com.dwarfeng.fileio.sdk.handler.ExporterMaker;
import com.dwarfeng.fileio.stack.exception.ExporterException;
import com.dwarfeng.fileio.stack.exception.UnsupportedExporterTypeException;
import com.dwarfeng.fileio.stack.handler.Exporter;
import com.dwarfeng.fileio.stack.handler.ExporterHandler;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class ExporterHandlerImpl implements ExporterHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExporterHandlerImpl.class);

    private final List<ExporterMaker> exporterMakers;

    public ExporterHandlerImpl(List<ExporterMaker> exporterMakers) {
        this.exporterMakers = Optional.ofNullable(exporterMakers).orElse(Collections.emptyList());
    }

    @Override
    public Exporter make(String type, String param) throws HandlerException {
        try {
            // 生成导出器。
            LOGGER.debug("通过导出器信息构建新的的导出器...");
            ExporterMaker exporterMaker = exporterMakers.stream().filter(maker -> maker.supportType(type))
                    .findFirst().orElseThrow(() -> new UnsupportedExporterTypeException(type));
            Exporter exporter = exporterMaker.makeExporter(type, param);
            LOGGER.debug("导出器构建成功!");
            LOGGER.debug("导出器: {}", exporter);
            return exporter;
        } catch (ExporterException e) {
            throw e;
        } catch (Exception e) {
            throw new ExporterException(e);
        }
    }
}
