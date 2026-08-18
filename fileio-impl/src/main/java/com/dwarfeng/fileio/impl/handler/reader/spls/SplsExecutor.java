package com.dwarfeng.fileio.impl.handler.reader.spls;

import com.alibaba.fastjson.JSON;
import com.dwarfeng.fileio.sdk.handler.reader.AbstractExecutor;
import com.dwarfeng.fileio.stack.bean.entity.ExportMetadata;
import com.dwarfeng.fileio.stack.struct.ReadResult;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.entity.Entity;
import com.dwarfeng.subgrade.stack.service.PresetLookupService;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Subgrade PresetLookupService 读取器执行器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component("splsReaderRegistry.splsExecutor")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class SplsExecutor extends AbstractExecutor {

    private final ApplicationContext ctx;

    private final Config config;

    private PresetLookupService<? extends Entity<?>> presetLookupService;

    private int maxCount;
    private String preset;
    private Object[] args;

    private int readPage = 0;
    private int readCount = 0;

    public SplsExecutor(ApplicationContext ctx, Config config) {
        this.ctx = ctx;
        this.config = config;
    }

    @Override
    public Set<String> topicSpace() {
        return Collections.singleton(null);
    }

    // 使用泛化手段处理 presetLookupService 返回的数据，因此不需要考虑泛型的问题。
    @SuppressWarnings("unchecked")
    @Override
    public void beforeExecute() throws Exception {
        // 展开参数。
        String presetLookupServiceBeanName = config.getPresetLookupServiceBeanName();
        String metadataIdentifier = config.getMetadataIdentifier();
        String argsResolverBeanName = config.getArgsResolverBeanName();

        // 基于配置初始化参数。
        maxCount = config.getMaxCount();
        // 如果 maxCount 小于等于 0，则不限制导出数量。
        if (maxCount <= 0) {
            // Integer.MAX_VALUE 在该场景下可以表示无穷大。
            maxCount = Integer.MAX_VALUE;
        }

        // 从 Spring 容器中获取 Bean。
        presetLookupService = ctx.getBean(presetLookupServiceBeanName, PresetLookupService.class);

        // 读取元数据。
        ExportMetadata exportMetadata = context.getMetadata(metadataIdentifier);
        Metadata metadata = JSON.parseObject(exportMetadata.getContent(), Metadata.class);

        // 基于元数据初始化参数。
        preset = metadata.getPreset();
        ArgsResolver argsResolver = ctx.getBean(argsResolverBeanName, ArgsResolver.class);
        args = argsResolver.resolve(metadata.getArgsString());
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public ReadResult execute(String topic) throws Exception {
        // 展开参数。
        int batchSize = config.getBatchSize();

        // 如果已经读取了足够的数据，则不再继续读取。
        if (readCount >= maxCount) {
            return new ReadResult(false, Collections.emptyList());
        }

        // 定义结果变量。
        boolean hasNext = true;
        List<Map<String, Object>> datas = new ArrayList<>(batchSize);

        // 读取本页数据。
        List<? extends Entity<?>> entities = presetLookupService.lookupAsList(
                preset, args, new PagingInfo(readPage, batchSize)
        );

        // 如果实体列表为空，则表示没有数据，直接返回。
        if (entities.isEmpty()) {
            return new ReadResult(false, Collections.emptyList());
        }

        // 如果实体的长度小于 batchSize，则表示没有更多数据，将 hasNext 置为 false。
        if (entities.size() < batchSize) {
            hasNext = false;
        }

        // 如果实体的长度加上已经读取的长度大于最大长度，则截取实体，并将 hasNext 置为 false。
        if (entities.size() + readCount > maxCount) {
            hasNext = false;
            entities = entities.subList(0, maxCount - readCount);
        }

        // 遍历数据，将每条数据转换为 Map<String, Object>，并加入到 datas 中。
        for (Entity<?> entity : entities) {
            datas.add(parseSingleEntity(entity));
        }

        // 如果 hasNext 为 false，则表示没有更多数据，不需要更新计数器，直接返回结果。
        if (!hasNext) {
            return new ReadResult(false, datas);
        }

        // 更新计数器。
        readPage++;
        readCount += entities.size();

        // 返回结果。
        return new ReadResult(true, datas);
    }

    @SuppressWarnings("DuplicatedCode")
    private Map<String, Object> parseSingleEntity(Entity<?> entity) throws Exception {
        // 展开参数。
        List<Config.FieldSetting> fieldSettings = config.getFieldSettings();

        // 定义结果变量。
        Map<String, Object> data = new HashMap<>();

        // 遍历字段设置，将字段设置中的字段名和实体中的字段值对应起来。
        for (Config.FieldSetting fieldSetting : fieldSettings) {
            String entityFieldExpression = fieldSetting.getEntityFieldExpression();
            String fieldName = fieldSetting.getFieldName();

            // 使用 apache-beanutils 的表达式语言，从实体中获取字段值。
            Object value = BeanUtilsBean.getInstance().getPropertyUtils().getProperty(
                    entity, entityFieldExpression
            );
            data.put(fieldName, value);
        }

        // 返回结果。
        return data;
    }

    @Override
    public void afterExecute() {
    }

    @Override
    public String toString() {
        return "SplsExecutor{" +
                "ctx=" + ctx +
                ", config=" + config +
                ", presetLookupService=" + presetLookupService +
                ", maxCount=" + maxCount +
                ", preset='" + preset + '\'' +
                ", args=" + Arrays.toString(args) +
                ", readPage=" + readPage +
                ", readCount=" + readCount +
                ", context=" + context +
                '}';
    }
}
