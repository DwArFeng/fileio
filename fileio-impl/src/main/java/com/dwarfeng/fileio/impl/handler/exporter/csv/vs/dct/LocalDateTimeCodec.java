package com.dwarfeng.fileio.impl.handler.exporter.csv.vs.dct;

import com.dwarfeng.dct.sdk.handler.vc.AbstractValueCodec;
import com.dwarfeng.dct.sdk.util.Constants;

import javax.annotation.Nonnull;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * LocalDateTime 值编解码器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class LocalDateTimeCodec extends AbstractValueCodec {

    public LocalDateTimeCodec() {
        super(LocalDateTime.class, "local-date-time", Constants.VALUE_CODEC_PRIORITY_NORMAL);
    }

    @Nonnull
    @Override
    protected String doEncode(@Nonnull Object target) {
        LocalDateTime localDateTime = (LocalDateTime) target;
        Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
        long timestamp = instant.toEpochMilli();
        return String.valueOf(timestamp);
    }

    @Nonnull
    @Override
    protected Object doDecode(@Nonnull String text) {
        long timestamp = Long.parseLong(text);
        Instant instant = Instant.ofEpochMilli(timestamp);
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }
}
