package com.dwarfeng.fileio.impl.handler;

import com.dwarfeng.fileio.sdk.handler.ReaderMaker;
import com.dwarfeng.fileio.stack.exception.ReaderException;
import com.dwarfeng.fileio.stack.exception.UnsupportedReaderTypeException;
import com.dwarfeng.fileio.stack.handler.Reader;
import com.dwarfeng.fileio.stack.handler.ReaderHandler;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class ReaderHandlerImpl implements ReaderHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReaderHandlerImpl.class);

    private final List<ReaderMaker> readerMakers;

    public ReaderHandlerImpl(List<ReaderMaker> readerMakers) {
        this.readerMakers = Optional.ofNullable(readerMakers).orElse(Collections.emptyList());
    }

    @Override
    public Reader make(String type, String param) throws HandlerException {
        try {
            // 生成读取器。
            LOGGER.debug("通过读取器信息构建新的的读取器...");
            ReaderMaker readerMaker = readerMakers.stream().filter(maker -> maker.supportType(type))
                    .findFirst().orElseThrow(() -> new UnsupportedReaderTypeException(type));
            Reader reader = readerMaker.makeReader(type, param);
            LOGGER.debug("读取器构建成功!");
            LOGGER.debug("读取器: {}", reader);
            return reader;
        } catch (ReaderException e) {
            throw e;
        } catch (Exception e) {
            throw new ReaderException(e);
        }
    }
}
