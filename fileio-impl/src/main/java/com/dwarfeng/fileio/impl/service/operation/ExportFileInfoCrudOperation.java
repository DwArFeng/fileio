package com.dwarfeng.fileio.impl.service.operation;

import com.dwarfeng.fileio.impl.handler.FtpPathResolver;
import com.dwarfeng.fileio.sdk.util.FileUtil;
import com.dwarfeng.fileio.stack.bean.entity.ExportFileInfo;
import com.dwarfeng.fileio.stack.bean.key.TaskItemKey;
import com.dwarfeng.fileio.stack.cache.ExportFileInfoCache;
import com.dwarfeng.fileio.stack.dao.ExportFileInfoDao;
import com.dwarfeng.ftp.stack.handler.FtpHandler;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionCodes;
import com.dwarfeng.subgrade.sdk.service.custom.operation.BatchCrudOperation;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ExportFileInfoCrudOperation implements BatchCrudOperation<TaskItemKey, ExportFileInfo> {

    private final ExportFileInfoDao exportFileInfoDao;
    private final ExportFileInfoCache exportFileInfoCache;

    private final FtpHandler ftpHandler;

    private final FtpPathResolver ftpPathResolver;

    @Value("${com.dwarfeng.fileio.cache.timeout.entity.export_file_info}")
    private long exportFileInfoTimeout;

    public ExportFileInfoCrudOperation(
            ExportFileInfoDao exportFileInfoDao,
            ExportFileInfoCache exportFileInfoCache,
            FtpHandler ftpHandler,
            FtpPathResolver ftpPathResolver
    ) {
        this.exportFileInfoDao = exportFileInfoDao;
        this.exportFileInfoCache = exportFileInfoCache;
        this.ftpHandler = ftpHandler;
        this.ftpPathResolver = ftpPathResolver;
    }

    @Override
    public boolean exists(TaskItemKey key) throws Exception {
        return exportFileInfoCache.exists(key) || exportFileInfoDao.exists(key);
    }

    @Override
    public ExportFileInfo get(TaskItemKey key) throws Exception {
        if (exportFileInfoCache.exists(key)) {
            return exportFileInfoCache.get(key);
        } else {
            if (!exportFileInfoDao.exists(key)) {
                throw new ServiceException(ServiceExceptionCodes.ENTITY_NOT_EXIST);
            }
            ExportFileInfo exportFileInfo = exportFileInfoDao.get(key);
            exportFileInfoCache.push(exportFileInfo, exportFileInfoTimeout);
            return exportFileInfo;
        }
    }

    @Override
    public TaskItemKey insert(ExportFileInfo exportFileInfo) throws Exception {
        exportFileInfoCache.push(exportFileInfo, exportFileInfoTimeout);
        return exportFileInfoDao.insert(exportFileInfo);
    }

    @Override
    public void update(ExportFileInfo exportFileInfo) throws Exception {
        exportFileInfoCache.push(exportFileInfo, exportFileInfoTimeout);
        exportFileInfoDao.update(exportFileInfo);
    }

    @Override
    public void delete(TaskItemKey key) throws Exception {
        // 如果存在票据文件，则删除票据文件。
        if (ftpHandler.existsFile(
                ftpPathResolver.resolvePath(
                        FtpPathResolver.RELATIVE_PATHS_EXPORT_FILE), FileUtil.resolveFileName(key)
        )) {
            ftpHandler.deleteFile(
                    ftpPathResolver.resolvePath(FtpPathResolver.RELATIVE_PATHS_EXPORT_FILE),
                    FileUtil.resolveFileName(key)
            );
        }

        // 删除票据文件信息实体自身。
        exportFileInfoCache.delete(key);
        exportFileInfoDao.delete(key);
    }

    @Override
    public boolean allExists(List<TaskItemKey> keys) throws Exception {
        return exportFileInfoCache.allExists(keys) || exportFileInfoDao.allExists(keys);
    }

    @Override
    public boolean nonExists(List<TaskItemKey> keys) throws Exception {
        return exportFileInfoCache.nonExists(keys) && exportFileInfoDao.nonExists(keys);
    }

    @Override
    public List<ExportFileInfo> batchGet(List<TaskItemKey> keys) throws Exception {
        if (exportFileInfoCache.allExists(keys)) {
            return exportFileInfoCache.batchGet(keys);
        } else {
            if (!exportFileInfoDao.allExists(keys)) {
                throw new ServiceException(ServiceExceptionCodes.ENTITY_NOT_EXIST);
            }
            List<ExportFileInfo> exportFileInfos = exportFileInfoDao.batchGet(keys);
            exportFileInfoCache.batchPush(exportFileInfos, exportFileInfoTimeout);
            return exportFileInfos;
        }
    }

    @Override
    public List<TaskItemKey> batchInsert(List<ExportFileInfo> exportFileInfos) throws Exception {
        exportFileInfoCache.batchPush(exportFileInfos, exportFileInfoTimeout);
        return exportFileInfoDao.batchInsert(exportFileInfos);
    }

    @Override
    public void batchUpdate(List<ExportFileInfo> exportFileInfos) throws Exception {
        exportFileInfoCache.batchPush(exportFileInfos, exportFileInfoTimeout);
        exportFileInfoDao.batchUpdate(exportFileInfos);
    }

    @Override
    public void batchDelete(List<TaskItemKey> keys) throws Exception {
        for (TaskItemKey key : keys) {
            delete(key);
        }
    }
}
