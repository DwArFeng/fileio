package com.dwarfeng.fileio.impl.service.telqos;

import com.dwarfeng.fileio.stack.service.SupportQosService;
import com.dwarfeng.springtelqos.sdk.command.CliCommand;
import com.dwarfeng.springtelqos.sdk.configuration.TelqosCommand;
import com.dwarfeng.springtelqos.sdk.util.CliCommandUtil;
import com.dwarfeng.springtelqos.stack.command.CommandDescriptor;
import com.dwarfeng.springtelqos.stack.command.CommandExecutor;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

@TelqosCommand
public class SupportCommand extends CliCommand {

    @SuppressWarnings({"SpellCheckingInspection", "GrazieInspectionRunner", "RedundantSuppression"})
    private static final String IDENTITY = "support";

    // region 指令选项

    private static final String COMMAND_OPTION_RESET_EXPORTER = "reset-exporter";
    private static final String COMMAND_OPTION_RESET_IMPORTER = "reset-importer";
    private static final String COMMAND_OPTION_RESET_WRITER = "reset-writer";
    private static final String COMMAND_OPTION_RESET_READER = "reset-reader";

    private static final String[] COMMAND_OPTION_ARRAY = new String[]{
            COMMAND_OPTION_RESET_EXPORTER,
            COMMAND_OPTION_RESET_IMPORTER,
            COMMAND_OPTION_RESET_WRITER,
            COMMAND_OPTION_RESET_READER
    };

    // endregion

    private final SupportQosService supportQosService;

    public SupportCommand(SupportQosService supportQosService) {
        super(IDENTITY);
        this.supportQosService = supportQosService;
    }

    @Override
    protected DescriptionProvider provideDescriptionProvider() {
        return context -> "支持操作";
    }

    @Override
    protected CliSyntaxProvider provideCliSyntaxProvider() {
        return this::cliSyntaxProvider;
    }

    private String cliSyntaxProvider(CommandDescriptor.Context context) throws Exception {
        String identity = context.getRuntimeIdentity();
        String[] patterns = new String[]{
                identity + " " + CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_RESET_EXPORTER),
                identity + " " + CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_RESET_IMPORTER),
                identity + " " + CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_RESET_WRITER),
                identity + " " + CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_RESET_READER)
        };
        return CliCommandUtil.cliSyntax(patterns);
    }

    @Override
    protected List<Option> provideOptions() {
        List<Option> list = new ArrayList<>();
        list.add(Option.builder().longOpt(COMMAND_OPTION_RESET_EXPORTER).desc("重置导出器支持").build());
        list.add(Option.builder().longOpt(COMMAND_OPTION_RESET_IMPORTER).desc("重置导入器支持").build());
        list.add(Option.builder().longOpt(COMMAND_OPTION_RESET_WRITER).desc("重置写入器支持").build());
        list.add(Option.builder().longOpt(COMMAND_OPTION_RESET_READER).desc("重置读取器支持").build());
        return list;
    }

    @Override
    protected void executeWithCmd(CommandExecutor.Context context, CommandLine cmd) throws Exception {
        Pair<String, Integer> pair = CliCommandUtil.analyseCommand(cmd, COMMAND_OPTION_ARRAY);
        if (pair.getRight() != 1) {
            context.sendMessage(CliCommandUtil.optionMismatchMessage(COMMAND_OPTION_ARRAY));
            context.sendMessage(context.getCommandManual(context.getRuntimeIdentity()));
            return;
        }
        switch (pair.getLeft()) {
            case COMMAND_OPTION_RESET_EXPORTER:
                supportQosService.resetExporter();
                context.sendMessage("重置导出器支持成功。");
                break;
            case COMMAND_OPTION_RESET_IMPORTER:
                supportQosService.resetImporter();
                context.sendMessage("重置导入器支持成功。");
                break;
            case COMMAND_OPTION_RESET_WRITER:
                supportQosService.resetWriter();
                context.sendMessage("重置写入器支持成功。");
                break;
            case COMMAND_OPTION_RESET_READER:
                supportQosService.resetReader();
                context.sendMessage("重置读取器成功。");
                break;
            default:
                throw new IllegalStateException("不应该执行到此处, 请联系开发人员");
        }
    }
}
