package com.dwarfeng.fileio.impl.handler;

import com.dwarfeng.fileio.stack.bean.dto.MetadataRemoveInfo;
import com.dwarfeng.fileio.stack.bean.dto.MetadataUpsertInfo;
import com.dwarfeng.fileio.stack.bean.entity.ImportMetadata;
import com.dwarfeng.fileio.stack.bean.key.TaskItemKey;
import com.dwarfeng.fileio.stack.handler.ImportMetadataOperateHandler;
import com.dwarfeng.fileio.stack.service.ImportMetadataMaintainService;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.springframework.stereotype.Component;

@Component
public class ImportMetadataOperateHandlerImpl implements ImportMetadataOperateHandler {

    private final ImportMetadataMaintainService importMetadataMaintainService;

    private final HandlerValidator handlerValidator;

    public ImportMetadataOperateHandlerImpl(
            ImportMetadataMaintainService importMetadataMaintainService,
            HandlerValidator handlerValidator
    ) {
        this.importMetadataMaintainService = importMetadataMaintainService;
        this.handlerValidator = handlerValidator;
    }

    @Override
    public void upsert(MetadataUpsertInfo upsertInfo) throws HandlerException {
        try {
            // 展开参数。
            TaskItemKey key = upsertInfo.getKey();
            String content = upsertInfo.getContent();

            // 确认导入任务存在。
            LongIdKey taskKey = new LongIdKey(key.getTaskId());
            handlerValidator.makeSureImportTaskExists(taskKey);

            // 构建导入元数据。
            ImportMetadata importMetadata = new ImportMetadata(key, content, "通过维护处理器插入/更新");

            // 调用维护服务插入/更新导入元数据。
            importMetadataMaintainService.insertOrUpdate(importMetadata);
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

            // 确认导入任务存在。
            LongIdKey taskKey = new LongIdKey(key.getTaskId());
            handlerValidator.makeSureImportTaskExists(taskKey);
            // 确认导入元数据存在。
            handlerValidator.makeSureImportMetadataExists(key);

            // 调用维护服务删除导入元数据。
            importMetadataMaintainService.delete(key);
        } catch (HandlerException e) {
            throw e;
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }
}
