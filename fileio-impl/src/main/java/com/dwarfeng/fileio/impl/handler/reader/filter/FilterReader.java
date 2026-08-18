package com.dwarfeng.fileio.impl.handler.reader.filter;

import com.dwarfeng.fileio.impl.handler.reader.filter.Config.MapperMakerConfig;
import com.dwarfeng.fileio.impl.handler.reader.filter.Config.ReaderMakerConfig;
import com.dwarfeng.fileio.sdk.handler.ReaderMaker;
import com.dwarfeng.fileio.sdk.handler.reader.AbstractReader;
import com.dwarfeng.fileio.stack.handler.Reader;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * 过滤读取器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component("filterReaderRegistry.filterReader")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class FilterReader extends AbstractReader {

    private final ApplicationContext ctx;

    private final Config config;

    private Reader reader;
    private List<Mapper> mappers;

    public FilterReader(ApplicationContext ctx, Config config) {
        this.ctx = ctx;
        this.config = config;
    }

    @PostConstruct
    public void init() throws Exception {
        // 构造 reader;
        ReaderMakerConfig readerMakerConfig = config.getReaderMakerConfig();
        ReaderMaker readerMaker = ctx.getBean(readerMakerConfig.getBeanName(), ReaderMaker.class);
        reader = readerMaker.makeReader(readerMakerConfig.getType(), readerMakerConfig.getParam());
        // 构造 mappers。
        List<MapperMakerConfig> mapperMakerConfigs = config.getMapperMakerConfigs();
        mappers = new ArrayList<>();
        for (MapperMakerConfig mapperMakerConfig : mapperMakerConfigs) {
            MapperMaker mapperMaker = ctx.getBean(mapperMakerConfig.getBeanName(), MapperMaker.class);
            mappers.add(mapperMaker.makeMapper(mapperMakerConfig.getParam()));
        }
    }

    @Override
    protected Executor doNewExecutor() throws Exception {
        // 基于 reader 构造 readerExecutor。
        Reader.Executor readerExecutor = reader.newExecutor();
        // 基于 mappers 构造 mapperExecutors。
        List<Mapper.Executor> mapperExecutors = new ArrayList<>();
        for (Mapper mapper : mappers) {
            mapperExecutors.add(mapper.newExecutor());
        }
        // 构造 filterExecutor。
        return ctx.getBean(FilterExecutor.class, readerExecutor, mapperExecutors);
    }

    @Override
    public String toString() {
        return "FilterReader{" +
                "ctx=" + ctx +
                ", config=" + config +
                ", reader=" + reader +
                ", mappers=" + mappers +
                '}';
    }
}
