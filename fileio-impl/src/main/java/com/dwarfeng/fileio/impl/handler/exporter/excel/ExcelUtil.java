package com.dwarfeng.fileio.impl.handler.exporter.excel;

import org.apache.commons.lang3.StringUtils;

/**
 * Excel 工具类。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public final class ExcelUtil {

    /**
     * 将列索引转换为列名。
     *
     * @param columnIndex 列索引。
     * @return 列名。
     */
    public static String columnIndexToColumnName(int columnIndex) {
        // 校验 columnIndex 是否合法。
        if (columnIndex < 0) {
            throw new IllegalArgumentException("columnIndex 不能小于 0");
        }
        // 执行转换。
        StringBuilder columnName = new StringBuilder();
        while (columnIndex >= 0) {
            columnName.insert(0, (char) (columnIndex % 26 + 'A'));
            columnIndex = (columnIndex / 26) - 1;
        }
        return columnName.toString();
    }

    /**
     * 将列名转换为列索引。
     *
     * @param columnName 列名。
     * @return 列索引。
     */
    public static int columnNameToColumnIndex(String columnName) {
        // 校验 columnName 是否合法。
        if (StringUtils.isBlank(columnName)) {
            throw new IllegalArgumentException("columnName 不能为空");
        }
        for (int i = 0; i < columnName.length(); i++) {
            if (columnName.charAt(i) < 'A' || columnName.charAt(i) > 'Z') {
                throw new IllegalArgumentException("columnName 不合法");
            }
        }
        // 执行转换。
        int index = 0;
        for (int i = 0; i < columnName.length(); i++) {
            index *= 26;
            index += columnName.charAt(i) - 'A';
        }
        return index;
    }

    private ExcelUtil() {
        throw new IllegalStateException("禁止实例化");
    }
}
