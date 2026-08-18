package com.dwarfeng.fileio.impl.handler;

import com.dwarfeng.fileio.sdk.handler.ImporterMaker;
import com.dwarfeng.fileio.stack.exception.ImporterException;
import com.dwarfeng.fileio.stack.exception.UnsupportedImporterTypeException;
import com.dwarfeng.fileio.stack.handler.Importer;
import com.dwarfeng.fileio.stack.handler.ImporterHandler;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class ImporterHandlerImpl implements ImporterHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImporterHandlerImpl.class);

    private final List<ImporterMaker> importerMakers;

    public ImporterHandlerImpl(List<ImporterMaker> importerMakers) {
        this.importerMakers = Optional.ofNullable(importerMakers).orElse(Collections.emptyList());
    }

    @Override
    public Importer make(String type, String param) throws HandlerException {
        try {
            // 生成导入器。
            LOGGER.debug("通过导入器信息构建新的的导入器...");
            ImporterMaker importerMaker = importerMakers.stream().filter(maker -> maker.supportType(type))
                    .findFirst().orElseThrow(() -> new UnsupportedImporterTypeException(type));
            Importer importer = importerMaker.makeImporter(type, param);
            LOGGER.debug("导入器构建成功!");
            LOGGER.debug("导入器: {}", importer);
            return importer;
        } catch (ImporterException e) {
            throw e;
        } catch (Exception e) {
            throw new ImporterException(e);
        }
    }
}
