package com.dwarfeng.fileio.impl.handler.exporter.csv.vs;

import com.dwarfeng.dct.stack.handler.ValueCodingHandler;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * 基于 DCT 的值序列化器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class DctValueSerializer extends AbstractValueSerializer {

    public static final String VALUE_SERIALIZER_NAME = "dct";

    private final ValueCodingHandler valueCodingHandler;

    public DctValueSerializer(
            String name,
            @Qualifier("csvExporterRegistry.dctValueSerialize.valueCodingHandler")
            ValueCodingHandler valueCodingHandler
    ) {
        super(name);
        this.valueCodingHandler = valueCodingHandler;
    }

    @Override
    public String serialize(Object value) throws Exception {
        return valueCodingHandler.encode(value);
    }

}
