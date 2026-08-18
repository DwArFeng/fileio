package com.dwarfeng.fileio.node.all.he.launcher;

import com.dwarfeng.fileio.node.all.he.handler.LauncherSettingHandler;
import com.dwarfeng.fileio.stack.service.PurgeQosService;
import com.dwarfeng.fileio.stack.service.ResetQosService;
import com.dwarfeng.fileio.stack.service.SupportQosService;
import com.dwarfeng.fileio.stack.service.TaskCheckQosService;
import com.dwarfeng.springterminator.sdk.util.ApplicationUtil;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.Date;

/**
 * 程序启动器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class Launcher {

    private static final Logger LOGGER = LoggerFactory.getLogger(Launcher.class);

    public static void main(String[] args) {
        ApplicationUtil.launch(new String[]{
                "classpath:spring/application-context*.xml",
                "file:opt/opt*.xml",
                "file:optext/opt*.xml"
        }, ctx -> {
            // 根据启动器设置处理器的设置，选择性重置导入器。
            mayResetImporter(ctx);

            // 根据启动器设置处理器的设置，选择性重置导出器。
            mayResetExporter(ctx);

            // 根据启动器设置处理器的设置，选择性重置读取器。
            mayResetReader(ctx);

            // 根据启动器设置处理器的设置，选择性重置写入器。
            mayResetWriter(ctx);

            // 根据启动器设置处理器的设置，选择性上线任务检查服务。
            mayOnlineTaskCheck(ctx);
            // 根据启动器设置处理器的设置，选择性启动任务检查服务。
            mayEnableTaskCheck(ctx);

            // 根据启动器设置处理器的设置，选择性启动重置服务。
            mayStartReset(ctx);

            // 根据启动器设置处理器的设置，选择性上线清除服务。
            mayOnlinePurge(ctx);
            // 根据启动器设置处理器的设置，选择性启动清除服务。
            mayEnablePurge(ctx);
        });
    }

    private static void mayResetImporter(ApplicationContext ctx) {
        // 获取启动器设置处理器，用于获取启动器设置，并按照设置选择性执行功能。
        LauncherSettingHandler launcherSettingHandler = ctx.getBean(LauncherSettingHandler.class);

        // 判断是否重置导入器支持，并按条件执行重置操作。
        if (launcherSettingHandler.isResetImporterSupport()) {
            LOGGER.info("重置导入器支持...");
            SupportQosService supportQosService = ctx.getBean(SupportQosService.class);
            try {
                supportQosService.resetImporter();
            } catch (ServiceException e) {
                LOGGER.warn("导入器支持重置失败，异常信息如下", e);
            }
        }
    }

    private static void mayResetExporter(ApplicationContext ctx) {
        // 获取启动器设置处理器，用于获取启动器设置，并按照设置选择性执行功能。
        LauncherSettingHandler launcherSettingHandler = ctx.getBean(LauncherSettingHandler.class);

        // 判断是否重置导出器支持，并按条件执行重置操作。
        if (launcherSettingHandler.isResetExporterSupport()) {
            LOGGER.info("重置导出器支持...");
            SupportQosService supportQosService = ctx.getBean(SupportQosService.class);
            try {
                supportQosService.resetExporter();
            } catch (ServiceException e) {
                LOGGER.warn("导出器支持重置失败，异常信息如下", e);
            }
        }
    }

    private static void mayResetReader(ApplicationContext ctx) {
        // 获取启动器设置处理器，用于获取启动器设置，并按照设置选择性执行功能。
        LauncherSettingHandler launcherSettingHandler = ctx.getBean(LauncherSettingHandler.class);

        // 判断是否重置读取器支持，并按条件执行重置操作。
        if (launcherSettingHandler.isResetReaderSupport()) {
            LOGGER.info("重置读取器支持...");
            SupportQosService supportQosService = ctx.getBean(SupportQosService.class);
            try {
                supportQosService.resetReader();
            } catch (ServiceException e) {
                LOGGER.warn("读取器支持重置失败，异常信息如下", e);
            }
        }
    }

    private static void mayResetWriter(ApplicationContext ctx) {
        // 获取启动器设置处理器，用于获取启动器设置，并按照设置选择性执行功能。
        LauncherSettingHandler launcherSettingHandler = ctx.getBean(LauncherSettingHandler.class);

        // 判断是否重置写入器支持，并按条件执行重置操作。
        if (launcherSettingHandler.isResetWriterSupport()) {
            LOGGER.info("重置写入器支持...");
            SupportQosService supportQosService = ctx.getBean(SupportQosService.class);
            try {
                supportQosService.resetWriter();
            } catch (ServiceException e) {
                LOGGER.warn("写入器支持重置失败，异常信息如下", e);
            }
        }
    }

    private static void mayOnlineTaskCheck(ApplicationContext ctx) {
        // 获取启动器设置处理器，用于获取启动器设置，并按照设置选择性执行功能。
        LauncherSettingHandler launcherSettingHandler = ctx.getBean(LauncherSettingHandler.class);

        // 获取程序中的 ThreadPoolTaskScheduler，用于处理计划任务。
        ThreadPoolTaskScheduler scheduler = ctx.getBean(ThreadPoolTaskScheduler.class);

        // 获取任务检查 QOS 服务。
        TaskCheckQosService taskCheckQosService = ctx.getBean(TaskCheckQosService.class);

        // 判断任务检查处理器是否上线任务检查服务，并按条件执行不同的操作。
        long onlineTaskCheckDelay = launcherSettingHandler.getOnlineTaskCheckDelay();
        if (onlineTaskCheckDelay == 0) {
            LOGGER.info("立即上线任务检查服务...");
            try {
                taskCheckQosService.online();
            } catch (ServiceException e) {
                LOGGER.error("无法上线任务检查服务，异常原因如下", e);
            }
        } else if (onlineTaskCheckDelay > 0) {
            LOGGER.info("{} 毫秒后上线任务检查服务...", onlineTaskCheckDelay);
            scheduler.schedule(
                    () -> {
                        LOGGER.info("上线任务检查服务...");
                        try {
                            taskCheckQosService.online();
                        } catch (ServiceException e) {
                            LOGGER.error("无法上线任务检查服务，异常原因如下", e);
                        }
                    },
                    new Date(System.currentTimeMillis() + onlineTaskCheckDelay)
            );
        }
    }

    private static void mayEnableTaskCheck(ApplicationContext ctx) {
        // 获取启动器设置处理器，用于获取启动器设置，并按照设置选择性执行功能。
        LauncherSettingHandler launcherSettingHandler = ctx.getBean(LauncherSettingHandler.class);

        // 获取程序中的 ThreadPoolTaskScheduler，用于处理计划任务。
        ThreadPoolTaskScheduler scheduler = ctx.getBean(ThreadPoolTaskScheduler.class);

        // 获取任务检查 QOS 服务。
        TaskCheckQosService taskCheckQosService = ctx.getBean(TaskCheckQosService.class);

        // 判断任务检查处理器是否启动任务检查服务，并按条件执行不同的操作。
        long enableTaskCheckDelay = launcherSettingHandler.getEnableTaskCheckDelay();
        if (enableTaskCheckDelay == 0) {
            LOGGER.info("立即启动任务检查服务...");
            try {
                taskCheckQosService.start();
            } catch (ServiceException e) {
                LOGGER.error("无法启动任务检查服务，异常原因如下", e);
            }
        } else if (enableTaskCheckDelay > 0) {
            LOGGER.info("{} 毫秒后启动任务检查服务...", enableTaskCheckDelay);
            scheduler.schedule(
                    () -> {
                        LOGGER.info("启动任务检查服务...");
                        try {
                            taskCheckQosService.start();
                        } catch (ServiceException e) {
                            LOGGER.error("无法启动任务检查服务，异常原因如下", e);
                        }
                    },
                    new Date(System.currentTimeMillis() + enableTaskCheckDelay)
            );
        }
    }

    private static void mayStartReset(ApplicationContext ctx) {
        // 获取启动器设置处理器，用于获取启动器设置，并按照设置选择性执行功能。
        LauncherSettingHandler launcherSettingHandler = ctx.getBean(LauncherSettingHandler.class);

        // 获取程序中的 ThreadPoolTaskScheduler，用于处理计划任务。
        ThreadPoolTaskScheduler scheduler = ctx.getBean(ThreadPoolTaskScheduler.class);

        // 获取重置 QOS 服务。
        ResetQosService resetQosService = ctx.getBean(ResetQosService.class);

        // 判断重置处理器是否启动重置服务，并按条件执行不同的操作。
        long startResetDelay = launcherSettingHandler.getStartResetDelay();
        if (startResetDelay == 0) {
            LOGGER.info("立即启动重置服务...");
            try {
                resetQosService.start();
            } catch (ServiceException e) {
                LOGGER.error("无法启动重置服务，异常原因如下", e);
            }
        } else if (startResetDelay > 0) {
            LOGGER.info("{} 毫秒后启动重置服务...", startResetDelay);
            scheduler.schedule(
                    () -> {
                        LOGGER.info("启动重置服务...");
                        try {
                            resetQosService.start();
                        } catch (ServiceException e) {
                            LOGGER.error("无法启动重置服务，异常原因如下", e);
                        }
                    },
                    new Date(System.currentTimeMillis() + startResetDelay)
            );
        }
    }

    private static void mayOnlinePurge(ApplicationContext ctx) {
        // 获取启动器设置处理器，用于获取启动器设置，并按照设置选择性执行功能。
        LauncherSettingHandler launcherSettingHandler = ctx.getBean(LauncherSettingHandler.class);

        // 获取程序中的 ThreadPoolTaskScheduler，用于处理计划任务。
        ThreadPoolTaskScheduler scheduler = ctx.getBean(ThreadPoolTaskScheduler.class);

        // 获取清除 QOS 服务。
        PurgeQosService purgeQosService = ctx.getBean(PurgeQosService.class);

        // 判断清除处理器是否上线清除服务，并按条件执行不同的操作。
        long onlinePurgeDelay = launcherSettingHandler.getOnlinePurgeDelay();
        if (onlinePurgeDelay == 0) {
            LOGGER.info("立即上线清除服务...");
            try {
                purgeQosService.online();
            } catch (ServiceException e) {
                LOGGER.error("无法上线清除服务，异常原因如下", e);
            }
        } else if (onlinePurgeDelay > 0) {
            LOGGER.info("{} 毫秒后上线清除服务...", onlinePurgeDelay);
            scheduler.schedule(
                    () -> {
                        LOGGER.info("上线清除服务...");
                        try {
                            purgeQosService.online();
                        } catch (ServiceException e) {
                            LOGGER.error("无法上线清除服务，异常原因如下", e);
                        }
                    },
                    new Date(System.currentTimeMillis() + onlinePurgeDelay)
            );
        }
    }

    private static void mayEnablePurge(ApplicationContext ctx) {
        // 获取启动器设置处理器，用于获取启动器设置，并按照设置选择性执行功能。
        LauncherSettingHandler launcherSettingHandler = ctx.getBean(LauncherSettingHandler.class);

        // 获取程序中的 ThreadPoolTaskScheduler，用于处理计划任务。
        ThreadPoolTaskScheduler scheduler = ctx.getBean(ThreadPoolTaskScheduler.class);

        // 获取清除 QOS 服务。
        PurgeQosService purgeQosService = ctx.getBean(PurgeQosService.class);

        // 判断清除处理器是否启动清除服务，并按条件执行不同的操作。
        long enablePurgeDelay = launcherSettingHandler.getEnablePurgeDelay();
        if (enablePurgeDelay == 0) {
            LOGGER.info("立即启动清除服务...");
            try {
                purgeQosService.start();
            } catch (ServiceException e) {
                LOGGER.error("无法启动清除服务，异常原因如下", e);
            }
        } else if (enablePurgeDelay > 0) {
            LOGGER.info("{} 毫秒后启动清除服务...", enablePurgeDelay);
            scheduler.schedule(
                    () -> {
                        LOGGER.info("启动清除服务...");
                        try {
                            purgeQosService.start();
                        } catch (ServiceException e) {
                            LOGGER.error("无法启动清除服务，异常原因如下", e);
                        }
                    },
                    new Date(System.currentTimeMillis() + enablePurgeDelay)
            );
        }
    }
}
