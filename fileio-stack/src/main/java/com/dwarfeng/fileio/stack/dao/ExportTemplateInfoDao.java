package com.dwarfeng.fileio.stack.dao;

import com.dwarfeng.subgrade.stack.dao.BatchBaseDao;
import com.dwarfeng.subgrade.stack.dao.EntireLookupDao;
import com.dwarfeng.subgrade.stack.dao.PresetLookupDao;
import com.dwarfeng.fileio.stack.bean.entity.ExportTemplateInfo;
import com.dwarfeng.fileio.stack.bean.key.TaskSettingItemKey;

/**
 * 导出模板信息数据访问层。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface ExportTemplateInfoDao extends BatchBaseDao<TaskSettingItemKey, ExportTemplateInfo>,
        EntireLookupDao<ExportTemplateInfo>, PresetLookupDao<ExportTemplateInfo> {
}
