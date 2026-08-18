package com.dwarfeng.fileio.impl.handler;

import com.dwarfeng.fileio.stack.bean.dto.ExportInfo;
import com.dwarfeng.fileio.stack.bean.entity.*;
import com.dwarfeng.fileio.stack.handler.*;
import com.dwarfeng.fileio.stack.service.*;
import com.dwarfeng.subgrade.impl.handler.Fetcher;
import com.dwarfeng.subgrade.impl.handler.GeneralLocalCacheHandler;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ExportLocalCacheHandlerImpl implements ExportLocalCacheHandler {

    private final GeneralLocalCacheHandler<LongIdKey, ExportInfo> handler;

    public ExportLocalCacheHandlerImpl(ExportInfoFetcher exportInfoFetcher) {
        this.handler = new GeneralLocalCacheHandler<>(exportInfoFetcher);
    }

    @BehaviorAnalyse
    @Override
    public boolean exists(LongIdKey key) throws HandlerException {
        return handler.exists(key);
    }

    @BehaviorAnalyse
    @Override
    public ExportInfo get(LongIdKey key) throws HandlerException {
        return handler.get(key);
    }

    @BehaviorAnalyse
    @Override
    public boolean remove(LongIdKey key) {
        return handler.remove(key);
    }

    @BehaviorAnalyse
    @Override
    public void clear() {
        handler.clear();
    }

    @Component
    public static class ExportInfoFetcher implements Fetcher<LongIdKey, ExportInfo> {

        private final ExportTaskSettingMaintainService exportTaskSettingMaintainService;
        private final ExporterInfoMaintainService exporterInfoMaintainService;
        private final ReaderInfoMaintainService readerInfoMaintainService;
        private final ExportConfMaintainService exportConfMaintainService;
        private final ExportTemplateInfoMaintainService exportTemplateInfoMaintainService;

        private final ExporterHandler exporterHandler;
        private final ReaderHandler readerHandler;

        public ExportInfoFetcher(
                ExportTaskSettingMaintainService exportTaskSettingMaintainService,
                ExporterInfoMaintainService exporterInfoMaintainService,
                ReaderInfoMaintainService readerInfoMaintainService,
                ExportConfMaintainService exportConfMaintainService,
                ExportTemplateInfoMaintainService exportTemplateInfoMaintainService,
                ExporterHandler exporterHandler,
                ReaderHandler readerHandler
        ) {
            this.exportTaskSettingMaintainService = exportTaskSettingMaintainService;
            this.exporterInfoMaintainService = exporterInfoMaintainService;
            this.readerInfoMaintainService = readerInfoMaintainService;
            this.exportConfMaintainService = exportConfMaintainService;
            this.exportTemplateInfoMaintainService = exportTemplateInfoMaintainService;
            this.exporterHandler = exporterHandler;
            this.readerHandler = readerHandler;
        }

        @Override
        @BehaviorAnalyse
        @Transactional(
                transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class
        )
        public boolean exists(LongIdKey key) throws Exception {
            return exportTaskSettingMaintainService.exists(key);
        }

        @Override
        @BehaviorAnalyse
        @Transactional(
                transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class
        )
        public ExportInfo fetch(LongIdKey key) throws Exception {
            ExportTaskSetting exportTaskSetting = exportTaskSettingMaintainService.get(key);

            Map<String, ExporterInfo> exporterInfoMap = new HashMap<>();
            Map<String, Exporter> exporterMap = new HashMap<>();
            List<ExporterInfo> exporterInfos = exporterInfoMaintainService.lookupAsList(
                    ExporterInfoMaintainService.CHILD_FOR_TASK_SETTING_ENABLED, new Object[]{key}
            );
            for (ExporterInfo exporterInfo : exporterInfos) {
                exporterInfoMap.put(exporterInfo.getKey().getIdentifier(), exporterInfo);
                Exporter exporter = exporterHandler.make(exporterInfo.getType(), exporterInfo.getParam());
                exporterMap.put(exporterInfo.getKey().getIdentifier(), exporter);
            }

            Map<String, ReaderInfo> readerInfoMap = new HashMap<>();
            Map<String, Reader> readerMap = new HashMap<>();
            List<ReaderInfo> readerInfos = readerInfoMaintainService.lookupAsList(
                    ReaderInfoMaintainService.CHILD_FOR_TASK_SETTING_ENABLED, new Object[]{key}
            );
            for (ReaderInfo readerInfo : readerInfos) {
                readerInfoMap.put(readerInfo.getKey().getIdentifier(), readerInfo);
                Reader reader = readerHandler.make(readerInfo.getType(), readerInfo.getParam());
                readerMap.put(readerInfo.getKey().getIdentifier(), reader);
            }

            Map<String, ExportConf> exportConfMap = new HashMap<>();
            List<ExportConf> exportConfs = exportConfMaintainService.lookupAsList(
                    ExportConfMaintainService.CHILD_FOR_TASK_SETTING, new Object[]{key}
            );
            for (ExportConf exportConf : exportConfs) {
                exportConfMap.put(exportConf.getKey().getIdentifier(), exportConf);
            }

            Map<String, ExportTemplateInfo> exportTemplateInfoMap = new HashMap<>();
            List<ExportTemplateInfo> exportTemplateInfos = exportTemplateInfoMaintainService.lookupAsList(
                    ExportTemplateInfoMaintainService.CHILD_FOR_TASK_SETTING, new Object[]{key}
            );
            for (ExportTemplateInfo exportTemplateInfo : exportTemplateInfos) {
                exportTemplateInfoMap.put(exportTemplateInfo.getKey().getIdentifier(), exportTemplateInfo);
            }

            return new ExportInfo(
                    exportTaskSetting, exporterInfoMap, exporterMap, readerInfoMap, readerMap,
                    exportConfMap, exportTemplateInfoMap
            );
        }
    }
}
