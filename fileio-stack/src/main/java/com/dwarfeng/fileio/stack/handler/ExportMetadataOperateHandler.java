package com.dwarfeng.fileio.stack.handler;

import com.dwarfeng.fileio.stack.bean.dto.MetadataRemoveInfo;
import com.dwarfeng.fileio.stack.bean.dto.MetadataUpsertInfo;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.handler.Handler;

/**
 * 导出元数据操作处理器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface ExportMetadataOperateHandler extends Handler {

    /**
     * 插入/更新导出元数据。
     *
     * @param upsertInfo 导出元数据的插入/更新信息。
     * @throws HandlerException 处理器异常。
     */
    void upsert(MetadataUpsertInfo upsertInfo) throws HandlerException;

    /**
     * 删除导出元数据。
     *
     * @param removeInfo 导出元数据的删除信息。
     * @throws HandlerException 处理器异常。
     */
    void remove(MetadataRemoveInfo removeInfo) throws HandlerException;
}
