package com.dwarfeng.fileio.stack.service;

import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 支持 QoS 服务。
 *
 * @author DwArFeng
 * @since 1.9.0
 */
public interface SupportQosService extends Service {

    /**
     * 重置导出器支持。
     *
     * @throws ServiceException 服务异常。
     */
    void resetExporter() throws ServiceException;

    /**
     * 重置导入器支持。
     *
     * @throws ServiceException 服务异常。
     */
    void resetImporter() throws ServiceException;

    /**
     * 重置读取器支持。
     *
     * @throws ServiceException 服务异常。
     */
    void resetReader() throws ServiceException;

    /**
     * 重置写入器支持。
     *
     * @throws ServiceException 服务异常。
     */
    void resetWriter() throws ServiceException;
}
