package com.dwarfeng.fileio.impl.handler.reader.filter;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.subgrade.stack.bean.Bean;

import java.util.List;

/**
 * 配置。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class Config implements Bean {

    private static final long serialVersionUID = -7608199121759987878L;

    @JSONField(name = "#reader_maker_config", ordinal = 1, deserialize = false)
    private String readerMakerConfigRem = "读取器构造器配置，用于构造读取器。";

    @JSONField(name = "reader_maker_config", ordinal = 2)
    private ReaderMakerConfig readerMakerConfig;

    @JSONField(name = "#mapper_maker_configs", ordinal = 3, deserialize = false)
    private String mapperMakerConfigsRem = "映射器构造器配置列表，用于构造映射器列表。";

    @JSONField(name = "mapper_maker_configs", ordinal = 4)
    private List<MapperMakerConfig> mapperMakerConfigs;

    public Config() {
    }

    public Config(ReaderMakerConfig readerMakerConfig, List<MapperMakerConfig> mapperMakerConfigs) {
        this.readerMakerConfig = readerMakerConfig;
        this.mapperMakerConfigs = mapperMakerConfigs;
    }

    public String getReaderMakerConfigRem() {
        return readerMakerConfigRem;
    }

    public void setReaderMakerConfigRem(String readerMakerConfigRem) {
        this.readerMakerConfigRem = readerMakerConfigRem;
    }

    public ReaderMakerConfig getReaderMakerConfig() {
        return readerMakerConfig;
    }

    public void setReaderMakerConfig(ReaderMakerConfig readerMakerConfig) {
        this.readerMakerConfig = readerMakerConfig;
    }

    public String getMapperMakerConfigsRem() {
        return mapperMakerConfigsRem;
    }

    public void setMapperMakerConfigsRem(String mapperMakerConfigsRem) {
        this.mapperMakerConfigsRem = mapperMakerConfigsRem;
    }

    public List<MapperMakerConfig> getMapperMakerConfigs() {
        return mapperMakerConfigs;
    }

    public void setMapperMakerConfigs(List<MapperMakerConfig> mapperMakerConfigs) {
        this.mapperMakerConfigs = mapperMakerConfigs;
    }

    @Override
    public String toString() {
        return "Config{" +
                "readerMakerConfigRem='" + readerMakerConfigRem + '\'' +
                ", readerMakerConfig=" + readerMakerConfig +
                ", mapperMakerConfigsRem='" + mapperMakerConfigsRem + '\'' +
                ", mapperMakerConfigs=" + mapperMakerConfigs +
                '}';
    }

    public static class ReaderMakerConfig implements Bean {

        private static final long serialVersionUID = 4057748072343264795L;

        @JSONField(name = "#bean_name", ordinal = 1, deserialize = false)
        private String beanNameRem = "读取器构造器的 bean 的名称。";

        @JSONField(name = "bean_name", ordinal = 2)
        private String beanName;

        @JSONField(name = "#type", ordinal = 3, deserialize = false)
        private String typeRem = "读取器构造器构造读取器时使用的类型。";

        @JSONField(name = "type", ordinal = 4)
        private String type;

        @JSONField(name = "#param", ordinal = 5, deserialize = false)
        private String paramRem = "读取器构造器构造读取器时使用的参数。";

        @JSONField(name = "param", ordinal = 6)
        private String param;

        public ReaderMakerConfig() {
        }

        public ReaderMakerConfig(String beanName, String type, String param) {
            this.beanName = beanName;
            this.type = type;
            this.param = param;
        }

        public String getBeanNameRem() {
            return beanNameRem;
        }

        public void setBeanNameRem(String beanNameRem) {
            this.beanNameRem = beanNameRem;
        }

        public String getBeanName() {
            return beanName;
        }

        public void setBeanName(String beanName) {
            this.beanName = beanName;
        }

        public String getTypeRem() {
            return typeRem;
        }

        public void setTypeRem(String typeRem) {
            this.typeRem = typeRem;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getParamRem() {
            return paramRem;
        }

        public void setParamRem(String paramRem) {
            this.paramRem = paramRem;
        }

        public String getParam() {
            return param;
        }

        public void setParam(String param) {
            this.param = param;
        }

        @Override
        public String toString() {
            return "ReaderMakerConfig{" +
                    "beanNameRem='" + beanNameRem + '\'' +
                    ", beanName='" + beanName + '\'' +
                    ", typeRem='" + typeRem + '\'' +
                    ", type='" + type + '\'' +
                    ", paramRem='" + paramRem + '\'' +
                    ", param='" + param + '\'' +
                    '}';
        }
    }

    public static class MapperMakerConfig implements Bean {

        private static final long serialVersionUID = -460526932280702125L;

        @JSONField(name = "#bean_name", ordinal = 1, deserialize = false)
        private String beanNameRem = "映射器构造器的 bean 的名称。";

        @JSONField(name = "bean_name", ordinal = 2)
        private String beanName;

        @JSONField(name = "#param", ordinal = 3, deserialize = false)
        private String paramRem = "映射器构造器构造映射器时使用的参数。";

        @JSONField(name = "param", ordinal = 4)
        private String param;

        public MapperMakerConfig() {
        }

        public MapperMakerConfig(String beanName, String param) {
            this.beanName = beanName;
            this.param = param;
        }

        public String getBeanNameRem() {
            return beanNameRem;
        }

        public void setBeanNameRem(String beanNameRem) {
            this.beanNameRem = beanNameRem;
        }

        public String getBeanName() {
            return beanName;
        }

        public void setBeanName(String beanName) {
            this.beanName = beanName;
        }

        public String getParamRem() {
            return paramRem;
        }

        public void setParamRem(String paramRem) {
            this.paramRem = paramRem;
        }

        public String getParam() {
            return param;
        }

        public void setParam(String param) {
            this.param = param;
        }

        @Override
        public String toString() {
            return "MapperMakerConfig{" +
                    "beanNameRem='" + beanNameRem + '\'' +
                    ", beanName='" + beanName + '\'' +
                    ", paramRem='" + paramRem + '\'' +
                    ", param='" + param + '\'' +
                    '}';
        }
    }
}
