package com.dwarfeng.fileio.sdk.util;

import com.dwarfeng.subgrade.stack.exception.ServiceException;

/**
 * 服务异常代码。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public final class ServiceExceptionCodes {

    private static int EXCEPTION_CODE_OFFSET = 10000;

    public static final ServiceException.Code EXPORT_FILE_INFO_EXISTS =
            new ServiceException.Code(offset(0), "export file info exists");
    public static final ServiceException.Code EXPORT_FILE_INFO_NOT_EXISTS =
            new ServiceException.Code(offset(10), "export file info not exists");
    public static final ServiceException.Code EXPORT_TASK_NOT_EXISTS =
            new ServiceException.Code(offset(20), "export task not exists");
    public static final ServiceException.Code EXPORT_TASK_SETTING_NOT_EXISTS =
            new ServiceException.Code(offset(30), "export task setting not exists");
    public static final ServiceException.Code EXPORT_TEMPLATE_INFO_EXISTS =
            new ServiceException.Code(offset(40), "export template info exists");
    public static final ServiceException.Code EXPORT_TEMPLATE_INFO_NOT_EXISTS =
            new ServiceException.Code(offset(50), "export template info not exists");
    public static final ServiceException.Code IMPORT_FILE_INFO_EXISTS =
            new ServiceException.Code(offset(60), "import file info exists");
    public static final ServiceException.Code IMPORT_FILE_INFO_NOT_EXISTS =
            new ServiceException.Code(offset(70), "import file info not exists");
    public static final ServiceException.Code IMPORT_TASK_NOT_EXISTS =
            new ServiceException.Code(offset(80), "import task not exists");
    public static final ServiceException.Code IMPORT_TASK_SETTING_NOT_EXISTS =
            new ServiceException.Code(offset(90), "import task setting not exists");
    public static final ServiceException.Code IMPORT_TEMPLATE_INFO_EXISTS =
            new ServiceException.Code(offset(100), "import template info exists");
    public static final ServiceException.Code IMPORT_TEMPLATE_INFO_NOT_EXISTS =
            new ServiceException.Code(offset(110), "import template info not exists");
    public static final ServiceException.Code TASK_EXPIRE_DATE_NOT_REACHED =
            new ServiceException.Code(offset(170), "task expire date not reached");
    public static final ServiceException.Code TASK_STATUS_MISMATCH =
            new ServiceException.Code(offset(180), "task status mismatch");
    public static final ServiceException.Code TASK_DEAD_DATE_NOT_REACHED =
            new ServiceException.Code(offset(190), "task dead date not reached");
    public static final ServiceException.Code EXPORT_METADATA_NOT_EXISTS =
            new ServiceException.Code(offset(200), "export metadata not exists");
    public static final ServiceException.Code IMPORT_METADATA_NOT_EXISTS =
            new ServiceException.Code(offset(210), "import metadata not exists");

    private static int offset(int value) {
        return EXCEPTION_CODE_OFFSET + value;
    }

    /**
     * 获取异常代号的偏移量。
     *
     * @return 异常代号的偏移量。
     */
    public static int getExceptionCodeOffset() {
        return EXCEPTION_CODE_OFFSET;
    }

    /**
     * 设置异常代号的偏移量。
     *
     * @param exceptionCodeOffset 指定的异常代号的偏移量。
     */
    public static void setExceptionCodeOffset(int exceptionCodeOffset) {
        // 设置 EXCEPTION_CODE_OFFSET 的值。
        EXCEPTION_CODE_OFFSET = exceptionCodeOffset;

        // 以新的 EXCEPTION_CODE_OFFSET 为基准，更新异常代码的值。
        EXPORT_FILE_INFO_EXISTS.setCode(offset(0));
        EXPORT_FILE_INFO_NOT_EXISTS.setCode(offset(10));
        EXPORT_TASK_NOT_EXISTS.setCode(offset(20));
        EXPORT_TASK_SETTING_NOT_EXISTS.setCode(offset(30));
        EXPORT_TEMPLATE_INFO_EXISTS.setCode(offset(40));
        EXPORT_TEMPLATE_INFO_NOT_EXISTS.setCode(offset(50));
        IMPORT_FILE_INFO_EXISTS.setCode(offset(60));
        IMPORT_FILE_INFO_NOT_EXISTS.setCode(offset(70));
        IMPORT_TASK_NOT_EXISTS.setCode(offset(80));
        IMPORT_TASK_SETTING_NOT_EXISTS.setCode(offset(90));
        IMPORT_TEMPLATE_INFO_EXISTS.setCode(offset(100));
        IMPORT_TEMPLATE_INFO_NOT_EXISTS.setCode(offset(110));
        TASK_EXPIRE_DATE_NOT_REACHED.setCode(offset(170));
        TASK_STATUS_MISMATCH.setCode(offset(180));
        TASK_DEAD_DATE_NOT_REACHED.setCode(offset(190));
        EXPORT_METADATA_NOT_EXISTS.setCode(offset(200));
        IMPORT_METADATA_NOT_EXISTS.setCode(offset(210));
    }

    private ServiceExceptionCodes() {
        throw new IllegalStateException("禁止实例化");
    }
}
