package com.dwarfeng.fileio.impl.handler;

import com.dwarfeng.fileio.sdk.util.Constants;
import com.dwarfeng.fileio.stack.bean.dto.*;
import com.dwarfeng.fileio.stack.bean.entity.ExportTask;
import com.dwarfeng.fileio.stack.handler.ExportTaskOperateHandler;
import com.dwarfeng.fileio.stack.service.ExportTaskMaintainService;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.generation.KeyGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Component
public class ExportTaskOperateHandlerImpl implements ExportTaskOperateHandler {

    private static final Set<Integer> VALID_STATUS_SET_START;
    private static final Set<Integer> VALID_STATUS_SET_FINISH;
    private static final Set<Integer> VALID_STATUS_SET_FAIL;
    private static final Set<Integer> VALID_STATUS_SET_EXPIRE;
    private static final Set<Integer> VALID_STATUS_SET_UPDATE_DATA_COUNT;
    private static final Set<Integer> VALID_STATUS_SET_DEAD;
    private static final Set<Integer> VALID_STATUS_SET_BEAT;

    static {
        Set<Integer> VALID_STATUS_SET_START_DEJA_VU = new HashSet<>();
        VALID_STATUS_SET_START_DEJA_VU.add(Constants.EXPORT_TASK_STATUS_CREATED);
        VALID_STATUS_SET_START = Collections.unmodifiableSet(VALID_STATUS_SET_START_DEJA_VU);

        Set<Integer> VALID_STATUS_SET_FINISH_DEJA_VU = new HashSet<>();
        VALID_STATUS_SET_FINISH_DEJA_VU.add(Constants.EXPORT_TASK_STATUS_CREATED);
        VALID_STATUS_SET_FINISH_DEJA_VU.add(Constants.EXPORT_TASK_STATUS_PROCESSING);
        VALID_STATUS_SET_FINISH = Collections.unmodifiableSet(VALID_STATUS_SET_FINISH_DEJA_VU);

        Set<Integer> VALID_STATUS_SET_FAIL_DEJA_VU = new HashSet<>();
        VALID_STATUS_SET_FAIL_DEJA_VU.add(Constants.EXPORT_TASK_STATUS_CREATED);
        VALID_STATUS_SET_FAIL_DEJA_VU.add(Constants.EXPORT_TASK_STATUS_PROCESSING);
        VALID_STATUS_SET_FAIL = Collections.unmodifiableSet(VALID_STATUS_SET_FAIL_DEJA_VU);

        Set<Integer> VALID_STATUS_SET_EXPIRE_DEJA_VU = new HashSet<>();
        VALID_STATUS_SET_EXPIRE_DEJA_VU.add(Constants.EXPORT_TASK_STATUS_CREATED);
        VALID_STATUS_SET_EXPIRE_DEJA_VU.add(Constants.EXPORT_TASK_STATUS_PROCESSING);
        VALID_STATUS_SET_EXPIRE = Collections.unmodifiableSet(VALID_STATUS_SET_EXPIRE_DEJA_VU);

        Set<Integer> VALID_STATUS_SET_UPDATE_DATA_COUNT_DEJA_VU = new HashSet<>();
        VALID_STATUS_SET_UPDATE_DATA_COUNT_DEJA_VU.add(Constants.EXPORT_TASK_STATUS_PROCESSING);
        VALID_STATUS_SET_UPDATE_DATA_COUNT = Collections.unmodifiableSet(VALID_STATUS_SET_UPDATE_DATA_COUNT_DEJA_VU);

        Set<Integer> VALID_STATUS_SET_DEAD_DEJA_VU = new HashSet<>();
        VALID_STATUS_SET_DEAD_DEJA_VU.add(Constants.EXPORT_TASK_STATUS_PROCESSING);
        VALID_STATUS_SET_DEAD = Collections.unmodifiableSet(VALID_STATUS_SET_DEAD_DEJA_VU);

        Set<Integer> VALID_STATUS_SET_BEAT_DEJA_VU = new HashSet<>();
        VALID_STATUS_SET_BEAT_DEJA_VU.add(Constants.EXPORT_TASK_STATUS_PROCESSING);
        VALID_STATUS_SET_BEAT = Collections.unmodifiableSet(VALID_STATUS_SET_BEAT_DEJA_VU);
    }

    private final ExportTaskMaintainService exportTaskMaintainService;

    private final KeyGenerator<LongIdKey> keyGenerator;

    private final HandlerValidator handlerValidator;

    private final Lock lock = new ReentrantLock();

    @Value("${com.dwarfeng.fileio.task.export.expire_timeout}")
    private long expireTimeout;
    @Value("${com.dwarfeng.fileio.task.export.dead_timeout}")
    private long deadTimeout;

    public ExportTaskOperateHandlerImpl(
            ExportTaskMaintainService exportTaskMaintainService,
            KeyGenerator<LongIdKey> keyGenerator,
            HandlerValidator handlerValidator
    ) {
        this.exportTaskMaintainService = exportTaskMaintainService;
        this.keyGenerator = keyGenerator;
        this.handlerValidator = handlerValidator;
    }

    @Override
    public LongIdKey create(TaskCreateInfo createInfo) throws HandlerException {
        lock.lock();
        try {
            // 展开参数。
            LongIdKey settingKey = createInfo.getSettingKey();
            String message = createInfo.getMessage();
            StringIdKey userKey = createInfo.getUserKey();

            // 确认导出任务设置存在。
            handlerValidator.makeSureExportTaskSettingExists(settingKey);

            // 创建导出任务。
            LongIdKey taskKey = keyGenerator.generate();
            Date currentDate = new Date();

            ExportTask exportTask = new ExportTask(
                    taskKey,
                    settingKey,
                    userKey,
                    Constants.EXPORT_TASK_STATUS_CREATED,
                    currentDate,
                    null,
                    null,
                    null,
                    0,
                    message,
                    new Date(currentDate.getTime() + expireTimeout),
                    null
            );

            // 调用维护服务插入导出任务。
            exportTaskMaintainService.insert(exportTask);

            // 返回导出任务的主键。
            return taskKey;
        } catch (HandlerException e) {
            throw e;
        } catch (Exception e) {
            throw new HandlerException(e);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void start(TaskStartInfo startInfo) throws HandlerException {
        lock.lock();
        try {
            // 展开参数。
            LongIdKey taskKey = startInfo.getTaskKey();
            String message = startInfo.getMessage();

            // 确认导出任务存在。
            handlerValidator.makeSureExportTaskExists(taskKey);
            // 确认导出任务状态合法。
            handlerValidator.makeSureExportTaskStatusValid(taskKey, VALID_STATUS_SET_START);

            // 获取导出任务。
            ExportTask exportTask = exportTaskMaintainService.get(taskKey);

            // 更新导出任务字段。
            Date currentDate = new Date();
            exportTask.setStatus(Constants.EXPORT_TASK_STATUS_PROCESSING);
            exportTask.setStartDate(new Date());
            exportTask.setMessage(message);
            exportTask.setShouldDeadDate(new Date(currentDate.getTime() + deadTimeout));

            // 调用维护服务更新导出任务。
            exportTaskMaintainService.update(exportTask);
        } catch (HandlerException e) {
            throw e;
        } catch (Exception e) {
            throw new HandlerException(e);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void finish(TaskFinishInfo finishInfo) throws HandlerException {
        lock.lock();
        try {
            // 展开参数。
            LongIdKey taskKey = finishInfo.getTaskKey();
            String message = finishInfo.getMessage();

            // 确认导出任务存在。
            handlerValidator.makeSureExportTaskExists(taskKey);
            // 确认导出任务状态合法。
            handlerValidator.makeSureExportTaskStatusValid(taskKey, VALID_STATUS_SET_FINISH);

            // 获取导出任务。
            ExportTask exportTask = exportTaskMaintainService.get(taskKey);

            // 更新导出任务字段。
            Date currentDate = new Date();
            exportTask.setStatus(Constants.EXPORT_TASK_STATUS_FINISHED);
            if (Objects.isNull(exportTask.getStartDate())) {
                exportTask.setStartDate(currentDate);
            }
            exportTask.setEndDate(currentDate);
            exportTask.setDuration(exportTask.getEndDate().getTime() - exportTask.getStartDate().getTime());
            exportTask.setMessage(message);

            // 调用维护服务更新导出任务。
            exportTaskMaintainService.update(exportTask);
        } catch (HandlerException e) {
            throw e;
        } catch (Exception e) {
            throw new HandlerException(e);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void fail(TaskFailInfo failInfo) throws HandlerException {
        lock.lock();
        try {
            // 展开参数。
            LongIdKey taskKey = failInfo.getTaskKey();
            String message = failInfo.getMessage();

            // 确认导出任务存在。
            handlerValidator.makeSureExportTaskExists(taskKey);
            // 确认导出任务状态合法。
            handlerValidator.makeSureExportTaskStatusValid(taskKey, VALID_STATUS_SET_FAIL);

            // 获取导出任务。
            ExportTask exportTask = exportTaskMaintainService.get(taskKey);

            // 更新导出任务字段。
            Date currentDate = new Date();
            exportTask.setStatus(Constants.EXPORT_TASK_STATUS_FAILED);
            if (Objects.isNull(exportTask.getStartDate())) {
                exportTask.setStartDate(currentDate);
            }
            exportTask.setEndDate(currentDate);
            exportTask.setDuration(exportTask.getEndDate().getTime() - exportTask.getStartDate().getTime());
            exportTask.setMessage(message);

            // 调用维护服务更新导出任务。
            exportTaskMaintainService.update(exportTask);
        } catch (HandlerException e) {
            throw e;
        } catch (Exception e) {
            throw new HandlerException(e);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void expire(TaskExpireInfo expireInfo) throws HandlerException {
        lock.lock();
        try {
            // 展开参数。
            LongIdKey taskKey = expireInfo.getTaskKey();
            String message = expireInfo.getMessage();

            // 确认导出任务存在。
            handlerValidator.makeSureExportTaskExists(taskKey);
            // 确认导出任务状态合法。
            handlerValidator.makeSureExportTaskStatusValid(taskKey, VALID_STATUS_SET_EXPIRE);
            // 确认超时时间已到达。
            handlerValidator.makeSureExportTaskExpireDateReached(taskKey);

            // 获取导出任务。
            ExportTask exportTask = exportTaskMaintainService.get(taskKey);

            // 更新导出任务字段。
            Date currentDate = new Date();
            exportTask.setStatus(Constants.EXPORT_TASK_STATUS_EXPIRED);
            if (Objects.isNull(exportTask.getStartDate())) {
                exportTask.setStartDate(currentDate);
            }
            exportTask.setEndDate(currentDate);
            exportTask.setDuration(exportTask.getEndDate().getTime() - exportTask.getStartDate().getTime());
            exportTask.setMessage(message);

            // 调用维护服务更新导出任务。
            exportTaskMaintainService.update(exportTask);
        } catch (HandlerException e) {
            throw e;
        } catch (Exception e) {
            throw new HandlerException(e);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void updateDataCount(TaskDataCountUpdateInfo updateInfo) throws HandlerException {
        lock.lock();
        try {
            // 展开参数。
            LongIdKey taskKey = updateInfo.getTaskKey();
            int dataCount = updateInfo.getDataCount();

            // 确认导出任务存在。
            handlerValidator.makeSureExportTaskExists(taskKey);
            // 确认导出任务状态合法。
            handlerValidator.makeSureExportTaskStatusValid(taskKey, VALID_STATUS_SET_UPDATE_DATA_COUNT);

            // 获取导出任务。
            ExportTask exportTask = exportTaskMaintainService.get(taskKey);

            // 更新导出任务字段。
            exportTask.setDataCount(dataCount);

            // 调用维护服务更新导出任务。
            exportTaskMaintainService.update(exportTask);
        } catch (HandlerException e) {
            throw e;
        } catch (Exception e) {
            throw new HandlerException(e);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void increaseDataCount(TaskDataCountIncreaseInfo updateInfo) throws HandlerException {
        lock.lock();
        try {
            // 展开参数。
            LongIdKey taskKey = updateInfo.getTaskKey();
            int increment = updateInfo.getIncrement();

            // 确认导出任务存在。
            handlerValidator.makeSureExportTaskExists(taskKey);
            // 确认导出任务状态合法。
            handlerValidator.makeSureExportTaskStatusValid(taskKey, VALID_STATUS_SET_UPDATE_DATA_COUNT);

            // 获取导出任务。
            ExportTask exportTask = exportTaskMaintainService.get(taskKey);

            // 更新导出任务字段。
            exportTask.setDataCount(exportTask.getDataCount() + increment);

            // 调用维护服务更新导出任务。
            exportTaskMaintainService.update(exportTask);
        } catch (HandlerException e) {
            throw e;
        } catch (Exception e) {
            throw new HandlerException(e);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void dead(TaskDeadInfo deadInfo) throws HandlerException {
        lock.lock();
        try {
            // 展开参数。
            LongIdKey taskKey = deadInfo.getTaskKey();
            String message = deadInfo.getMessage();

            // 确认导出任务存在。
            handlerValidator.makeSureExportTaskExists(taskKey);
            // 确认导出任务状态合法。
            handlerValidator.makeSureExportTaskStatusValid(taskKey, VALID_STATUS_SET_DEAD);
            // 确认超时时间已到达。
            handlerValidator.makeSureExportTaskDeadDateReached(taskKey);

            // 获取导出任务。
            ExportTask exportTask = exportTaskMaintainService.get(taskKey);

            // 更新导出任务字段。
            Date currentDate = new Date();
            exportTask.setStatus(Constants.EXPORT_TASK_STATUS_DEAD);
            if (Objects.isNull(exportTask.getStartDate())) {
                exportTask.setStartDate(currentDate);
            }
            exportTask.setEndDate(currentDate);
            exportTask.setDuration(exportTask.getEndDate().getTime() - exportTask.getStartDate().getTime());
            exportTask.setMessage(message);

            // 调用维护服务更新导出任务。
            exportTaskMaintainService.update(exportTask);
        } catch (HandlerException e) {
            throw e;
        } catch (Exception e) {
            throw new HandlerException(e);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void beat(TaskBeatInfo beatInfo) throws HandlerException {
        lock.lock();
        try {
            // 展开参数。
            LongIdKey taskKey = beatInfo.getTaskKey();

            // 确认导出任务存在。
            handlerValidator.makeSureExportTaskExists(taskKey);
            // 确认导出任务状态合法。
            handlerValidator.makeSureExportTaskStatusValid(taskKey, VALID_STATUS_SET_BEAT);

            // 获取导出任务。
            ExportTask exportTask = exportTaskMaintainService.get(taskKey);

            // 更新导出任务字段。
            Date currentDate = new Date();
            exportTask.setShouldDeadDate(new Date(currentDate.getTime() + deadTimeout));

            // 调用维护服务更新导出任务。
            exportTaskMaintainService.update(exportTask);
        } catch (HandlerException e) {
            throw e;
        } catch (Exception e) {
            throw new HandlerException(e);
        } finally {
            lock.unlock();
        }
    }
}
