package com.dwarfeng.fileio.impl.handler.reader.filter;

import com.dwarfeng.fileio.stack.handler.Reader;
import com.dwarfeng.fileio.stack.struct.ReadResult;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 过滤读取器执行器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component("filterReaderRegistry.filterExecutor")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class FilterExecutor implements Reader.Executor {

    private final Reader.Executor readerExecutor;
    private final List<Mapper.Executor> mapperExecutors;

    public FilterExecutor(Reader.Executor readerExecutor, List<Mapper.Executor> mapperExecutors) {
        this.readerExecutor = readerExecutor;
        this.mapperExecutors = mapperExecutors;
    }

    @Override
    public void init(Reader.Context context) {
        readerExecutor.init(context);
    }

    @Override
    public Set<String> topicSpace() throws Exception {
        return readerExecutor.topicSpace();
    }

    @Override
    public void beforeExecute() throws Exception {
        readerExecutor.beforeExecute();
        for (Mapper.Executor mapperExecutor : mapperExecutors) {
            mapperExecutor.beforeExecute();
        }
    }

    @Override
    public ReadResult execute(String topic) throws Exception {
        ReadResult result = readerExecutor.execute(topic);
        List<Map<String, Object>> datas = new ArrayList<>();
        for (Map<String, Object> data : result.getDatas()) {
            Map<String, Object> filteredData = new HashMap<>(data);
            for (Mapper.Executor mapperExecutor : mapperExecutors) {
                filteredData = mapperExecutor.execute(filteredData);
            }
            datas.add(filteredData);
        }
        return new ReadResult(result.isHasNext(), datas);
    }

    @Override
    public void afterExecute() throws Exception {
        readerExecutor.afterExecute();
        for (Mapper.Executor mapperExecutor : mapperExecutors) {
            mapperExecutor.afterExecute();
        }
    }

    @Override
    public String toString() {
        return "FilterExecutor{" +
                "readerExecutor=" + readerExecutor +
                ", mapperExecutors=" + mapperExecutors +
                '}';
    }
}
