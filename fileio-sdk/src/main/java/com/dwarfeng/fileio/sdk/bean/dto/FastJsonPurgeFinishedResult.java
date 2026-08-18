package com.dwarfeng.fileio.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fileio.stack.bean.dto.PurgeFinishedResult;
import com.dwarfeng.subgrade.stack.bean.Bean;

import java.util.Objects;

/**
 * FastJson 清除完成结果。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class FastJsonPurgeFinishedResult implements Bean {

    private static final long serialVersionUID = -6924033431070469968L;

    public static FastJsonPurgeFinishedResult of(PurgeFinishedResult purgeFinishedResult) {
        if (Objects.isNull(purgeFinishedResult)) {
            return null;
        } else {
            return new FastJsonPurgeFinishedResult(
                    purgeFinishedResult.getImportTaskDeletionCount(),
                    purgeFinishedResult.isImportTaskDivergent(),
                    purgeFinishedResult.getExportTaskDeletionCount(),
                    purgeFinishedResult.isExportTaskDivergent()
            );
        }
    }

    @JSONField(name = "import_task_deletion_count", ordinal = 1)
    private int importTaskDeletionCount;

    @JSONField(name = "import_task_divergent", ordinal = 2)
    private boolean importTaskDivergent;

    @JSONField(name = "export_task_deletion_count", ordinal = 3)
    private int exportTaskDeletionCount;

    @JSONField(name = "export_task_divergent", ordinal = 4)
    private boolean exportTaskDivergent;

    public FastJsonPurgeFinishedResult() {
    }

    public FastJsonPurgeFinishedResult(
            int importTaskDeletionCount, boolean importTaskDivergent,
            int exportTaskDeletionCount, boolean exportTaskDivergent
    ) {
        this.importTaskDeletionCount = importTaskDeletionCount;
        this.importTaskDivergent = importTaskDivergent;
        this.exportTaskDeletionCount = exportTaskDeletionCount;
        this.exportTaskDivergent = exportTaskDivergent;
    }

    public int getImportTaskDeletionCount() {
        return importTaskDeletionCount;
    }

    public void setImportTaskDeletionCount(int importTaskDeletionCount) {
        this.importTaskDeletionCount = importTaskDeletionCount;
    }

    public boolean isImportTaskDivergent() {
        return importTaskDivergent;
    }

    public void setImportTaskDivergent(boolean importTaskDivergent) {
        this.importTaskDivergent = importTaskDivergent;
    }

    public int getExportTaskDeletionCount() {
        return exportTaskDeletionCount;
    }

    public void setExportTaskDeletionCount(int exportTaskDeletionCount) {
        this.exportTaskDeletionCount = exportTaskDeletionCount;
    }

    public boolean isExportTaskDivergent() {
        return exportTaskDivergent;
    }

    public void setExportTaskDivergent(boolean exportTaskDivergent) {
        this.exportTaskDivergent = exportTaskDivergent;
    }

    @Override
    public String toString() {
        return "FastJsonPurgeFinishedResult{" +
                "importTaskDeletionCount=" + importTaskDeletionCount +
                ", importTaskDivergent=" + importTaskDivergent +
                ", exportTaskDeletionCount=" + exportTaskDeletionCount +
                ", exportTaskDivergent=" + exportTaskDivergent +
                '}';
    }
}
