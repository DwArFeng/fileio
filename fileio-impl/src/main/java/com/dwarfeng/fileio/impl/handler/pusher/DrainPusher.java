package com.dwarfeng.fileio.impl.handler.pusher;

import com.dwarfeng.fileio.sdk.handler.pusher.AbstractPusher;
import com.dwarfeng.fileio.stack.bean.dto.PurgeFinishedResult;
import com.dwarfeng.fileio.stack.bean.entity.ExportTask;
import com.dwarfeng.fileio.stack.bean.entity.ExportTaskSetting;
import com.dwarfeng.fileio.stack.bean.entity.ImportTask;
import com.dwarfeng.fileio.stack.bean.entity.ImportTaskSetting;
import org.springframework.stereotype.Component;

/**
 * 简单的丢弃掉所有信息的推送器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component
public class DrainPusher extends AbstractPusher {

    public static final String PUSHER_TYPE = "drain";

    public DrainPusher() {
        super(PUSHER_TYPE);
    }

    @Override
    public void exportReset() {
    }

    @Override
    public void importReset() {
    }

    @Override
    public void exportTaskFinished(ExportTaskSetting exportTaskSetting, ExportTask exportTask) {
    }

    @Override
    public void exportTaskFailed(ExportTaskSetting exportTaskSetting, ExportTask exportTask) {
    }

    @Override
    public void exportTaskExpired(ExportTaskSetting exportTaskSetting, ExportTask exportTask) {
    }

    @Override
    public void exportTaskDead(ExportTaskSetting exportTaskSetting, ExportTask exportTask) {
    }

    @Override
    public void importTaskFinished(ImportTaskSetting importTaskSetting, ImportTask importTask) {
    }

    @Override
    public void importTaskFailed(ImportTaskSetting importTaskSetting, ImportTask importTask) {
    }

    @Override
    public void importTaskExpired(ImportTaskSetting importTaskSetting, ImportTask importTask) {
    }

    @Override
    public void importTaskDead(ImportTaskSetting importTaskSetting, ImportTask importTask) {
    }

    @Override
    public void purgeFinished(PurgeFinishedResult purgeFinishedResult) {
    }

    @Override
    public String toString() {
        return "DrainPusher{" +
                "pusherType='" + pusherType + '\'' +
                '}';
    }
}
