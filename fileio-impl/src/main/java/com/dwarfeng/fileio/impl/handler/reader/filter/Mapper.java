package com.dwarfeng.fileio.impl.handler.reader.filter;

import java.util.Map;

/**
 * 映射器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface Mapper {

    /**
     * 构造一个映射器执行器。
     *
     * @return 映射器执行器。
     * @throws Exception 构造过程中发生的任何异常。
     */
    Executor newExecutor() throws Exception;

    /**
     * 映射器执行器。
     *
     * @author DwArFeng
     * @since 1.0.0
     */
    interface Executor {

        /**
         * 执行前调度。
         *
         * @throws Exception 方法执行过程中发生的任何异常。
         */
        void beforeExecute() throws Exception;

        /**
         * 执行调度。
         *
         * <p>
         * 该方法需要将指定的数据，按照映射器自身的规则进行映射，返回映射后的数据。
         *
         * @param data 指定的数据。
         * @return 执行调度后返回的数据。
         * @throws Exception 方法执行过程中发生的任何异常。
         */
        Map<String, Object> execute(Map<String, Object> data) throws Exception;

        /**
         * 执行后调度。
         *
         * @throws Exception 方法执行过程中发生的任何异常。
         */
        void afterExecute() throws Exception;
    }
}
