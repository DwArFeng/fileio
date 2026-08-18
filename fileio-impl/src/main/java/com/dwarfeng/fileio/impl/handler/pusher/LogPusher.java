package com.dwarfeng.fileio.impl.handler.pusher;

import com.alibaba.fastjson.JSON;
import com.dwarfeng.fileio.sdk.bean.dto.FastJsonPurgeFinishedResult;
import com.dwarfeng.fileio.sdk.bean.entity.FastJsonExportTask;
import com.dwarfeng.fileio.sdk.bean.entity.FastJsonExportTaskSetting;
import com.dwarfeng.fileio.sdk.bean.entity.FastJsonImportTask;
import com.dwarfeng.fileio.sdk.bean.entity.FastJsonImportTaskSetting;
import com.dwarfeng.fileio.sdk.handler.pusher.AbstractPusher;
import com.dwarfeng.fileio.stack.bean.dto.PurgeFinishedResult;
import com.dwarfeng.fileio.stack.bean.entity.ExportTask;
import com.dwarfeng.fileio.stack.bean.entity.ExportTaskSetting;
import com.dwarfeng.fileio.stack.bean.entity.ImportTask;
import com.dwarfeng.fileio.stack.bean.entity.ImportTaskSetting;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 将信息输出至日志的推送器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component
public class LogPusher extends AbstractPusher {

    public static final String PUSHER_TYPE = "log";

    private static final Logger LOGGER = LoggerFactory.getLogger(LogPusher.class);

    private static final String LEVEL_TRACE = "TRACE";
    private static final String LEVEL_DEBUG = "DEBUG";
    private static final String LEVEL_INFO = "INFO";
    private static final String LEVEL_WARN = "WARN";
    private static final String LEVEL_ERROR = "ERROR";

    @Value("${com.dwarfeng.fileio.pusher.log.log_level}")
    private String logLevel;

    public LogPusher() {
        super(PUSHER_TYPE);
    }

    @Override
    public void exportReset() throws HandlerException {
        String title = "推送导出重置消息:";
        String message = StringUtils.EMPTY;
        logData(title, message);
    }

    @Override
    public void importReset() throws HandlerException {
        String title = "推送导入重置消息:";
        String message = StringUtils.EMPTY;
        logData(title, message);
    }

    @Override
    public void exportTaskFinished(ExportTaskSetting exportTaskSetting, ExportTask exportTask) throws HandlerException {
        String title = "推送导出完成消息:";
        String message = String.format(
                "导出任务设置\n%s\n导出任务\n%s",
                JSON.toJSONString(FastJsonExportTaskSetting.of(exportTaskSetting), true),
                JSON.toJSONString(FastJsonExportTask.of(exportTask), true)
        );
        logData(title, message);
    }

    @Override
    public void exportTaskFailed(ExportTaskSetting exportTaskSetting, ExportTask exportTask) throws HandlerException {
        String title = "推送导出失败消息:";
        String message = String.format(
                "导出任务设置\n%s\n导出任务\n%s",
                JSON.toJSONString(FastJsonExportTaskSetting.of(exportTaskSetting), true),
                JSON.toJSONString(FastJsonExportTask.of(exportTask), true)
        );
        logData(title, message);
    }

    @Override
    public void exportTaskExpired(ExportTaskSetting exportTaskSetting, ExportTask exportTask) throws HandlerException {
        String title = "推送导出过期消息:";
        String message = String.format(
                "导出任务设置\n%s\n导出任务\n%s",
                JSON.toJSONString(FastJsonExportTaskSetting.of(exportTaskSetting), true),
                JSON.toJSONString(FastJsonExportTask.of(exportTask), true)
        );
        logData(title, message);
    }

    @Override
    public void exportTaskDead(ExportTaskSetting exportTaskSetting, ExportTask exportTask) throws HandlerException {
        String title = "推送导出死亡消息:";
        String message = String.format(
                "导出任务设置\n%s\n导出任务\n%s",
                JSON.toJSONString(FastJsonExportTaskSetting.of(exportTaskSetting), true),
                JSON.toJSONString(FastJsonExportTask.of(exportTask), true)
        );
        logData(title, message);
    }

    @Override
    public void importTaskFinished(ImportTaskSetting importTaskSetting, ImportTask importTask) throws HandlerException {
        String title = "推送导入完成消息:";
        String message = String.format(
                "导入任务设置\n%s\n导入任务\n%s",
                JSON.toJSONString(FastJsonImportTaskSetting.of(importTaskSetting), true),
                JSON.toJSONString(FastJsonImportTask.of(importTask), true)
        );
        logData(title, message);
    }

    @Override
    public void importTaskFailed(ImportTaskSetting importTaskSetting, ImportTask importTask) throws HandlerException {
        String title = "推送导入失败消息:";
        String message = String.format(
                "导入任务设置\n%s\n导入任务\n%s",
                JSON.toJSONString(FastJsonImportTaskSetting.of(importTaskSetting), true),
                JSON.toJSONString(FastJsonImportTask.of(importTask), true)
        );
        logData(title, message);
    }

    @Override
    public void importTaskExpired(ImportTaskSetting importTaskSetting, ImportTask importTask) throws HandlerException {
        String title = "推送导入过期消息:";
        String message = String.format(
                "导入任务设置\n%s\n导入任务\n%s",
                JSON.toJSONString(FastJsonImportTaskSetting.of(importTaskSetting), true),
                JSON.toJSONString(FastJsonImportTask.of(importTask), true)
        );
        logData(title, message);
    }

    @Override
    public void importTaskDead(ImportTaskSetting importTaskSetting, ImportTask importTask) throws HandlerException {
        String title = "推送导入死亡消息:";
        String message = String.format(
                "导入任务设置\n%s\n导入任务\n%s",
                JSON.toJSONString(FastJsonImportTaskSetting.of(importTaskSetting), true),
                JSON.toJSONString(FastJsonImportTask.of(importTask), true)
        );
        logData(title, message);
    }

    @Override
    public void purgeFinished(PurgeFinishedResult purgeFinishedResult) throws HandlerException {
        String title = "推送清除完成消息:";
        String message = String.format(
                "清除完成结果\n%s",
                JSON.toJSONString(FastJsonPurgeFinishedResult.of(purgeFinishedResult), true)
        );
        logData(title, message);
    }

    private void logData(String title, String message) throws HandlerException {
        String logLevel = this.logLevel.toUpperCase();
        logString(title, logLevel);
        if (StringUtils.isNotEmpty(message)) {
            logString(message, logLevel);
        }
    }

    private void logString(String title, String logLevel) throws HandlerException {
        switch (logLevel) {
            case LEVEL_TRACE:
                LOGGER.trace(title);
                return;
            case LEVEL_DEBUG:
                LOGGER.debug(title);
                return;
            case LEVEL_INFO:
                LOGGER.info(title);
                return;
            case LEVEL_WARN:
                LOGGER.warn(title);
                return;
            case LEVEL_ERROR:
                LOGGER.error(title);
                return;
            default:
                throw new HandlerException("未知的日志等级: " + logLevel);
        }
    }

    @Override
    public String toString() {
        return "LogPusher{" +
                "logLevel='" + logLevel + '\'' +
                ", pusherType='" + pusherType + '\'' +
                '}';
    }
}
