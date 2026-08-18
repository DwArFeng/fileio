package com.dwarfeng.fileio.impl.handler.reader.spls.ar;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.parser.ParserConfig;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Fastjson 参数解析器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class FastJsonArgsResolver extends AbstractArgsResolver {

    @SuppressWarnings({"SpellCheckingInspection", "RedundantSuppression"})
    public static final String ARGS_RESOLVER_NAME = "fastjson";

    @Value("${com.dwarfeng.fileio.reader.spls.args_resolver.fastjson.auto_type_accepts}")
    private String autoTypeAcceptsString;

    private ParserConfig parserConfig;

    public FastJsonArgsResolver() {
        super(ARGS_RESOLVER_NAME);
    }

    @PostConstruct
    public void init() {
        // 解析 autoTypeAccepts。
        String[] autoTypeAccepts = autoTypeAcceptsString.trim().split(",");

        // 构造 ParserConfig。
        parserConfig = new ParserConfig();
        parserConfig.setAutoTypeSupport(true);
        for (String autoTypeAccept : autoTypeAccepts) {
            parserConfig.addAccept(autoTypeAccept);
        }
    }

    @Override
    public Object[] resolve(String value) {
        // 使用 fastjson 解析 value。
        List<Object> objectList = JSONArray.parseArray(value, Object.class, parserConfig);
        return objectList.toArray(new Object[0]);
    }

    @Override
    public String toString() {
        return "FastJsonArgsResolver{" +
                "autoTypeAcceptsString='" + autoTypeAcceptsString + '\'' +
                ", parserConfig=" + parserConfig +
                ", name='" + name + '\'' +
                '}';
    }
}
