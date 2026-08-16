package com.dwarfeng.fileio.impl.dao.preset;

import com.dwarfeng.fileio.sdk.util.Constants;
import com.dwarfeng.fileio.stack.service.ImportTaskMaintainService;
import com.dwarfeng.subgrade.sdk.hibernate.criteria.PresetCriteriaMaker;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ImportTaskPresetCriteriaMaker implements PresetCriteriaMaker {

    @Override
    public void makeCriteria(DetachedCriteria criteria, String preset, Object[] objs) {
        switch (preset) {
            case ImportTaskMaintainService.CHILD_FOR_TASK_SETTING:
                childForTaskSetting(criteria, objs);
                break;
            case ImportTaskMaintainService.CHILD_FOR_USER:
                childForUser(criteria, objs);
                break;
            case ImportTaskMaintainService.SHOULD_EXPIRE:
                shouldExpire(criteria, objs);
                break;
            case ImportTaskMaintainService.SHOULD_DEAD:
                shouldDead(criteria, objs);
                break;
            case ImportTaskMaintainService.CREATE_DATE_DESC:
                createDesc(criteria, objs);
                break;
            case ImportTaskMaintainService.TO_PURGED:
                toPurged(criteria, objs);
                break;
            default:
                throw new IllegalArgumentException("无法识别的预设: " + preset);
        }
    }

    @SuppressWarnings("DuplicatedCode")
    private void childForTaskSetting(DetachedCriteria criteria, Object[] objs) {
        try {
            if (Objects.isNull(objs[0])) {
                criteria.add(Restrictions.isNull("settingLongId"));
            } else {
                LongIdKey longIdKey = (LongIdKey) objs[0];
                criteria.add(Restrictions.eqOrIsNull("settingLongId", longIdKey.getLongId()));
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objs));
        }
    }

    private void childForUser(DetachedCriteria criteria, Object[] objs) {
        try {
            StringIdKey userKey = (StringIdKey) objs[0];
            criteria.add(Restrictions.eq("userStringId", userKey.getStringId()));
            criteria.addOrder(Order.desc("createDate"));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objs));
        }
    }

    private void shouldExpire(DetachedCriteria criteria, Object[] objs) {
        try {
            Date currentDate = new Date();
            criteria.add(Restrictions.in("status", Collections.singletonList(Constants.IMPORT_TASK_STATUS_CREATED)));
            criteria.add(Restrictions.le("shouldExpireDate", currentDate));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objs));
        }
    }

    @SuppressWarnings("DuplicatedCode")
    private void shouldDead(DetachedCriteria criteria, Object[] objs) {
        try {
            Date currentDate = new Date();
            criteria.add(Restrictions.in("status", Collections.singletonList(Constants.EXPORT_TASK_STATUS_PROCESSING)));
            criteria.add(Restrictions.le("shouldDeadDate", currentDate));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objs));
        }
    }

    private void createDesc(DetachedCriteria criteria, @SuppressWarnings("unused") Object[] objs) {
        criteria.addOrder(Order.desc("createDate"));
    }

    private void toPurged(DetachedCriteria criteria, Object[] objs) {
        try {
            Date date = (Date) objs[0];
            // 添加状态条件：完成、失败、过期、死亡。
            List<Integer> statusList = Arrays.asList(
                    Constants.IMPORT_TASK_STATUS_FINISHED,
                    Constants.IMPORT_TASK_STATUS_FAILED,
                    Constants.IMPORT_TASK_STATUS_EXPIRED,
                    Constants.IMPORT_TASK_STATUS_DEAD
            );
            criteria.add(Restrictions.in("status", statusList));
            // 添加日期条件：endDate 早于指定日期，且 endDate 不为 null。
            criteria.add(Restrictions.lt("endDate", date));
            criteria.add(Restrictions.isNotNull("endDate"));
            // 按 endDate 升序排列。
            criteria.addOrder(Order.asc("endDate"));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objs));
        }
    }
}
