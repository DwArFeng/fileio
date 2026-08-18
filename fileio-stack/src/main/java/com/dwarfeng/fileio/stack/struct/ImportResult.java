package com.dwarfeng.fileio.stack.struct;

import java.util.List;
import java.util.Map;

/**
 * 导入结果。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public final class ImportResult {

    private final boolean hasNext;
    private final List<Map<String, Object>> datas;

    public ImportResult(boolean hasNext, List<Map<String, Object>> datas) {
        this.hasNext = hasNext;
        this.datas = datas;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public List<Map<String, Object>> getDatas() {
        return datas;
    }

    @Override
    public String toString() {
        return "ImportResult{" +
                "hasNext=" + hasNext +
                ", datas=" + datas +
                '}';
    }
}
