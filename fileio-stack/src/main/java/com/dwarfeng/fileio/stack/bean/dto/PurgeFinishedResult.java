package com.dwarfeng.fileio.stack.bean.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;

/**
 * 清除完成结果。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class PurgeFinishedResult implements Dto {

    private static final long serialVersionUID = -3053293633749989825L;

    private int importTaskDeletionCount;
    private boolean importTaskDivergent;
    private int exportTaskDeletionCount;
    private boolean exportTaskDivergent;

    public PurgeFinishedResult() {
    }

    public PurgeFinishedResult(
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
        return "PurgeFinishedResult{" +
                "importTaskDeletionCount=" + importTaskDeletionCount +
                ", importTaskDivergent=" + importTaskDivergent +
                ", exportTaskDeletionCount=" + exportTaskDeletionCount +
                ", exportTaskDivergent=" + exportTaskDivergent +
                '}';
    }
}
