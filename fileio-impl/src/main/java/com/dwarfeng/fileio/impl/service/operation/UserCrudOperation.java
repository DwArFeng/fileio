package com.dwarfeng.fileio.impl.service.operation;

import com.dwarfeng.fileio.stack.bean.entity.ExportTask;
import com.dwarfeng.fileio.stack.bean.entity.ImportTask;
import com.dwarfeng.fileio.stack.bean.entity.User;
import com.dwarfeng.fileio.stack.cache.ExportTaskCache;
import com.dwarfeng.fileio.stack.cache.ImportTaskCache;
import com.dwarfeng.fileio.stack.cache.UserCache;
import com.dwarfeng.fileio.stack.dao.ExportTaskDao;
import com.dwarfeng.fileio.stack.dao.ImportTaskDao;
import com.dwarfeng.fileio.stack.dao.UserDao;
import com.dwarfeng.fileio.stack.service.ExportTaskMaintainService;
import com.dwarfeng.fileio.stack.service.ImportTaskMaintainService;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionCodes;
import com.dwarfeng.subgrade.sdk.service.custom.operation.BatchCrudOperation;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserCrudOperation implements BatchCrudOperation<StringIdKey, User> {

    private final UserDao userDao;
    private final UserCache userCache;

    private final ImportTaskDao importTaskDao;
    private final ImportTaskCache importTaskCache;

    private final ExportTaskDao exportTaskDao;
    private final ExportTaskCache exportTaskCache;

    @Value("${com.dwarfeng.fileio.cache.timeout.entity.user}")
    private long userTimeout;

    public UserCrudOperation(
            UserDao userDao,
            UserCache userCache,
            ImportTaskDao importTaskDao,
            ImportTaskCache importTaskCache,
            ExportTaskDao exportTaskDao,
            ExportTaskCache exportTaskCache
    ) {
        this.userDao = userDao;
        this.userCache = userCache;
        this.importTaskDao = importTaskDao;
        this.importTaskCache = importTaskCache;
        this.exportTaskDao = exportTaskDao;
        this.exportTaskCache = exportTaskCache;
    }

    @Override
    public boolean exists(StringIdKey key) throws Exception {
        return userCache.exists(key) || userDao.exists(key);
    }

    @Override
    public User get(StringIdKey key) throws Exception {
        if (userCache.exists(key)) {
            return userCache.get(key);
        } else {
            if (!userDao.exists(key)) {
                throw new ServiceException(ServiceExceptionCodes.ENTITY_NOT_EXIST);
            }
            User user = userDao.get(key);
            userCache.push(user, userTimeout);
            return user;
        }
    }

    @Override
    public StringIdKey insert(User user) throws Exception {
        userCache.push(user, userTimeout);
        return userDao.insert(user);
    }

    @Override
    public void update(User user) throws Exception {
        userCache.push(user, userTimeout);
        userDao.update(user);
    }

    @Override
    public void delete(StringIdKey key) throws Exception {
        // 解除 用户 实体与 导入任务 实体之间的关联。
        List<ImportTask> importTasks = importTaskDao.lookup(
                ImportTaskMaintainService.CHILD_FOR_USER, new Object[]{key}
        );
        importTasks.forEach(importTask -> importTask.setUserKey(null));
        importTaskCache.batchDelete(importTasks.stream().map(ImportTask::getKey).collect(Collectors.toList()));
        importTaskDao.batchUpdate(importTasks);

        // 解除 用户 实体与 导出任务 实体之间的关联。
        List<ExportTask> exportTasks = exportTaskDao.lookup(
                ExportTaskMaintainService.CHILD_FOR_USER, new Object[]{key}
        );
        exportTasks.forEach(exportTask -> exportTask.setUserKey(null));
        exportTaskCache.batchDelete(exportTasks.stream().map(ExportTask::getKey).collect(Collectors.toList()));
        exportTaskDao.batchUpdate(exportTasks);

        // 删除 用户 实体自身。
        userCache.delete(key);
        userDao.delete(key);
    }

    @Override
    public boolean allExists(List<StringIdKey> keys) throws Exception {
        return userCache.allExists(keys) || userDao.allExists(keys);
    }

    @Override
    public boolean nonExists(List<StringIdKey> keys) throws Exception {
        return userCache.nonExists(keys) && userDao.nonExists(keys);
    }

    @Override
    public List<User> batchGet(List<StringIdKey> keys) throws Exception {
        if (userCache.allExists(keys)) {
            return userCache.batchGet(keys);
        } else {
            if (!userDao.allExists(keys)) {
                throw new ServiceException(ServiceExceptionCodes.ENTITY_NOT_EXIST);
            }
            List<User> users = userDao.batchGet(keys);
            userCache.batchPush(users, userTimeout);
            return users;
        }
    }

    @Override
    public List<StringIdKey> batchInsert(List<User> users) throws Exception {
        userCache.batchPush(users, userTimeout);
        return userDao.batchInsert(users);
    }

    @Override
    public void batchUpdate(List<User> users) throws Exception {
        userCache.batchPush(users, userTimeout);
        userDao.batchUpdate(users);
    }

    @Override
    public void batchDelete(List<StringIdKey> keys) throws Exception {
        for (StringIdKey key : keys) {
            delete(key);
        }
    }
}
