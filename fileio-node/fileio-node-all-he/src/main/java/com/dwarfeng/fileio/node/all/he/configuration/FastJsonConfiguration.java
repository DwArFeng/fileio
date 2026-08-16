package com.dwarfeng.fileio.node.all.he.configuration;

import com.alibaba.fastjson.parser.ParserConfig;
import com.dwarfeng.fileio.sdk.bean.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FastJsonConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(FastJsonConfiguration.class);

    public FastJsonConfiguration() {
        LOGGER.info("正在配置 FastJson autotype 白名单");
        ParserConfig.getGlobalInstance().addAccept(FastJsonExportConf.class.getCanonicalName());
        ParserConfig.getGlobalInstance().addAccept(FastJsonExporterInfo.class.getCanonicalName());
        ParserConfig.getGlobalInstance().addAccept(FastJsonExporterSupport.class.getCanonicalName());
        ParserConfig.getGlobalInstance().addAccept(FastJsonExportFileInfo.class.getCanonicalName());
        ParserConfig.getGlobalInstance().addAccept(FastJsonExportTask.class.getCanonicalName());
        ParserConfig.getGlobalInstance().addAccept(FastJsonExportTaskSetting.class.getCanonicalName());
        ParserConfig.getGlobalInstance().addAccept(FastJsonExportTemplateInfo.class.getCanonicalName());
        ParserConfig.getGlobalInstance().addAccept(FastJsonImportConf.class.getCanonicalName());
        ParserConfig.getGlobalInstance().addAccept(FastJsonImporterInfo.class.getCanonicalName());
        ParserConfig.getGlobalInstance().addAccept(FastJsonImporterSupport.class.getCanonicalName());
        ParserConfig.getGlobalInstance().addAccept(FastJsonImportFileInfo.class.getCanonicalName());
        ParserConfig.getGlobalInstance().addAccept(FastJsonImportTask.class.getCanonicalName());
        ParserConfig.getGlobalInstance().addAccept(FastJsonImportTaskSetting.class.getCanonicalName());
        ParserConfig.getGlobalInstance().addAccept(FastJsonImportTemplateInfo.class.getCanonicalName());
        ParserConfig.getGlobalInstance().addAccept(FastJsonReaderInfo.class.getCanonicalName());
        ParserConfig.getGlobalInstance().addAccept(FastJsonReaderSupport.class.getCanonicalName());
        ParserConfig.getGlobalInstance().addAccept(FastJsonWriterInfo.class.getCanonicalName());
        ParserConfig.getGlobalInstance().addAccept(FastJsonWriterSupport.class.getCanonicalName());
        ParserConfig.getGlobalInstance().addAccept(FastJsonExportMetadata.class.getCanonicalName());
        ParserConfig.getGlobalInstance().addAccept(FastJsonImportMetadata.class.getCanonicalName());
        ParserConfig.getGlobalInstance().addAccept(FastJsonUser.class.getCanonicalName());
        LOGGER.debug("FastJson autotype 白名单配置完毕");
    }
}
