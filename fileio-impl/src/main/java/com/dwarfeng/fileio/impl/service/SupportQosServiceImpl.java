package com.dwarfeng.fileio.impl.service;

import com.dwarfeng.fileio.stack.handler.SupportHandler;
import com.dwarfeng.fileio.stack.service.SupportQosService;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.springframework.stereotype.Service;

@Service
public class SupportQosServiceImpl implements SupportQosService {

    private final SupportHandler supportHandler;

    private final ServiceExceptionMapper sem;

    public SupportQosServiceImpl(SupportHandler supportHandler, ServiceExceptionMapper sem) {
        this.supportHandler = supportHandler;
        this.sem = sem;
    }

    @Override
    public void resetExporter() throws ServiceException {
        try {
            supportHandler.resetExporter();
        } catch (HandlerException e) {
            throw ServiceExceptionHelper.logParse("重置导出器时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public void resetImporter() throws ServiceException {
        try {
            supportHandler.resetImporter();
        } catch (HandlerException e) {
            throw ServiceExceptionHelper.logParse("重置导入器时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public void resetReader() throws ServiceException {
        try {
            supportHandler.resetReader();
        } catch (HandlerException e) {
            throw ServiceExceptionHelper.logParse("重置读取器时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public void resetWriter() throws ServiceException {
        try {
            supportHandler.resetWriter();
        } catch (HandlerException e) {
            throw ServiceExceptionHelper.logParse("重置写入器时发生异常", LogLevel.WARN, e, sem);
        }
    }
}
