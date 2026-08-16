package com.dwarfeng.fileio.impl.cache;

import com.dwarfeng.fileio.stack.bean.entity.ReaderInfo;
import com.dwarfeng.fileio.stack.bean.key.TaskSettingItemKey;
import com.dwarfeng.fileio.stack.cache.ReaderInfoCache;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.SkipRecord;
import com.dwarfeng.subgrade.stack.cache.BatchBaseCache;
import com.dwarfeng.subgrade.stack.exception.CacheException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class ReaderInfoCacheImpl implements ReaderInfoCache {

    private final BatchBaseCache<TaskSettingItemKey, ReaderInfo> batchBaseCache;

    public ReaderInfoCacheImpl(
            BatchBaseCache<TaskSettingItemKey, ReaderInfo> batchBaseCache
    ) {
        this.batchBaseCache = batchBaseCache;
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public boolean exists(TaskSettingItemKey key) throws CacheException {
        return batchBaseCache.exists(key);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public ReaderInfo get(TaskSettingItemKey key) throws CacheException {
        return batchBaseCache.get(key);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void push(ReaderInfo value, long timeout) throws CacheException {
        batchBaseCache.push(value, timeout);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void delete(TaskSettingItemKey key) throws CacheException {
        batchBaseCache.delete(key);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void clear() throws CacheException {
        batchBaseCache.clear();
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public boolean allExists(@SkipRecord List<TaskSettingItemKey> keys) throws CacheException {
        return batchBaseCache.allExists(keys);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public boolean nonExists(@SkipRecord List<TaskSettingItemKey> keys) throws CacheException {
        return batchBaseCache.nonExists(keys);
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
    public List<ReaderInfo> batchGet(@SkipRecord List<TaskSettingItemKey> keys) throws CacheException {
        return batchBaseCache.batchGet(keys);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void batchPush(@SkipRecord List<ReaderInfo> entities, long timeout) throws CacheException {
        batchBaseCache.batchPush(entities, timeout);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void batchDelete(@SkipRecord List<TaskSettingItemKey> keys) throws CacheException {
        batchBaseCache.batchDelete(keys);
    }
}
