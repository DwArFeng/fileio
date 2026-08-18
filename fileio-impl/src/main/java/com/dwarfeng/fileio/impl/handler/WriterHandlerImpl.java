package com.dwarfeng.fileio.impl.handler;

import com.dwarfeng.fileio.sdk.handler.WriterMaker;
import com.dwarfeng.fileio.stack.exception.UnsupportedWriterTypeException;
import com.dwarfeng.fileio.stack.exception.WriterException;
import com.dwarfeng.fileio.stack.handler.Writer;
import com.dwarfeng.fileio.stack.handler.WriterHandler;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class WriterHandlerImpl implements WriterHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(WriterHandlerImpl.class);

    private final List<WriterMaker> writerMakers;

    public WriterHandlerImpl(List<WriterMaker> writerMakers) {
        this.writerMakers = Optional.ofNullable(writerMakers).orElse(Collections.emptyList());
    }

    @Override
    public Writer make(String type, String param) throws HandlerException {
        try {
            // 生成写入器。
            LOGGER.debug("通过写入器信息构建新的的写入器...");
            WriterMaker writerMaker = writerMakers.stream().filter(maker -> maker.supportType(type))
                    .findFirst().orElseThrow(() -> new UnsupportedWriterTypeException(type));
            Writer writer = writerMaker.makeWriter(type, param);
            LOGGER.debug("写入器构建成功!");
            LOGGER.debug("写入器: {}", writer);
            return writer;
        } catch (WriterException e) {
            throw e;
        } catch (Exception e) {
            throw new WriterException(e);
        }
    }
}
