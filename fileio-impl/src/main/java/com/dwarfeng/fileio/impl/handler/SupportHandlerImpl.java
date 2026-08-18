package com.dwarfeng.fileio.impl.handler;

import com.dwarfeng.fileio.sdk.handler.ExporterSupporter;
import com.dwarfeng.fileio.sdk.handler.ImporterSupporter;
import com.dwarfeng.fileio.sdk.handler.ReaderSupporter;
import com.dwarfeng.fileio.stack.bean.entity.ExporterSupport;
import com.dwarfeng.fileio.stack.bean.entity.ImporterSupport;
import com.dwarfeng.fileio.stack.bean.entity.ReaderSupport;
import com.dwarfeng.fileio.stack.bean.entity.WriterSupport;
import com.dwarfeng.fileio.stack.handler.SupportHandler;
import com.dwarfeng.fileio.stack.service.ExporterSupportMaintainService;
import com.dwarfeng.fileio.stack.service.ImporterSupportMaintainService;
import com.dwarfeng.fileio.stack.service.ReaderSupportMaintainService;
import com.dwarfeng.fileio.stack.service.WriterSupportMaintainService;
import com.dwarfeng.subgrade.sdk.exception.HandlerExceptionHelper;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class SupportHandlerImpl implements SupportHandler {

    private final ExporterSupportMaintainService exporterSupportMaintainService;

    private final ImporterSupportMaintainService importerSupportMaintainService;

    private final ReaderSupportMaintainService readerSupportMaintainService;

    private final WriterSupportMaintainService writerSupportMaintainService;

    private final List<ExporterSupporter> exporterSupporters;

    private final List<ImporterSupporter> importerSupporters;

    private final List<ReaderSupporter> readerSupporters;

    private final List<com.dwarfeng.fileio.sdk.handler.WriterSupporter> writerSupporters;

    public SupportHandlerImpl(
            ExporterSupportMaintainService exporterSupportMaintainService,
            ImporterSupportMaintainService importerSupportMaintainService,
            ReaderSupportMaintainService readerSupportMaintainService,
            WriterSupportMaintainService writerSupportMaintainService,
            List<ExporterSupporter> exporterSupporters,
            List<ImporterSupporter> importerSupporters,
            List<ReaderSupporter> readerSupporters,
            List<com.dwarfeng.fileio.sdk.handler.WriterSupporter> writerSupporters
    ) {
        this.exporterSupportMaintainService = exporterSupportMaintainService;
        this.importerSupportMaintainService = importerSupportMaintainService;
        this.readerSupportMaintainService = readerSupportMaintainService;
        this.writerSupportMaintainService = writerSupportMaintainService;
        this.exporterSupporters = Optional.ofNullable(exporterSupporters).orElse(Collections.emptyList());
        this.importerSupporters = Optional.ofNullable(importerSupporters).orElse(Collections.emptyList());
        this.readerSupporters = Optional.ofNullable(readerSupporters).orElse(Collections.emptyList());
        this.writerSupporters = Optional.ofNullable(writerSupporters).orElse(Collections.emptyList());
    }

    @Override
    @BehaviorAnalyse
    public void resetExporter() throws HandlerException {
        try {
            doResetExporter();
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    private void doResetExporter() throws Exception {
        List<StringIdKey> exporterKeys = exporterSupportMaintainService.lookupAsList().stream()
                .map(ExporterSupport::getKey).collect(Collectors.toList());
        exporterSupportMaintainService.batchDelete(exporterKeys);
        List<ExporterSupport> exporterSupports = exporterSupporters.stream().map(
                supporter -> new ExporterSupport(
                        new StringIdKey(supporter.provideType()),
                        supporter.provideLabel(),
                        supporter.provideDescription(),
                        supporter.provideExampleParam(),
                        supporter.provideExampleMetadata()
                )).collect(Collectors.toList());
        exporterSupportMaintainService.batchInsert(exporterSupports);
    }

    @Override
    @BehaviorAnalyse
    public void resetImporter() throws HandlerException {
        try {
            doResetImporter();
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    private void doResetImporter() throws Exception {
        List<StringIdKey> importerKeys = importerSupportMaintainService.lookupAsList().stream()
                .map(ImporterSupport::getKey).collect(Collectors.toList());
        importerSupportMaintainService.batchDelete(importerKeys);
        List<ImporterSupport> importerSupports = importerSupporters.stream().map(
                supporter -> new ImporterSupport(
                        new StringIdKey(supporter.provideType()),
                        supporter.provideLabel(),
                        supporter.provideDescription(),
                        supporter.provideExampleParam(),
                        supporter.provideExampleMetadata()
                )).collect(Collectors.toList());
        importerSupportMaintainService.batchInsert(importerSupports);
    }

    @Override
    @BehaviorAnalyse
    public void resetReader() throws HandlerException {
        try {
            doResetReader();
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    private void doResetReader() throws Exception {
        List<StringIdKey> readerKeys = readerSupportMaintainService.lookupAsList().stream()
                .map(ReaderSupport::getKey).collect(Collectors.toList());
        readerSupportMaintainService.batchDelete(readerKeys);
        List<ReaderSupport> readerSupports = readerSupporters.stream().map(
                supporter -> new ReaderSupport(
                        new StringIdKey(supporter.provideType()),
                        supporter.provideLabel(),
                        supporter.provideDescription(),
                        supporter.provideExampleParam(),
                        supporter.provideExampleMetadata()
                )).collect(Collectors.toList());
        readerSupportMaintainService.batchInsert(readerSupports);
    }

    @Override
    @BehaviorAnalyse
    public void resetWriter() throws HandlerException {
        try {
            doResetWriter();
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    private void doResetWriter() throws Exception {
        List<StringIdKey> writerKeys = writerSupportMaintainService.lookupAsList().stream()
                .map(WriterSupport::getKey).collect(Collectors.toList());
        writerSupportMaintainService.batchDelete(writerKeys);
        List<WriterSupport> writerSupports = writerSupporters.stream().map(
                supporter -> new WriterSupport(
                        new StringIdKey(supporter.provideType()),
                        supporter.provideLabel(),
                        supporter.provideDescription(),
                        supporter.provideExampleParam(),
                        supporter.provideExampleMetadata()
                )).collect(Collectors.toList());
        writerSupportMaintainService.batchInsert(writerSupports);
    }
}
