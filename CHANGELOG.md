# ChangeLog

## Release_1.0.0_20260816_build_A

### 功能构建

- 完成 `fileio-distribute` 模块，打包测试通过。

- 新增 telqos 指令。
  - com.dwarfeng.fileio.impl.service.telqos.ExportCommand。
  - com.dwarfeng.fileio.impl.service.telqos.ExportTemplateCommand。
  - com.dwarfeng.fileio.impl.service.telqos.ImportCommand。
  - com.dwarfeng.fileio.impl.service.telqos.ImportTemplateCommand。
  - com.dwarfeng.fileio.impl.service.telqos.PurgeCommand。
  - com.dwarfeng.fileio.impl.service.telqos.ResetCommand。
  - com.dwarfeng.fileio.impl.service.telqos.SupportCommand。

- 实现核心服务。
  - com.dwarfeng.fileio.stack.service.ImportService。
  - com.dwarfeng.fileio.stack.service.ExportService。

- 实现预设推送器。
  - com.dwarfeng.fileio.impl.handler.pusher.DrainPusher。
  - com.dwarfeng.fileio.impl.handler.pusher.LogPusher。
  - com.dwarfeng.fileio.impl.handler.pusher.MultiPusher。
  - com.dwarfeng.fileio.impl.handler.pusher.NativeKafkaPusher。

- 实现预设重置器。
  - com.dwarfeng.fileio.impl.handler.resetter.CronResetter。
  - com.dwarfeng.fileio.impl.handler.resetter.DubboResetter。
  - com.dwarfeng.fileio.impl.handler.resetter.FixedDelayResetter。
  - com.dwarfeng.fileio.impl.handler.resetter.FixedRateResetter。
  - com.dwarfeng.fileio.impl.handler.resetter.NeverResetter。

- 实现预设写入器。
  - com.dwarfeng.fileio.impl.handler.writer.log.LogWriterRegistry。

- 实现预设读取器。
  - com.dwarfeng.fileio.impl.handler.reader.filter.FilterReaderRegistry。
  - com.dwarfeng.fileio.impl.handler.reader.jdbc.JdbcReaderRegistry。
  - com.dwarfeng.fileio.impl.handler.reader.mock.MockReaderRegistry。
  - com.dwarfeng.fileio.impl.handler.reader.sels.SelsReaderRegistry。
  - com.dwarfeng.fileio.impl.handler.reader.spls.SplsReaderRegistry。

- 实现预设导出器。
  - com.dwarfeng.fileio.impl.handler.exporter.csv.CsvExporterRegistry。
  - com.dwarfeng.fileio.impl.handler.exporter.dct.DctExporterRegistry。
  - com.dwarfeng.fileio.impl.handler.exporter.excel.ExcelExporterRegistry。
  - com.dwarfeng.fileio.impl.handler.exporter.log.LogExporterRegistry。

- 实现预设导入器。
  - com.dwarfeng.fileio.impl.handler.importer.dct.DctImporterRegistry。

- 实现核心机制。
  - 推送机制。
  - 重置机制。
  - 任务检查机制。
  - 导入机制。
  - 导出机制。
  - 读取机制。
  - 写入机制。

- 增加操作服务。
  - com.dwarfeng.fileio.stack.service.ExportTemplateOperateService。
  - com.dwarfeng.fileio.stack.service.ImportTemplateOperateService。

- 完成 `fileio-node-all-he` 模块，启动测试通过。

- 建立实体以及维护服务，并通过单元测试。
  - com.dwarfeng.fileio.stack.bean.entity.ExportConf。
  - com.dwarfeng.fileio.stack.bean.entity.ExporterInfo。
  - com.dwarfeng.fileio.stack.bean.entity.ExporterSupport。
  - com.dwarfeng.fileio.stack.bean.entity.ExportFileInfo。
  - com.dwarfeng.fileio.stack.bean.entity.ExportMetadata。
  - com.dwarfeng.fileio.stack.bean.entity.ExportTask。
  - com.dwarfeng.fileio.stack.bean.entity.ExportTaskSetting。
  - com.dwarfeng.fileio.stack.bean.entity.ExportTemplateInfo。
  - com.dwarfeng.fileio.stack.bean.entity.ImportConf。
  - com.dwarfeng.fileio.stack.bean.entity.ImporterInfo。
  - com.dwarfeng.fileio.stack.bean.entity.ImporterSupport。
  - com.dwarfeng.fileio.stack.bean.entity.ImportFileInfo。
  - com.dwarfeng.fileio.stack.bean.entity.ImportMetadata。
  - com.dwarfeng.fileio.stack.bean.entity.ImportTask。
  - com.dwarfeng.fileio.stack.bean.entity.ImportTaskSetting。
  - com.dwarfeng.fileio.stack.bean.entity.ImportTemplateInfo。
  - com.dwarfeng.fileio.stack.bean.entity.ReaderInfo。
  - com.dwarfeng.fileio.stack.bean.entity.ReaderSupport。
  - com.dwarfeng.fileio.stack.bean.entity.User。
  - com.dwarfeng.fileio.stack.bean.entity.WriterInfo。
  - com.dwarfeng.fileio.stack.bean.entity.WriterSupport。

- 项目结构建立，清理测试通过。

### Bug 修复

- (无)

### 功能移除

- (无)
