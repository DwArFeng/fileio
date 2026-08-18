package com.dwarfeng.fileio.impl.handler.resetter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.util.concurrent.ScheduledFuture;

/**
 * 使用 CRON 表达式定时重置的重置器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component
public class CronResetter extends com.dwarfeng.fileio.sdk.handler.resetter.AbstractResetter {

    private static final Logger LOGGER = LoggerFactory.getLogger(CronResetter.class);

    private final ThreadPoolTaskScheduler scheduler;

    @Value("${com.dwarfeng.fileio.resetter.cron.cron}")
    private String cron;

    private final ResetTask resetTask = new ResetTask();

    private ScheduledFuture<?> resetTaskFuture;

    public CronResetter(ThreadPoolTaskScheduler scheduler) {
        this.scheduler = scheduler;
    }

    @Override
    protected void doStart() {
        resetTaskFuture = scheduler.schedule(resetTask, new CronTrigger(cron));
    }

    @Override
    protected void doStop() {
        resetTaskFuture.cancel(true);
    }

    @Override
    public String toString() {
        return "CronResetter{" +
                "cron='" + cron + '\'' +
                '}';
    }

    private class ResetTask implements Runnable {

        @Override
        public void run() {
            try {
                LOGGER.info("计划时间已到, 重置导入及导出功能...");
                context.resetExport();
                context.resetImport();
            } catch (Exception e) {
                String message = "重置器 " + CronResetter.this +
                        " 执行重置调度时发生异常, 导入及导出功能将不会重置, 异常信息如下: ";
                LOGGER.warn(message, e);
            }
        }
    }
}
