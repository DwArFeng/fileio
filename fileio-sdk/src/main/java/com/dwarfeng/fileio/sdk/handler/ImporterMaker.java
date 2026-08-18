package com.dwarfeng.fileio.sdk.handler;

import com.dwarfeng.fileio.stack.exception.ImporterException;
import com.dwarfeng.fileio.stack.handler.Importer;

/**
 * 导入器构造器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface ImporterMaker {

    /**
     * 返回构造器是否支持指定的类型。
     *
     * @param type 指定的类型。
     * @return 构造器是否支持指定的类型。
     */
    boolean supportType(String type);

    /**
     * 根据指定的导入器信息生成一个导入器对象。
     *
     * <p>
     * 可以保证传入的导入器信息中的类型是支持的。
     *
     * @param type  导入器类型。
     * @param param 导入器参数。
     * @return 构造的导入器。
     * @throws ImporterException 导入器异常。
     */
    Importer makeImporter(String type, String param) throws ImporterException;
}
