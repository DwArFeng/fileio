package com.dwarfeng.fileio.sdk.handler;

import com.dwarfeng.fileio.stack.exception.ExporterException;
import com.dwarfeng.fileio.stack.handler.Exporter;

/**
 * 导出器构造器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface ExporterMaker {

    /**
     * 返回构造器是否支持指定的类型。
     *
     * @param type 指定的类型。
     * @return 构造器是否支持指定的类型。
     */
    boolean supportType(String type);

    /**
     * 根据指定的导出器信息生成一个导出器对象。
     *
     * <p>
     * 可以保证传入的导出器信息中的类型是支持的。
     *
     * @param type  导出器类型。
     * @param param 导出器参数。
     * @return 构造的导出器。
     * @throws ExporterException 导出器异常。
     */
    Exporter makeExporter(String type, String param) throws ExporterException;
}
