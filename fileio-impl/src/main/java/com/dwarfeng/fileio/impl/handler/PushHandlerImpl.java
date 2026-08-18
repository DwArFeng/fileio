package com.dwarfeng.fileio.impl.handler;

import com.dwarfeng.fileio.sdk.handler.Pusher;
import com.dwarfeng.fileio.stack.bean.dto.PurgeFinishedResult;
import com.dwarfeng.fileio.stack.bean.entity.ExportTask;
import com.dwarfeng.fileio.stack.bean.entity.ExportTaskSetting;
import com.dwarfeng.fileio.stack.bean.entity.ImportTask;
import com.dwarfeng.fileio.stack.bean.entity.ImportTaskSetting;
import com.dwarfeng.fileio.stack.handler.PushHandler;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class PushHandlerImpl implements PushHandler {

    private final List<Pusher> pushers;

    @Value("${com.dwarfeng.fileio.pusher.type}")
    private String pusherType;

    private Pusher pusher;

    public PushHandlerImpl(List<Pusher> pushers) {
        this.pushers = Optional.ofNullable(pushers).orElse(Collections.emptyList());
    }

    @PostConstruct
    public void init() throws HandlerException {
        this.pusher = pushers.stream().filter(p -> p.supportType(pusherType)).findAny().orElseThrow(
                () -> new HandlerException("未知的 pusher 类型: " + pusherType)
        );
    }

    @Override
    public void exportReset() throws HandlerException {
        pusher.exportReset();
    }

    @Override
    public void importReset() throws HandlerException {
        pusher.importReset();
    }

    @Override
    public void exportTaskFinished(ExportTaskSetting exportTaskSetting, ExportTask exportTask) throws HandlerException {
        pusher.exportTaskFinished(exportTaskSetting, exportTask);
    }

    @Override
    public void exportTaskFailed(ExportTaskSetting exportTaskSetting, ExportTask exportTask) throws HandlerException {
        pusher.exportTaskFailed(exportTaskSetting, exportTask);
    }

    @Override
    public void exportTaskExpired(ExportTaskSetting exportTaskSetting, ExportTask exportTask) throws HandlerException {
        pusher.exportTaskExpired(exportTaskSetting, exportTask);
    }

    @Override
    public void exportTaskDead(ExportTaskSetting exportTaskSetting, ExportTask exportTask) throws HandlerException {
        pusher.exportTaskDead(exportTaskSetting, exportTask);
    }

    @Override
    public void importTaskFinished(ImportTaskSetting importTaskSetting, ImportTask importTask) throws HandlerException {
        pusher.importTaskFinished(importTaskSetting, importTask);
    }

    @Override
    public void importTaskFailed(ImportTaskSetting importTaskSetting, ImportTask importTask) throws HandlerException {
        pusher.importTaskFailed(importTaskSetting, importTask);
    }

    @Override
    public void importTaskExpired(ImportTaskSetting importTaskSetting, ImportTask importTask) throws HandlerException {
        pusher.importTaskExpired(importTaskSetting, importTask);
    }

    @Override
    public void importTaskDead(ImportTaskSetting importTaskSetting, ImportTask importTask) throws HandlerException {
        pusher.importTaskDead(importTaskSetting, importTask);
    }

    @Override
    public void purgeFinished(PurgeFinishedResult purgeFinishedResult) throws HandlerException {
        pusher.purgeFinished(purgeFinishedResult);
    }

    @Override
    public String toString() {
        return "PushHandlerImpl{" +
                "pusherType='" + pusherType + '\'' +
                ", pusher=" + pusher +
                '}';
    }
}
