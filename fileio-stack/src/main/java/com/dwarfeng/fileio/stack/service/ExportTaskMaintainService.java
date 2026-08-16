package com.dwarfeng.fileio.stack.service;

import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.service.BatchCrudService;
import com.dwarfeng.subgrade.stack.service.EntireLookupService;
import com.dwarfeng.subgrade.stack.service.PresetLookupService;
import com.dwarfeng.fileio.stack.bean.entity.ExportTask;

/**
 * 导出任务维护服务。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface ExportTaskMaintainService extends BatchCrudService<LongIdKey, ExportTask>,
        EntireLookupService<ExportTask>, PresetLookupService<ExportTask> {

    String CHILD_FOR_TASK_SETTING = "child_for_task_setting";

    /**
     * @since 1.0.0
     */
    String CHILD_FOR_USER = "child_for_user";

    String SHOULD_EXPIRE = "should_expire";
    String SHOULD_DEAD = "should_dead";

    /**
     * @since 1.0.0
     */
    String CREATE_DATE_DESC = "create_date_desc";

    /**
     * 获取将要被清理的导出任务实体。
     *
     * <p>
     * 返回状态为完成、失败、过期或死亡，且 <code>endDate（结束日期）</code> 早于指定的日期的实体，
     * 且按照 <code>endDate（结束日期）</code> 升序排列。
     *
     * <p>
     * 参数列表：
     * <ol>
     *     <li>Date 指定的日期。</li>
     * </ol>
     * 返回的数据按照 <code>endDate（结束日期）</code> 升序排列。
     *
     * @since 1.0.0
     */
    String TO_PURGED = "to_purged";
}
