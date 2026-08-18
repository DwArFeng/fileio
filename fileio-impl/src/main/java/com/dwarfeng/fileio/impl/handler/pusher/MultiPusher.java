package com.dwarfeng.fileio.impl.handler.pusher;

import com.dwarfeng.fileio.sdk.handler.Pusher;
import com.dwarfeng.fileio.sdk.handler.pusher.AbstractPusher;
import com.dwarfeng.fileio.stack.bean.dto.PurgeFinishedResult;
import com.dwarfeng.fileio.stack.bean.entity.ExportTask;
import com.dwarfeng.fileio.stack.bean.entity.ExportTaskSetting;
import com.dwarfeng.fileio.stack.bean.entity.ImportTask;
import com.dwarfeng.fileio.stack.bean.entity.ImportTaskSetting;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * 同时将消息推送给所有代理的多重推送器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component
public class MultiPusher extends AbstractPusher {

    public static final String PUSHER_TYPE = "multi";
    @SuppressWarnings("unused")
    private static final Logger LOGGER = LoggerFactory.getLogger(MultiPusher.class);

    private final List<Pusher> pushers;

    @Value("${com.dwarfeng.fileio.pusher.multi.delegate_types}")
    private String delegateTypes;

    private final List<Pusher> delegates = new ArrayList<>();

    public MultiPusher(List<Pusher> pushers) {
        super(PUSHER_TYPE);
        this.pushers = Optional.ofNullable(pushers).orElse(Collections.emptyList());
    }

    @PostConstruct
    public void init() throws HandlerException {
        StringTokenizer st = new StringTokenizer(delegateTypes, ",");
        while (st.hasMoreTokens()) {
            String delegateType = st.nextToken();
            delegates.add(pushers.stream().filter(p -> p.supportType(delegateType)).findAny()
                    .orElseThrow(() -> new HandlerException("未知的 pusher 类型: " + delegateType)));
        }
    }

    @Override
    public void exportReset() {
        for (Pusher delegate : delegates) {
            try {
                delegate.exportReset();
            } catch (Exception e) {
                LOGGER.warn("代理推送器推送消息失败，异常信息如下: ", e);
            }
        }
    }

    @Override
    public void importReset() {
        for (Pusher delegate : delegates) {
            try {
                delegate.importReset();
            } catch (Exception e) {
                LOGGER.warn("代理推送器推送消息失败，异常信息如下: ", e);
            }
        }
    }

    @Override
    public void exportTaskFinished(ExportTaskSetting exportTaskSetting, ExportTask exportTask) {
        for (Pusher delegate : delegates) {
            try {
                delegate.exportTaskFinished(exportTaskSetting, exportTask);
            } catch (Exception e) {
                LOGGER.warn("代理推送器推送消息失败，异常信息如下: ", e);
            }
        }
    }

    @Override
    public void exportTaskFailed(ExportTaskSetting exportTaskSetting, ExportTask exportTask) {
        for (Pusher delegate : delegates) {
            try {
                delegate.exportTaskFailed(exportTaskSetting, exportTask);
            } catch (Exception e) {
                LOGGER.warn("代理推送器推送消息失败，异常信息如下: ", e);
            }
        }
    }

    @Override
    public void exportTaskExpired(ExportTaskSetting exportTaskSetting, ExportTask exportTask) {
        for (Pusher delegate : delegates) {
            try {
                delegate.exportTaskExpired(exportTaskSetting, exportTask);
            } catch (Exception e) {
                LOGGER.warn("代理推送器推送消息失败，异常信息如下: ", e);
            }
        }
    }

    @Override
    public void exportTaskDead(ExportTaskSetting exportTaskSetting, ExportTask exportTask) {
        for (Pusher delegate : delegates) {
            try {
                delegate.exportTaskDead(exportTaskSetting, exportTask);
            } catch (Exception e) {
                LOGGER.warn("代理推送器推送消息失败，异常信息如下: ", e);
            }
        }
    }

    @Override
    public void importTaskFinished(ImportTaskSetting importTaskSetting, ImportTask importTask) {
        for (Pusher delegate : delegates) {
            try {
                delegate.importTaskFinished(importTaskSetting, importTask);
            } catch (Exception e) {
                LOGGER.warn("代理推送器推送消息失败，异常信息如下: ", e);
            }
        }
    }

    @Override
    public void importTaskFailed(ImportTaskSetting importTaskSetting, ImportTask importTask) {
        for (Pusher delegate : delegates) {
            try {
                delegate.importTaskFailed(importTaskSetting, importTask);
            } catch (Exception e) {
                LOGGER.warn("代理推送器推送消息失败，异常信息如下: ", e);
            }
        }
    }

    @Override
    public void importTaskExpired(ImportTaskSetting importTaskSetting, ImportTask importTask) {
        for (Pusher delegate : delegates) {
            try {
                delegate.importTaskExpired(importTaskSetting, importTask);
            } catch (Exception e) {
                LOGGER.warn("代理推送器推送消息失败，异常信息如下: ", e);
            }
        }
    }

    @Override
    public void importTaskDead(ImportTaskSetting importTaskSetting, ImportTask importTask) {
        for (Pusher delegate : delegates) {
            try {
                delegate.importTaskDead(importTaskSetting, importTask);
            } catch (Exception e) {
                LOGGER.warn("代理推送器推送消息失败，异常信息如下: ", e);
            }
        }
    }

    @Override
    public void purgeFinished(PurgeFinishedResult purgeFinishedResult) {
        for (Pusher delegate : delegates) {
            try {
                delegate.purgeFinished(purgeFinishedResult);
            } catch (Exception e) {
                LOGGER.warn("代理推送器推送消息失败，异常信息如下: ", e);
            }
        }
    }

    @Override
    public String toString() {
        return "MultiPusher{" +
                "delegateTypes='" + delegateTypes + '\'' +
                ", pusherType='" + pusherType + '\'' +
                '}';
    }
}
