package com.dwarfeng.fileio.stack.handler;

import com.dwarfeng.fileio.stack.bean.dto.PurgeFinishedResult;
import com.dwarfeng.fileio.stack.bean.entity.ExportTask;
import com.dwarfeng.fileio.stack.bean.entity.ExportTaskSetting;
import com.dwarfeng.fileio.stack.bean.entity.ImportTask;
import com.dwarfeng.fileio.stack.bean.entity.ImportTaskSetting;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.handler.Handler;

/**
 * 推送器处理器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface PushHandler extends Handler {

    /**
     * 导出重置时执行的推送操作。
     *
     * @throws HandlerException 处理器异常。
     */
    void exportReset() throws HandlerException;

    /**
     * 导入重置时执行的推送操作。
     *
     * @throws HandlerException 处理器异常。
     */
    void importReset() throws HandlerException;

    /**
     * 导出任务完成时执行的推送操作。
     *
     * @param exportTaskSetting 相关的导出任务设置。
     * @param exportTask        相关的导出任务。
     * @throws HandlerException 处理器异常。
     */
    void exportTaskFinished(ExportTaskSetting exportTaskSetting, ExportTask exportTask) throws HandlerException;

    /**
     * 导出任务失败时执行的推送操作。
     *
     * @param exportTaskSetting 相关的导出任务设置。
     * @param exportTask        相关的导出任务。
     * @throws HandlerException 处理器异常。
     */
    void exportTaskFailed(ExportTaskSetting exportTaskSetting, ExportTask exportTask) throws HandlerException;

    /**
     * 导出任务过期时执行的推送操作。
     *
     * @param exportTaskSetting 相关的导出任务设置。
     * @param exportTask        相关的导出任务。
     * @throws HandlerException 处理器异常。
     */
    void exportTaskExpired(ExportTaskSetting exportTaskSetting, ExportTask exportTask) throws HandlerException;

    /**
     * 导出任务死亡时执行的推送操作。
     *
     * @param exportTaskSetting 相关的导出任务设置。
     * @param exportTask        相关的导出任务。
     * @throws HandlerException 处理器异常。
     */
    void exportTaskDead(ExportTaskSetting exportTaskSetting, ExportTask exportTask) throws HandlerException;

    /**
     * 导入任务完成时执行的推送操作。
     *
     * @param importTaskSetting 相关的导入任务设置。
     * @param importTask        相关的导入任务。
     * @throws HandlerException 处理器异常。
     */
    void importTaskFinished(ImportTaskSetting importTaskSetting, ImportTask importTask) throws HandlerException;

    /**
     * 导入任务失败时执行的推送操作。
     *
     * @param importTaskSetting 相关的导入任务设置。
     * @param importTask        相关的导入任务。
     * @throws HandlerException 处理器异常。
     */
    void importTaskFailed(ImportTaskSetting importTaskSetting, ImportTask importTask) throws HandlerException;

    /**
     * 导入任务过期时执行的推送操作。
     *
     * @param importTaskSetting 相关的导入任务设置。
     * @param importTask        相关的导入任务。
     * @throws HandlerException 处理器异常。
     */
    void importTaskExpired(ImportTaskSetting importTaskSetting, ImportTask importTask) throws HandlerException;

    /**
     * 导入任务死亡时执行的推送操作。
     *
     * @param importTaskSetting 相关的导入任务设置。
     * @param importTask        相关的导入任务。
     * @throws HandlerException 处理器异常。
     */
    void importTaskDead(ImportTaskSetting importTaskSetting, ImportTask importTask) throws HandlerException;

    /**
     * 清除完成时执行的推送操作。
     *
     * @param purgeFinishedResult 清除完成结果。
     * @throws HandlerException 处理器异常。
     * @since 1.0.0
     */
    void purgeFinished(PurgeFinishedResult purgeFinishedResult) throws HandlerException;
}
