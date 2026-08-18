package com.dwarfeng.fileio.impl.handler.importer.dct;

import com.dwarfeng.dct.stack.handler.ValueCodingHandler;
import com.dwarfeng.fileio.sdk.handler.importer.AbstractExecutor;
import com.dwarfeng.fileio.stack.struct.ImportResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * 基于 DCT 框架的导入器执行器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component("dctImporterRegistry.dctExecutor")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class DctExecutor extends AbstractExecutor {

    private static final Logger LOGGER = LoggerFactory.getLogger(DctExecutor.class);

    // 指定字符集为 UTF-8，以兼容不同操作系统。
    private static final Charset CHARSET = StandardCharsets.UTF_8;

    private final ValueCodingHandler valueCodingHandler;

    private final Config config;

    private Scanner temporaryFileScanner;

    public DctExecutor(ValueCodingHandler valueCodingHandler, Config config) {
        this.valueCodingHandler = valueCodingHandler;
        this.config = config;
    }

    @Override
    public Set<String> topicSpace() {
        return Collections.singleton(null);
    }

    @Override
    public void beforeExecute() throws Exception {
        // 申请临时文件。
        String temporaryFileIdentifier = UUID.randomUUID().toString();
        context.createTemporary(temporaryFileIdentifier);
        // 将目标文件复制到临时文件。
        context.copyFileToTemporary(config.getFileIdentifier(), temporaryFileIdentifier);
        // 并打开临时文件的读取器。
        temporaryFileScanner = new Scanner(
                context.openTemporaryInputStream(temporaryFileIdentifier), CHARSET.name()
        );
    }

    @Override
    public ImportResult execute(String topic) throws Exception {
        boolean hasNext = true;
        List<Map<String, Object>> datas = new ArrayList<>(config.getBatchSize());

        // 读取数据，直到达到批量大小或者读取到文件末尾。
        for (int i = 0; i < config.getBatchSize(); i++) {
            // 如果扫描器已经到达文件末尾，则跳出循环。
            if (!temporaryFileScanner.hasNextLine()) {
                hasNext = false;
                break;
            }
            String line = temporaryFileScanner.nextLine();
            Map<String, Object> data = parseLine(line);
            datas.add(data);
        }

        // 返回结果。
        return new ImportResult(hasNext, datas);
    }

    // 为了保持代码的可读性，屏蔽以下警告。
    @SuppressWarnings("UnusedAssignment")
    private Map<String, Object> parseLine(String line) throws Exception {
        // 定义返回结果。
        Map<String, Object> result = new HashMap<>();
        // 定义所需的变量。
        int fieldStartIndex = 0;
        int fieldEndIndex = 0;
        int valueStartIndex = 0;
        int valueEndIndex = 0;
        LineParsingState state = LineParsingState.FIELD_SEEKING;
        // 遍历 line 中的所有字符。
        int i = 0;
        do {
            // 特殊规则：第 0 个字符一定是引号，做特殊处理。
            if (i == 0) {
                fieldStartIndex = 1;
                state = LineParsingState.FIELD_SEEKING;
                i = 1;
            }
            char c = line.charAt(i);
            // 如果 c 不是引号，则跳过。
            if (c != '"') {
                i++;
                continue;
            }
            // 如果 c 是引号，但 c 前面的字符是转义符 \\，则跳过。
            if (line.charAt(i - 1) == '\\') {
                i++;
                continue;
            }
            // 如果 c 是引号，且 c 前面的字符不是转义符 \\，则根据 state 进行处理。
            if (Objects.equals(state, LineParsingState.FIELD_SEEKING)) {
                fieldEndIndex = i;
                // 字段的结尾到值的开头固定为 ":"，是 3 个字符。
                i += 3;
                valueStartIndex = i;
                state = LineParsingState.VALUE_SEEKING;
            } else if (Objects.equals(state, LineParsingState.VALUE_SEEKING)) {
                valueEndIndex = i;
                // 取出 field 和 value 子串，并还原转义符。
                String field = line.substring(fieldStartIndex, fieldEndIndex).replace("\\\"", "\"");
                String value = line.substring(valueStartIndex, valueEndIndex).replace("\\\"", "\"");
                // 反序列化 value。
                Object valueObject = valueCodingHandler.decode(value);
                // 将 field 和 value 添加到结果中。
                result.put(field, valueObject);
                // 值的结尾到下一个字段的开头固定为 ","，是 3 个字符。
                i += 3;
                fieldStartIndex = i;
                state = LineParsingState.FIELD_SEEKING;
            } else {
                LOGGER.warn("不应该执行到此处, 请联系开发人员");
                throw new IllegalStateException("state = " + state);
            }
        } while (i < line.length());
        // 返回结果。
        return result;
    }

    @Override
    public void afterExecute() {
        // 如果 temporaryFileScanner 不为 null，则关闭 temporaryFileScanner。
        if (Objects.nonNull(temporaryFileScanner)) {
            temporaryFileScanner.close();
        }
    }

    @Override
    public String toString() {
        return "DctExecutor{" +
                "valueCodingHandler=" + valueCodingHandler +
                ", config=" + config +
                ", temporaryFileScanner=" + temporaryFileScanner +
                ", context=" + context +
                '}';
    }

    private enum LineParsingState {
        FIELD_SEEKING, VALUE_SEEKING
    }
}
