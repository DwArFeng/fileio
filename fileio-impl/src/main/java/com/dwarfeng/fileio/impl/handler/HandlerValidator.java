package com.dwarfeng.fileio.impl.handler;

import com.dwarfeng.fileio.stack.bean.entity.ExportTask;
import com.dwarfeng.fileio.stack.bean.entity.ImportTask;
import com.dwarfeng.fileio.stack.bean.key.TaskItemKey;
import com.dwarfeng.fileio.stack.bean.key.TaskSettingItemKey;
import com.dwarfeng.fileio.stack.exception.*;
import com.dwarfeng.fileio.stack.service.*;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Objects;
import java.util.Set;

/**
 * 处理器验证器。
 *
 * <p>
 * 为处理器提供公共的验证方法。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component
public class HandlerValidator {

    private final ExportTaskSettingMaintainService exportTaskSettingMaintainService;
    private final ExportTemplateInfoMaintainService exportTemplateInfoMaintainService;
    private final ExportTaskMaintainService exportTaskMaintainService;
    private final ExportFileInfoMaintainService exportFileInfoMaintainService;
    private final ImportTaskSettingMaintainService importTaskSettingMaintainService;
    private final ImportTemplateInfoMaintainService importTemplateInfoMaintainService;
    private final ImportTaskMaintainService importTaskMaintainService;
    private final ImportFileInfoMaintainService importFileInfoMaintainService;

    public HandlerValidator(
            ExportTaskSettingMaintainService exportTaskSettingMaintainService,
            ExportTemplateInfoMaintainService exportTemplateInfoMaintainService,
            ExportTaskMaintainService exportTaskMaintainService,
            ExportFileInfoMaintainService exportFileInfoMaintainService,
            ImportTaskSettingMaintainService importTaskSettingMaintainService,
            ImportTemplateInfoMaintainService importTemplateInfoMaintainService,
            ImportTaskMaintainService importTaskMaintainService,
            ImportFileInfoMaintainService importFileInfoMaintainService
    ) {
        this.exportTaskSettingMaintainService = exportTaskSettingMaintainService;
        this.exportTemplateInfoMaintainService = exportTemplateInfoMaintainService;
        this.exportTaskMaintainService = exportTaskMaintainService;
        this.exportFileInfoMaintainService = exportFileInfoMaintainService;
        this.importTaskSettingMaintainService = importTaskSettingMaintainService;
        this.importTemplateInfoMaintainService = importTemplateInfoMaintainService;
        this.importTaskMaintainService = importTaskMaintainService;
        this.importFileInfoMaintainService = importFileInfoMaintainService;
    }

    public void makeSureExportTaskSettingExists(LongIdKey exportTaskSettingKey) throws HandlerException {
        try {
            if (!exportTaskSettingMaintainService.exists(exportTaskSettingKey)) {
                throw new ExportTaskSettingNotExistsException(exportTaskSettingKey);
            }
        } catch (ServiceException e) {
            throw new HandlerException(e);
        }
    }

    public void makeSureExportTemplateInfoExists(TaskSettingItemKey exportTemplateInfoKey) throws HandlerException {
        try {
            if (!exportTemplateInfoMaintainService.exists(exportTemplateInfoKey)) {
                throw new ExportTemplateInfoNotExistsException(exportTemplateInfoKey);
            }
        } catch (ServiceException e) {
            throw new HandlerException(e);
        }
    }

    public void makeSureExportTemplateInfoNotExists(TaskSettingItemKey exportTemplateInfoKey) throws HandlerException {
        try {
            if (exportTemplateInfoMaintainService.exists(exportTemplateInfoKey)) {
                throw new ExportTemplateInfoExistsException(exportTemplateInfoKey);
            }
        } catch (ServiceException e) {
            throw new HandlerException(e);
        }
    }

    public void makeSureExportTaskExists(LongIdKey exportTaskKey) throws HandlerException {
        try {
            if (!exportTaskMaintainService.exists(exportTaskKey)) {
                throw new ExportTaskNotExistsException(exportTaskKey);
            }
        } catch (ServiceException e) {
            throw new HandlerException(e);
        }
    }

    public void makeSureExportFileInfoNotExists(TaskItemKey exportFileInfoKey) throws HandlerException {
        try {
            if (exportFileInfoMaintainService.exists(exportFileInfoKey)) {
                throw new ExportFileInfoExistsException(exportFileInfoKey);
            }
        } catch (ServiceException e) {
            throw new HandlerException(e);
        }
    }

    public void makeSureExportFileInfoExists(TaskItemKey exportFileInfoKey) throws HandlerException {
        try {
            if (!exportFileInfoMaintainService.exists(exportFileInfoKey)) {
                throw new ExportFileInfoNotExistsException(exportFileInfoKey);
            }
        } catch (ServiceException e) {
            throw new HandlerException(e);
        }
    }

    public void makeSureImportTaskSettingExists(LongIdKey importTaskSettingKey) throws HandlerException {
        try {
            if (!importTaskSettingMaintainService.exists(importTaskSettingKey)) {
                throw new ImportTaskSettingNotExistsException(importTaskSettingKey);
            }
        } catch (ServiceException e) {
            throw new HandlerException(e);
        }
    }

    public void makeSureImportTemplateInfoExists(TaskSettingItemKey importTemplateInfoKey) throws HandlerException {
        try {
            if (!importTemplateInfoMaintainService.exists(importTemplateInfoKey)) {
                throw new ImportTemplateInfoNotExistsException(importTemplateInfoKey);
            }
        } catch (ServiceException e) {
            throw new HandlerException(e);
        }
    }

    public void makeSureImportTemplateInfoNotExists(TaskSettingItemKey importTemplateInfoKey) throws HandlerException {
        try {
            if (importTemplateInfoMaintainService.exists(importTemplateInfoKey)) {
                throw new ImportTemplateInfoExistsException(importTemplateInfoKey);
            }
        } catch (ServiceException e) {
            throw new HandlerException(e);
        }
    }

    public void makeSureImportTaskExists(LongIdKey importTaskKey) throws HandlerException {
        try {
            if (!importTaskMaintainService.exists(importTaskKey)) {
                throw new ImportTaskNotExistsException(importTaskKey);
            }
        } catch (ServiceException e) {
            throw new HandlerException(e);
        }
    }

    public void makeSureImportFileInfoExists(TaskItemKey importFileInfoKey) throws HandlerException {
        try {
            if (!importFileInfoMaintainService.exists(importFileInfoKey)) {
                throw new ImportFileInfoNotExistsException(importFileInfoKey);
            }
        } catch (ServiceException e) {
            throw new HandlerException(e);
        }
    }

    public void makeSureImportFileInfoNotExists(TaskItemKey importFileInfoKey) throws HandlerException {
        try {
            if (importFileInfoMaintainService.exists(importFileInfoKey)) {
                throw new ImportFileInfoExistsException(importFileInfoKey);
            }
        } catch (ServiceException e) {
            throw new HandlerException(e);
        }
    }

    public void makeSureExportTaskStatusValid(LongIdKey exportTaskKey, Set<Integer> validStatusSet)
            throws HandlerException {
        try {
            ExportTask exportTask = exportTaskMaintainService.getIfExists(exportTaskKey);
            if (Objects.isNull(exportTask)) {
                throw new ExportTaskNotExistsException(exportTaskKey);
            }
            if (!validStatusSet.contains(exportTask.getStatus())) {
                throw new TaskStatusMismatchException(validStatusSet, exportTask.getStatus());
            }
        } catch (ServiceException e) {
            throw new HandlerException(e);
        }
    }

    public void makeSureExportTaskExpireDateReached(LongIdKey exportTaskKey) throws HandlerException {
        try {
            ExportTask exportTask = exportTaskMaintainService.getIfExists(exportTaskKey);
            if (Objects.isNull(exportTask)) {
                throw new ExportTaskNotExistsException(exportTaskKey);
            }
            Date shouldExpireDate = exportTask.getShouldExpireDate();
            if (Objects.isNull(shouldExpireDate)) {
                return;
            }
            Date currentDate = new Date();
            if (shouldExpireDate.after(currentDate)) {
                throw new TaskExpireDateNotReachedException(shouldExpireDate, currentDate);
            }
        } catch (ServiceException e) {
            throw new HandlerException(e);
        }
    }

    public void makeSureExportTaskDeadDateReached(LongIdKey exportTaskKey) throws HandlerException {
        try {
            ExportTask exportTask = exportTaskMaintainService.getIfExists(exportTaskKey);
            if (Objects.isNull(exportTask)) {
                throw new ExportTaskNotExistsException(exportTaskKey);
            }
            Date shouldDeadDate = exportTask.getShouldDeadDate();
            if (Objects.isNull(shouldDeadDate)) {
                return;
            }
            Date currentDate = new Date();
            if (shouldDeadDate.after(currentDate)) {
                throw new TaskDeadDateNotReachedException(shouldDeadDate, currentDate);
            }
        } catch (ServiceException e) {
            throw new HandlerException(e);
        }
    }

    public void makeSureImportTaskStatusValid(LongIdKey importTaskKey, Set<Integer> validStatusSet)
            throws HandlerException {
        try {
            ImportTask importTask = importTaskMaintainService.getIfExists(importTaskKey);
            if (Objects.isNull(importTask)) {
                throw new ImportTaskNotExistsException(importTaskKey);
            }
            if (!validStatusSet.contains(importTask.getStatus())) {
                throw new TaskStatusMismatchException(validStatusSet, importTask.getStatus());
            }
        } catch (ServiceException e) {
            throw new HandlerException(e);
        }
    }

    public void makeSureImportTaskExpireDateReached(LongIdKey importTaskKey) throws HandlerException {
        try {
            ImportTask importTask = importTaskMaintainService.getIfExists(importTaskKey);
            if (Objects.isNull(importTask)) {
                throw new ImportTaskNotExistsException(importTaskKey);
            }
            Date shouldExpireDate = importTask.getShouldExpireDate();
            if (Objects.isNull(shouldExpireDate)) {
                return;
            }
            Date currentDate = new Date();
            if (shouldExpireDate.after(currentDate)) {
                throw new TaskExpireDateNotReachedException(shouldExpireDate, currentDate);
            }
        } catch (ServiceException e) {
            throw new HandlerException(e);
        }
    }

    public void makeSureImportTaskDeadDateReached(LongIdKey importTaskKey) throws HandlerException {
        try {
            ImportTask importTask = importTaskMaintainService.getIfExists(importTaskKey);
            if (Objects.isNull(importTask)) {
                throw new ImportTaskNotExistsException(importTaskKey);
            }
            Date shouldDeadDate = importTask.getShouldDeadDate();
            if (Objects.isNull(shouldDeadDate)) {
                return;
            }
            Date currentDate = new Date();
            if (shouldDeadDate.after(currentDate)) {
                throw new TaskDeadDateNotReachedException(shouldDeadDate, currentDate);
            }
        } catch (ServiceException e) {
            throw new HandlerException(e);
        }
    }

    public void makeSureExportMetadataExists(TaskItemKey key) throws HandlerException {
        try {
            if (!exportFileInfoMaintainService.exists(key)) {
                throw new ExportMetadataNotExistsException(key);
            }
        } catch (ServiceException e) {
            throw new HandlerException(e);
        }
    }

    public void makeSureImportMetadataExists(TaskItemKey key) throws HandlerException {
        try {
            if (!importFileInfoMaintainService.exists(key)) {
                throw new ImportMetadataNotExistsException(key);
            }
        } catch (ServiceException e) {
            throw new HandlerException(e);
        }
    }
}
