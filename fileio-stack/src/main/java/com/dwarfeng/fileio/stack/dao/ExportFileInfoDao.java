package com.dwarfeng.fileio.stack.dao;

import com.dwarfeng.fileio.stack.bean.entity.ExportFileInfo;
import com.dwarfeng.fileio.stack.bean.key.TaskItemKey;
import com.dwarfeng.subgrade.stack.dao.BatchBaseDao;
import com.dwarfeng.subgrade.stack.dao.EntireLookupDao;
import com.dwarfeng.subgrade.stack.dao.PresetLookupDao;

/**
 * 导出文件信息数据访问层。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface ExportFileInfoDao extends BatchBaseDao<TaskItemKey, ExportFileInfo>, EntireLookupDao<ExportFileInfo>,
        PresetLookupDao<ExportFileInfo> {
}
