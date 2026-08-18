package com.dwarfeng.fileio.impl.handler;

import com.dwarfeng.dct.impl.handler.ValueCodingHandlerImpl;
import com.dwarfeng.dct.stack.handler.ValueCodec;
import com.dwarfeng.dct.stack.handler.ValueCodingHandler;
import com.dwarfeng.dct.stack.struct.ValueCodingConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

/**
 * DCT 值编码处理器工具类。
 *
 * <p>
 * 该类通过静态方法将值编解码器集合组装为值编码处理器，供可选 Spring XML 配置调用。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public final class DctValueCodingHandlerUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(DctValueCodingHandlerUtil.class);

    /**
     * 根据指定的值编解码器集合生成值编码处理器。
     *
     * @param valueCodecs 值编解码器集合。
     * @return 新生成的值编码处理器。
     */
    public static ValueCodingHandler newValueCodingHandler(List<ValueCodec> valueCodecs) {
        LOGGER.info("生成 ValueCodingHandler...");
        ValueCodingConfig config = new ValueCodingConfig.Builder()
                .addCodecs(valueCodecs)
                .addPreCacheClasses(
                        valueCodecs.stream().map(ValueCodec::getTargetClass).collect(Collectors.toList())
                )
                .addPreCachePrefixes(
                        valueCodecs.stream().map(ValueCodec::getValuePrefix).collect(Collectors.toList())
                )
                .build();
        LOGGER.debug("ValueCodingHandler 生成完成");
        return new ValueCodingHandlerImpl(config);
    }

    private DctValueCodingHandlerUtil() {
        throw new IllegalStateException("禁止实例化");
    }
}
