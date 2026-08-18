package com.dwarfeng.fileio.sdk.util;

import com.dwarfeng.fileio.stack.exception.*;
import com.dwarfeng.subgrade.stack.exception.ServiceException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 异常的帮助工具类。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public final class ServiceExceptionHelper {

    /**
     * 向指定的映射中添加 Fileio 默认的目标映射。
     *
     * <p>
     * 该方法可以在配置类中快速的搭建目标映射。
     *
     * @param map 指定的映射，允许为 <code>null</code>。
     * @return 添加了默认目标的映射。
     */
    public static Map<Class<? extends Exception>, ServiceException.Code> putDefaultDestination(
            Map<Class<? extends Exception>, ServiceException.Code> map
    ) {
        if (Objects.isNull(map)) {
            map = new HashMap<>();
        }

        map.put(ExportFileInfoExistsException.class, ServiceExceptionCodes.EXPORT_FILE_INFO_EXISTS);
        map.put(ExportFileInfoNotExistsException.class, ServiceExceptionCodes.EXPORT_FILE_INFO_NOT_EXISTS);
        map.put(ExportTaskNotExistsException.class, ServiceExceptionCodes.EXPORT_TASK_NOT_EXISTS);
        map.put(ExportTaskSettingNotExistsException.class, ServiceExceptionCodes.EXPORT_TASK_SETTING_NOT_EXISTS);
        map.put(ExportTemplateInfoExistsException.class, ServiceExceptionCodes.EXPORT_TEMPLATE_INFO_EXISTS);
        map.put(ExportTemplateInfoNotExistsException.class, ServiceExceptionCodes.EXPORT_TEMPLATE_INFO_NOT_EXISTS);
        map.put(ImportFileInfoExistsException.class, ServiceExceptionCodes.IMPORT_FILE_INFO_EXISTS);
        map.put(ImportFileInfoNotExistsException.class, ServiceExceptionCodes.IMPORT_FILE_INFO_NOT_EXISTS);
        map.put(ImportTaskNotExistsException.class, ServiceExceptionCodes.IMPORT_TASK_NOT_EXISTS);
        map.put(ImportTaskSettingNotExistsException.class, ServiceExceptionCodes.IMPORT_TASK_SETTING_NOT_EXISTS);
        map.put(ImportTemplateInfoExistsException.class, ServiceExceptionCodes.IMPORT_TEMPLATE_INFO_EXISTS);
        map.put(ImportTemplateInfoNotExistsException.class, ServiceExceptionCodes.IMPORT_TEMPLATE_INFO_NOT_EXISTS);
        map.put(TaskExpireDateNotReachedException.class, ServiceExceptionCodes.TASK_EXPIRE_DATE_NOT_REACHED);
        map.put(TaskStatusMismatchException.class, ServiceExceptionCodes.TASK_STATUS_MISMATCH);
        map.put(TaskDeadDateNotReachedException.class, ServiceExceptionCodes.TASK_DEAD_DATE_NOT_REACHED);
        map.put(ExportMetadataNotExistsException.class, ServiceExceptionCodes.EXPORT_METADATA_NOT_EXISTS);
        map.put(ImportMetadataNotExistsException.class, ServiceExceptionCodes.IMPORT_METADATA_NOT_EXISTS);

        return map;
    }

    private ServiceExceptionHelper() {
        throw new IllegalStateException("禁止外部实例化");
    }
}
