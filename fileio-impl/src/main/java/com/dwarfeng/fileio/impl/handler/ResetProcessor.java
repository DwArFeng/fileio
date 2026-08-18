package com.dwarfeng.fileio.impl.handler;

import com.dwarfeng.fileio.stack.handler.ExportLocalCacheHandler;
import com.dwarfeng.fileio.stack.handler.ImportLocalCacheHandler;
import com.dwarfeng.fileio.stack.handler.PushHandler;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 重置处理器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component
public class ResetProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResetProcessor.class);

    private final ExportLocalCacheHandler exportLocalCacheHandler;
    private final ImportLocalCacheHandler importLocalCacheHandler;

    private final PushHandler pushHandler;

    public ResetProcessor(
            ExportLocalCacheHandler exportLocalCacheHandler,
            ImportLocalCacheHandler importLocalCacheHandler,
            PushHandler pushHandler
    ) {
        this.exportLocalCacheHandler = exportLocalCacheHandler;
        this.importLocalCacheHandler = importLocalCacheHandler;
        this.pushHandler = pushHandler;
    }

    public void resetExport() throws HandlerException {
        // 重置导出。
        exportLocalCacheHandler.clear();

        // 推送消息。
        try {
            pushHandler.exportReset();
        } catch (Exception e) {
            LOGGER.warn("推送记录功能重置消息时发生异常, 本次消息将不会被推送, 异常信息如下: ", e);
        }
    }

    public void resetImport() throws HandlerException {
        // 重置导入。
        importLocalCacheHandler.clear();

        // 推送消息。
        try {
            pushHandler.importReset();
        } catch (Exception e) {
            LOGGER.warn("推送记录功能重置消息时发生异常, 本次消息将不会被推送, 异常信息如下: ", e);
        }
    }
}
