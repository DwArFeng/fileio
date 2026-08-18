package com.dwarfeng.fileio.impl.handler.reader.filter;

/**
 * 映射器构造器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface MapperMaker {

    /**
     * 根据指定的参数生成一个映射器对象。
     *
     * @param param 指定的参数。
     * @return 构造的映射器。
     * @throws Exception 构造过程中发生的任何异常。
     */
    Mapper makeMapper(String param) throws Exception;
}
