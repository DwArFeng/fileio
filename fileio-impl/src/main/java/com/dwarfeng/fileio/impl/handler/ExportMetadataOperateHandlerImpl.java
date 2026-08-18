package com.dwarfeng.fileio.impl.handler;

import com.dwarfeng.fileio.stack.bean.dto.MetadataRemoveInfo;
import com.dwarfeng.fileio.stack.bean.dto.MetadataUpsertInfo;
import com.dwarfeng.fileio.stack.bean.entity.ExportMetadata;
import com.dwarfeng.fileio.stack.bean.key.TaskItemKey;
import com.dwarfeng.fileio.stack.handler.ExportMetadataOperateHandler;
import com.dwarfeng.fileio.stack.service.ExportMetadataMaintainService;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.springframework.stereotype.Component;

@Component
public class ExportMetadataOperateHandlerImpl implements ExportMetadataOperateHandler {

    private final ExportMetadataMaintainService exportMetadataMaintainService;

    private final HandlerValidator handlerValidator;

    public ExportMetadataOperateHandlerImpl(
            ExportMetadataMaintainService exportMetadataMaintainService,
            HandlerValidator handlerValidator
    ) {
        this.exportMetadataMaintainService = exportMetadataMaintainService;
        this.handlerValidator = handlerValidator;
    }

    @Override
    public void upsert(MetadataUpsertInfo upsertInfo) throws HandlerException {
        try {
            // 展开参数。
            TaskItemKey key = upsertInfo.getKey();
            String content = upsertInfo.getContent();

            // 确认导出任务存在。
            LongIdKey taskKey = new LongIdKey(key.getTaskId());
            handlerValidator.makeSureExportTaskExists(taskKey);

            // 构建导出元数据。
            ExportMetadata exportMetadata = new ExportMetadata(key, content, "通过维护处理器插入/更新");

            // 调用维护服务插入/更新导出元数据。
            exportMetadataMaintainService.insertOrUpdate(exportMetadata);
        } catch (HandlerException e) {
            throw e;
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    @Override
    public void remove(MetadataRemoveInfo removeInfo) throws HandlerException {
        try {
            // 展开参数。
            TaskItemKey key = removeInfo.getKey();

            // 确认导出任务存在。
            LongIdKey taskKey = new LongIdKey(key.getTaskId());
            handlerValidator.makeSureExportTaskExists(taskKey);
            // 确认导出元数据存在。
            handlerValidator.makeSureExportMetadataExists(key);

            // 调用维护服务删除导出元数据。
            exportMetadataMaintainService.delete(key);
        } catch (HandlerException e) {
            throw e;
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }
}
