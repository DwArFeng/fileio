package com.dwarfeng.fileio.impl.handler.exporter.excel;

import com.dwarfeng.fileio.sdk.handler.exporter.AbstractExecutor;
import com.dwarfeng.fileio.stack.bean.dto.TemporaryToFileCopyInfo;
import com.dwarfeng.fileio.stack.struct.ReadResult;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Excel 导出器执行器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component("excelExporterRegistry.excelExecutor")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ExcelExecutor extends AbstractExecutor {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelExecutor.class);

    private final ApplicationContext ctx;

    private final Config config;

    private Workbook workbook;
    private Map<SheetKey, SheetModel> sheetModelMap;
    private Map<SheetKey, List<ColumnModel>> columnModelsMap;

    private boolean exceptionFlag = false;

    public ExcelExecutor(ApplicationContext ctx, Config config) {
        this.ctx = ctx;
        this.config = config;
    }

    @Override
    public void beforeExecute() throws Exception {
        try {
            // 展开参数。
            String templateIdentifier = config.getTemplateIdentifier();

            // 创建临时文件。
            String temporaryFileIdentifier = UUID.randomUUID().toString();
            context.createTemporary(temporaryFileIdentifier);

            // 将模板文件复制到临时文件。
            context.copyTemplateToTemporary(templateIdentifier, temporaryFileIdentifier);

            // 获取临时文件的输入流。
            // 此处不关闭输入流，输入流将会在 afterExecute 方法中关闭。
            InputStream in = context.openTemporaryInputStream(temporaryFileIdentifier);
            try {
                workbook = WorkbookFactory.create(in);
            } catch (Exception e) {
                // 如果抛出异常，则需要关闭输入流。
                LOGGER.warn("创建工作簿时发生异常, 将关闭输入流并抛出异常, 异常信息如下: ", e);
                in.close();
                throw e;
            }

            // 初始化工作簿模型以及列模型。
            sheetModelMap = new HashMap<>();
            columnModelsMap = new HashMap<>();
            for (Config.SheetSetting sheetSetting : config.getSheetSettings()) {
                initSingleSheet(sheetSetting);
            }
        } catch (Exception e) {
            LOGGER.warn("执行器执行 beforeExecute 方法时发生异常, 将置位 exceptionFlag 并抛出异常, 异常信息如下: ", e);
            exceptionFlag = true;
            throw e;
        }
    }

    private void initSingleSheet(Config.SheetSetting sheetSetting) {
        // 展开参数。
        String readerIdentifier = sheetSetting.getReaderIdentifier();
        String readerTopic = sheetSetting.getReaderTopic();
        int sheetIndex = sheetSetting.getSheetIndex();
        int firstDataRow = sheetSetting.getFirstDataRow();

        // 构造工作簿键。
        SheetKey sheetKey = new SheetKey(readerIdentifier, readerTopic);

        // 获取工作簿。
        Sheet sheet = workbook.getSheetAt(sheetIndex);

        // 初始化工作簿模型。
        SheetModel sheetModel = new SheetModel(sheet, firstDataRow);

        // 将工作簿模型放入工作簿模型映射。
        sheetModelMap.put(sheetKey, sheetModel);

        // 初始化列模型。
        for (Config.ColumnSetting columnSetting : sheetSetting.getColumnSettings()) {

            // 展开参数。
            String fieldName = columnSetting.getFieldName();
            String columnName = columnSetting.getColumnName();
            int columnType = columnSetting.getColumnType();
            String valueMapperBeanName = columnSetting.getValueMapperBeanName();

            // 将 columnName 转换为 columnIndex。
            int columnIndex = ExcelUtil.columnNameToColumnIndex(columnName);

            // 获取单元格样式。
            CellStyle cellStyle = sheet.getRow(firstDataRow).getCell(columnIndex).getCellStyle();

            // 获取值映射器。
            ValueMapper<?> valueMapper = ctx.getBean(valueMapperBeanName, ValueMapper.class);

            // 初始化列模型。
            ColumnModel columnModel = new ColumnModel(
                    fieldName, sheetModel, columnIndex, columnType, cellStyle, valueMapper
            );

            // 将列模型放入列模型映射。
            List<ColumnModel> columnModels = columnModelsMap.computeIfAbsent(sheetKey, k -> new ArrayList<>());
            columnModels.add(columnModel);
        }
    }

    @Override
    public void execute(String identifier, String topic, ReadResult readResult) throws Exception {
        try {
            // 遍历数据，对每一条数据进行处理。
            for (Map<String, Object> data : readResult.getDatas()) {
                // 获取工作簿模型。
                SheetKey sheetKey = new SheetKey(identifier, topic);
                SheetModel sheetModel = sheetModelMap.get(sheetKey);
                // 如果工作簿模型为空，则跳过。
                if (Objects.isNull(sheetModel)) {
                    continue;
                }
                // 写入单条数据。
                writeSingleData(identifier, topic, data);
                // 工作簿模型的当前行索引自增。
                sheetModel.increaseCurrentRowIndex();
            }
        } catch (Exception e) {
            LOGGER.warn("执行器执行 execute 方法时发生异常, 将置位 exceptionFlag 并抛出异常, 异常信息如下: ", e);
            exceptionFlag = true;
            throw e;
        }
    }

    private void writeSingleData(String identifier, String topic, Map<String, Object> data) throws Exception {
        // 获取列模型。
        SheetKey sheetKey = new SheetKey(identifier, topic);
        List<ColumnModel> columnModels = columnModelsMap.get(sheetKey);
        // 遍历列模型的列表，对每一列进行处理。
        for (ColumnModel columnModel : columnModels) {
            // 获取字段的键和字段值。
            Object value = data.get(columnModel.getField());
            // 写入值。
            columnModel.writeValue(value);
            // 应用样式。
            columnModel.applyStyle();
        }
    }

    @Override
    public void afterExecute() throws Exception {
        // 如果 workbook 不为空且没有发生异常，则将 workbook 保存为文件。
        if (Objects.nonNull(workbook) && !exceptionFlag) {
            // 创建临时文件。
            String temporaryFileIdentifier = UUID.randomUUID().toString();
            context.createTemporary(temporaryFileIdentifier);
            // 将 workbook 写入临时文件。
            try (OutputStream outputStream = context.openTemporaryOutputStream(temporaryFileIdentifier)) {
                workbook.write(outputStream);
            }
            // 将临时文件拷贝至文件存储。
            String fileIdentifier = config.getFileIdentifier();
            String fileName = config.getFileName();
            context.copyTemporaryToFile(new TemporaryToFileCopyInfo(
                    temporaryFileIdentifier, fileIdentifier, fileName
            ));
        }

        // 如果 workbook 不为空，则关闭 workbook。
        if (Objects.nonNull(workbook)) {
            workbook.close();
        }
    }

    @Override
    public String toString() {
        return "ExcelExecutor{" +
                "config=" + config +
                ", ctx=" + ctx +
                ", workbook=" + workbook +
                ", sheetModelMap=" + sheetModelMap +
                ", columnModelsMap=" + columnModelsMap +
                ", exceptionFlag=" + exceptionFlag +
                ", context=" + context +
                '}';
    }

    private static final class SheetKey {

        private final String identifier;
        private final String topic;

        public SheetKey(String identifier, String topic) {
            this.identifier = identifier;
            this.topic = topic;
        }

        public String getIdentifier() {
            return identifier;
        }

        public String getTopic() {
            return topic;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            SheetKey sheetKey = (SheetKey) o;
            return Objects.equals(identifier, sheetKey.identifier) && Objects.equals(topic, sheetKey.topic);
        }

        @Override
        public int hashCode() {
            int result = Objects.hashCode(identifier);
            result = 31 * result + Objects.hashCode(topic);
            return result;
        }

        @Override
        public String toString() {
            return "SheetKey{" +
                    "identifier='" + identifier + '\'' +
                    ", topic='" + topic + '\'' +
                    '}';
        }
    }

    private static final class SheetModel {

        private final Sheet sheet;
        private int rowIndex;

        public SheetModel(Sheet sheet, int rowIndex) {
            this.sheet = sheet;
            this.rowIndex = rowIndex;
        }

        public Row currentRow() {
            return CellUtil.getRow(rowIndex, sheet);
        }

        public void increaseCurrentRowIndex() {
            rowIndex++;
        }
    }

    private static final class ColumnModel {

        private final String field;
        private final SheetModel sheetModel;
        private final int columnIndex;
        private final int columnType;
        private final CellStyle cellStyle;
        private final ValueMapper<?> valueMapper;

        private ColumnModel(
                String field, SheetModel sheetModel, int columnIndex, int columnType, CellStyle cellStyle,
                ValueMapper<?> valueMapper
        ) {
            this.field = field;
            this.sheetModel = sheetModel;
            this.columnIndex = columnIndex;
            this.columnType = columnType;
            this.cellStyle = cellStyle;
            this.valueMapper = valueMapper;
        }

        public String getField() {
            return field;
        }

        public void writeValue(Object value) throws Exception {
            Cell cell = currentCell();
            switch (columnType) {
                case Constants.COLUMN_TYPE_BOOLEAN:
                    cell.setCellValue((Boolean) valueMapper.map(value));
                    break;
                case Constants.COLUMN_TYPE_DATE:
                    cell.setCellValue((Date) valueMapper.map(value));
                    break;
                case Constants.COLUMN_TYPE_DOUBLE:
                    cell.setCellValue((Double) valueMapper.map(value));
                    break;
                case Constants.COLUMN_TYPE_LOCAL_DATE:
                    cell.setCellValue((LocalDate) valueMapper.map(value));
                    break;
                case Constants.COLUMN_TYPE_LOCAL_DATE_TIME:
                    cell.setCellValue((LocalDateTime) valueMapper.map(value));
                    break;
                case Constants.COLUMN_TYPE_STRING:
                    cell.setCellValue((String) valueMapper.map(value));
                    break;
                default:
                    throw new IllegalArgumentException("未知的列类型: " + columnType);
            }
        }

        public void applyStyle() {
            currentCell().setCellStyle(cellStyle);
        }

        private Cell currentCell() {
            return CellUtil.getCell(sheetModel.currentRow(), columnIndex);
        }
    }
}
