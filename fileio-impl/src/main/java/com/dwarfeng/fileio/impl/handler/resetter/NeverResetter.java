package com.dwarfeng.fileio.impl.handler.resetter;

import org.springframework.stereotype.Component;

/**
 * 永远不执行重置的重置器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component
public class NeverResetter extends com.dwarfeng.fileio.sdk.handler.resetter.AbstractResetter {

    @Override
    protected void doStart() {
    }

    @Override
    protected void doStop() {
    }

    @Override
    public String toString() {
        return "NeverResetter{}";
    }
}
