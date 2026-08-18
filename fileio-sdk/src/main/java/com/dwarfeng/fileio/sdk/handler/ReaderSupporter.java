package com.dwarfeng.fileio.sdk.handler;

/**
 * 读取器支持。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface ReaderSupporter {

    /**
     * 提供类型。
     *
     * @return 类型。
     */
    String provideType();

    /**
     * 提供标签。
     *
     * @return 标签。
     */
    String provideLabel();

    /**
     * 提供描述。
     *
     * @return 描述。
     */
    String provideDescription();

    /**
     * 提供示例参数。
     *
     * @return 示例参数。
     */
    String provideExampleParam();

    /**
     * 提供示例元数据。
     *
     * @return 示例元数据。
     */
    String provideExampleMetadata();
}
