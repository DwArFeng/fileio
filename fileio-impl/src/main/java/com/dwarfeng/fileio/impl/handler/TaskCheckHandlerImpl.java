package com.dwarfeng.fileio.impl.handler;

import com.dwarfeng.fileio.stack.bean.dto.TaskExpireInfo;
import com.dwarfeng.fileio.stack.bean.entity.ExportTask;
import com.dwarfeng.fileio.stack.bean.entity.ExportTaskSetting;
import com.dwarfeng.fileio.stack.bean.entity.ImportTask;
import com.dwarfeng.fileio.stack.bean.entity.ImportTaskSetting;
import com.dwarfeng.fileio.stack.handler.ExportTaskOperateHandler;
import com.dwarfeng.fileio.stack.handler.ImportTaskOperateHandler;
import com.dwarfeng.fileio.stack.handler.PushHandler;
import com.dwarfeng.fileio.stack.handler.TaskCheckHandler;
import com.dwarfeng.fileio.stack.service.ExportTaskMaintainService;
import com.dwarfeng.fileio.stack.service.ExportTaskSettingMaintainService;
import com.dwarfeng.fileio.stack.service.ImportTaskMaintainService;
import com.dwarfeng.fileio.stack.service.ImportTaskSettingMaintainService;
import com.dwarfeng.subgrade.impl.handler.CuratorDistributedLockHandler;
import com.dwarfeng.subgrade.impl.handler.Worker;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.apache.curator.framework.CuratorFramework;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.Future;

@Component
public class TaskCheckHandlerImpl implements TaskCheckHandler {

    private final CuratorDistributedLockHandler handler;

    public TaskCheckHandlerImpl(
            CuratorFramework curatorFramework,
            @Value("${com.dwarfeng.fileio.curator.latch_path.expire_check.leader_latch}") String leaserLatchPath,
            TaskCheckWorker taskCheckWorker
    ) {
        handler = new CuratorDistributedLockHandler(curatorFramework, leaserLatchPath, taskCheckWorker);
    }

    @BehaviorAnalyse
    @Override
    public boolean isOnline() {
        return handler.isOnline();
    }

    @BehaviorAnalyse
    @Override
    public void online() throws HandlerException {
        handler.online();
    }

    @BehaviorAnalyse
    @Override
    public void offline() throws HandlerException {
        handler.offline();
    }

    @BehaviorAnalyse
    @Override
    public boolean isStarted() {
        return handler.isStarted();
    }

    @BehaviorAnalyse
    @Override
    public void start() throws HandlerException {
        handler.start();
    }

    @BehaviorAnalyse
    @Override
    public void stop() throws HandlerException {
        handler.stop();
    }

    @BehaviorAnalyse
    @Override
    public boolean isLockHolding() {
        return handler.isLockHolding();
    }

    @BehaviorAnalyse
    @Override
    public boolean isWorking() {
        return handler.isWorking();
    }

    @Component
    public static class TaskCheckWorker implements Worker {

        private static final Logger LOGGER = LoggerFactory.getLogger(TaskCheckWorker.class);

        private final ExportTaskSettingMaintainService exportTaskSettingMaintainService;
        private final ExportTaskMaintainService exportTaskMaintainService;
        private final ImportTaskSettingMaintainService importTaskSettingMaintainService;
        private final ImportTaskMaintainService importTaskMaintainService;

        private final ExportTaskOperateHandler exportTaskOperateHandler;
        private final ImportTaskOperateHandler importTaskOperateHandler;

        private final PushHandler pushHandler;

        private final ThreadPoolTaskScheduler scheduler;

        @Value("${com.dwarfeng.fileio.task.check.expire_check.cron}")
        private String expireCheckCron;
        @Value("${com.dwarfeng.fileio.task.check.dead_check.cron}")
        private String deadCheckCron;

        private Future<?> expireCheckFuture;
        private Future<?> deadCheckFuture;

        public TaskCheckWorker(
                ExportTaskSettingMaintainService exportTaskSettingMaintainService,
                ExportTaskMaintainService exportTaskMaintainService,
                ImportTaskSettingMaintainService importTaskSettingMaintainService,
                ImportTaskMaintainService importTaskMaintainService,
                ExportTaskOperateHandler exportTaskOperateHandler,
                ImportTaskOperateHandler importTaskOperateHandler,
                PushHandler pushHandler,
                ThreadPoolTaskScheduler scheduler
        ) {
            this.exportTaskSettingMaintainService = exportTaskSettingMaintainService;
            this.exportTaskMaintainService = exportTaskMaintainService;
            this.importTaskSettingMaintainService = importTaskSettingMaintainService;
            this.importTaskMaintainService = importTaskMaintainService;
            this.exportTaskOperateHandler = exportTaskOperateHandler;
            this.importTaskOperateHandler = importTaskOperateHandler;
            this.pushHandler = pushHandler;
            this.scheduler = scheduler;
        }

        @Override
        public void work() {
            if (Objects.isNull(expireCheckFuture)) {
                expireCheckFuture = scheduler.schedule(this::expireCheck, new CronTrigger(expireCheckCron));
            }
            if (Objects.isNull(deadCheckFuture)) {
                deadCheckFuture = scheduler.schedule(this::deadCheck, new CronTrigger(deadCheckCron));
            }
        }

        @Override
        public void rest() {
            if (Objects.nonNull(expireCheckFuture)) {
                expireCheckFuture.cancel(true);
                expireCheckFuture = null;
            }
            if (Objects.nonNull(deadCheckFuture)) {
                deadCheckFuture.cancel(true);
                deadCheckFuture = null;
            }
        }

        public void expireCheck() {
            try {
                LOGGER.info("检查过期任务...");

                // 获取所有过期的导出任务。
                List<ExportTask> exportTasksToExpire = exportTaskMaintainService.lookupAsList(
                        ExportTaskMaintainService.SHOULD_EXPIRE, new Object[0]
                );
                // 调用操作处理器，将过期的导出任务设置为过期。
                for (ExportTask task : exportTasksToExpire) {
                    // 查询任务设置。
                    LongIdKey taskSettingKey = task.getSettingKey();
                    ExportTaskSetting taskSetting = null;
                    if (Objects.nonNull(taskSettingKey)) {
                        taskSetting = exportTaskSettingMaintainService.getIfExists(taskSettingKey);
                    }
                    // 调用操作处理器过期任务。
                    exportTaskOperateHandler.expire(new TaskExpireInfo(task.getKey(), "由过期检查处理器设置为过期"));
                    // 推送事件。
                    try {
                        pushHandler.exportTaskExpired(taskSetting, task);
                    } catch (Exception e) {
                        String message = "推送导出任务过期事件时失败, 事件将不会被推送, 任务设置: " + taskSetting +
                                ", 任务: " + task + ", 异常信息如下: ";
                        LOGGER.warn(message, e);
                    }
                }

                // 获取所有过期的导入任务。
                List<ImportTask> importTasksToExpire = importTaskMaintainService.lookupAsList(
                        ImportTaskMaintainService.SHOULD_EXPIRE, new Object[0]
                );
                // 调用操作处理器，将过期的导入任务设置为过期。
                for (ImportTask task : importTasksToExpire) {
                    // 查询任务设置。
                    LongIdKey taskSettingKey = task.getSettingKey();
                    ImportTaskSetting taskSetting = null;
                    if (Objects.nonNull(taskSettingKey)) {
                        taskSetting = importTaskSettingMaintainService.getIfExists(taskSettingKey);
                    }
                    // 调用操作处理器过期任务。
                    importTaskOperateHandler.expire(new TaskExpireInfo(task.getKey(), "由过期检查处理器设置为过期"));
                    // 推送事件。
                    try {
                        pushHandler.importTaskExpired(taskSetting, task);
                    } catch (Exception e) {
                        String message = "推送导入任务过期事件时失败, 事件将不会被推送, 任务设置: " + taskSetting +
                                ", 任务: " + task + ", 异常信息如下: ";
                        LOGGER.warn(message, e);
                    }
                }
            } catch (Exception e) {
                LOGGER.warn("检查过期任务时发生异常，异常信息如下", e);
            }
        }

        public void deadCheck() {
            try {
                LOGGER.info("检查死亡任务...");

                // 获取所有死亡的导出任务。
                List<ExportTask> exportTasksToExpire = exportTaskMaintainService.lookupAsList(
                        ExportTaskMaintainService.SHOULD_DEAD, new Object[0]
                );
                // 调用操作处理器，将死亡的导出任务设置为死亡。
                for (ExportTask task : exportTasksToExpire) {
                    // 查询任务设置。
                    LongIdKey taskSettingKey = task.getSettingKey();
                    ExportTaskSetting taskSetting = null;
                    if (Objects.nonNull(taskSettingKey)) {
                        taskSetting = exportTaskSettingMaintainService.getIfExists(taskSettingKey);
                    }
                    // 调用操作处理器过期任务。
                    exportTaskOperateHandler.expire(new TaskExpireInfo(task.getKey(), "由死亡检查处理器设置为死亡"));
                    // 推送事件。
                    try {
                        pushHandler.exportTaskDead(taskSetting, task);
                    } catch (Exception e) {
                        String message = "推送导出任务死亡事件时失败, 事件将不会被推送, 任务设置: " + taskSetting +
                                ", 任务: " + task + ", 异常信息如下: ";
                        LOGGER.warn(message, e);
                    }
                }

                // 获取所有死亡的导入任务。
                List<ImportTask> importTasksToExpire = importTaskMaintainService.lookupAsList(
                        ImportTaskMaintainService.SHOULD_DEAD, new Object[0]
                );
                // 调用操作处理器，将死亡的导入任务设置为死亡。
                for (ImportTask task : importTasksToExpire) {
                    // 查询任务设置。
                    LongIdKey taskSettingKey = task.getSettingKey();
                    ImportTaskSetting taskSetting = null;
                    if (Objects.nonNull(taskSettingKey)) {
                        taskSetting = importTaskSettingMaintainService.getIfExists(taskSettingKey);
                    }
                    // 调用操作处理器过期任务。
                    importTaskOperateHandler.expire(new TaskExpireInfo(task.getKey(), "由死亡检查处理器设置为死亡"));
                    // 推送事件。
                    try {
                        pushHandler.importTaskDead(taskSetting, task);
                    } catch (Exception e) {
                        String message = "推送导入任务死亡事件时失败, 事件将不会被推送, 任务设置: " + taskSetting +
                                ", 任务: " + task + ", 异常信息如下: ";
                        LOGGER.warn(message, e);
                    }
                }
            } catch (Exception e) {
                LOGGER.warn("检查死亡任务时发生异常，异常信息如下", e);
            }
        }
    }
}
