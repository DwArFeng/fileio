package com.dwarfeng.fileio.impl.handler;

import com.dwarfeng.fileio.stack.bean.dto.ImportInfo;
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
public class ImportLocalCacheHandlerImpl implements ImportLocalCacheHandler {

    private final GeneralLocalCacheHandler<LongIdKey, ImportInfo> handler;

    public ImportLocalCacheHandlerImpl(ImportInfoFetcher importInfoFetcher) {
        this.handler = new GeneralLocalCacheHandler<>(importInfoFetcher);
    }

    @BehaviorAnalyse
    @Override
    public boolean exists(LongIdKey key) throws HandlerException {
        return handler.exists(key);
    }

    @BehaviorAnalyse
    @Override
    public ImportInfo get(LongIdKey key) throws HandlerException {
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
    public static class ImportInfoFetcher implements Fetcher<LongIdKey, ImportInfo> {

        private final ImportTaskSettingMaintainService importTaskSettingMaintainService;
        private final ImporterInfoMaintainService importerInfoMaintainService;
        private final WriterInfoMaintainService writerInfoMaintainService;
        private final ImportConfMaintainService importConfMaintainService;
        private final ImportTemplateInfoMaintainService importTemplateInfoMaintainService;

        private final ImporterHandler importerHandler;
        private final WriterHandler writerHandler;

        public ImportInfoFetcher(
                ImportTaskSettingMaintainService importTaskSettingMaintainService,
                ImporterInfoMaintainService importerInfoMaintainService,
                WriterInfoMaintainService writerInfoMaintainService,
                ImportConfMaintainService importConfMaintainService,
                ImportTemplateInfoMaintainService importTemplateInfoMaintainService,
                ImporterHandler importerHandler,
                WriterHandler writerHandler
        ) {
            this.importTaskSettingMaintainService = importTaskSettingMaintainService;
            this.importerInfoMaintainService = importerInfoMaintainService;
            this.writerInfoMaintainService = writerInfoMaintainService;
            this.importConfMaintainService = importConfMaintainService;
            this.importTemplateInfoMaintainService = importTemplateInfoMaintainService;
            this.importerHandler = importerHandler;
            this.writerHandler = writerHandler;
        }

        @Override
        @BehaviorAnalyse
        @Transactional(
                transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class
        )
        public boolean exists(LongIdKey key) throws Exception {
            return importTaskSettingMaintainService.exists(key);
        }

        @Override
        @BehaviorAnalyse
        @Transactional(
                transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class
        )
        public ImportInfo fetch(LongIdKey key) throws Exception {
            ImportTaskSetting importTaskSetting = importTaskSettingMaintainService.get(key);

            Map<String, ImporterInfo> importerInfoMap = new HashMap<>();
            Map<String, Importer> importerMap = new HashMap<>();
            List<ImporterInfo> importerInfos = importerInfoMaintainService.lookupAsList(
                    ImporterInfoMaintainService.CHILD_FOR_TASK_SETTING_ENABLED, new Object[]{key}
            );
            for (ImporterInfo importerInfo : importerInfos) {
                importerInfoMap.put(importerInfo.getKey().getIdentifier(), importerInfo);
                Importer importer = importerHandler.make(importerInfo.getType(), importerInfo.getParam());
                importerMap.put(importerInfo.getKey().getIdentifier(), importer);
            }

            Map<String, WriterInfo> writerInfoMap = new HashMap<>();
            Map<String, Writer> writerMap = new HashMap<>();
            List<WriterInfo> writerInfos = writerInfoMaintainService.lookupAsList(
                    WriterInfoMaintainService.CHILD_FOR_TASK_SETTING_ENABLED, new Object[]{key}
            );
            for (WriterInfo writerInfo : writerInfos) {
                writerInfoMap.put(writerInfo.getKey().getIdentifier(), writerInfo);
                Writer writer = writerHandler.make(writerInfo.getType(), writerInfo.getParam());
                writerMap.put(writerInfo.getKey().getIdentifier(), writer);
            }

            Map<String, ImportConf> importConfMap = new HashMap<>();
            List<ImportConf> importConfs = importConfMaintainService.lookupAsList(
                    ImportConfMaintainService.CHILD_FOR_TASK_SETTING, new Object[]{key}
            );
            for (ImportConf importConf : importConfs) {
                importConfMap.put(importConf.getKey().getIdentifier(), importConf);
            }

            Map<String, ImportTemplateInfo> importTemplateInfoMap = new HashMap<>();
            List<ImportTemplateInfo> importTemplateInfos = importTemplateInfoMaintainService.lookupAsList(
                    ImportTemplateInfoMaintainService.CHILD_FOR_TASK_SETTING, new Object[]{key}
            );
            for (ImportTemplateInfo importTemplateInfo : importTemplateInfos) {
                importTemplateInfoMap.put(importTemplateInfo.getKey().getIdentifier(), importTemplateInfo);
            }

            return new ImportInfo(
                    importTaskSetting, importerInfoMap, importerMap, writerInfoMap, writerMap,
                    importConfMap, importTemplateInfoMap
            );
        }
    }
}
