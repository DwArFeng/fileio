package com.dwarfeng.fileio.stack.handler;

import com.dwarfeng.fileio.stack.bean.dto.*;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.handler.Handler;

/**
 * 导入任务操作处理器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface ImportTaskOperateHandler extends Handler {

    /**
     * 创建导入任务。
     *
     * @param createInfo 导入任务的创建信息。
     * @return 生成的导入任务的主键。
     * @throws HandlerException 处理器异常。
     */
    LongIdKey create(TaskCreateInfo createInfo) throws HandlerException;

    /**
     * 启动导入任务。
     *
     * @param startInfo 导入任务的启动信息。
     * @throws HandlerException 处理器异常。
     */
    void start(TaskStartInfo startInfo) throws HandlerException;

    /**
     * 完成导入任务。
     *
     * @param finishInfo 导入任务的完成信息。
     * @throws HandlerException 处理器异常。
     */
    void finish(TaskFinishInfo finishInfo) throws HandlerException;

    /**
     * 失败导入任务。
     *
     * @param failInfo 导入任务的失败信息。
     * @throws HandlerException 处理器异常。
     */
    void fail(TaskFailInfo failInfo) throws HandlerException;

    /**
     * 过期导入任务。
     *
     * @param expireInfo 导入任务的过期信息。
     * @throws HandlerException 处理器异常。
     */
    void expire(TaskExpireInfo expireInfo) throws HandlerException;

    /**
     * 更新导入任务的数据量。
     *
     * @param updateInfo 导入任务的数据量更新信息。
     * @throws HandlerException 处理器异常。
     */
    void updateDataCount(TaskDataCountUpdateInfo updateInfo) throws HandlerException;

    /**
     * 增加导入任务的数据量。
     *
     * @param updateInfo 导入任务的数据量增加信息。
     * @throws HandlerException 处理器异常。
     * @since 1.1.0
     */
    void increaseDataCount(TaskDataCountIncreaseInfo updateInfo) throws HandlerException;

    /**
     * 死亡导入任务。
     *
     * @param deadInfo 导入任务的死亡信息。
     * @throws HandlerException 处理器异常。
     */
    void dead(TaskDeadInfo deadInfo) throws HandlerException;

    /**
     * 心跳导入任务。
     *
     * @param beatInfo 导入任务的心跳信息。
     * @throws HandlerException 处理器异常。
     */
    void beat(TaskBeatInfo beatInfo) throws HandlerException;
}
