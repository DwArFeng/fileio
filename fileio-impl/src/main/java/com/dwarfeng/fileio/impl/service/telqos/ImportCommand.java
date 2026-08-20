package com.dwarfeng.fileio.impl.service.telqos;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.dutil.basic.io.IOUtil;
import com.dwarfeng.fileio.sdk.bean.dto.WebInputMetadataUpsertInfo;
import com.dwarfeng.fileio.sdk.bean.dto.WebInputTaskCreateInfo;
import com.dwarfeng.fileio.sdk.bean.dto.WebInputTaskExecuteInfo;
import com.dwarfeng.fileio.sdk.bean.key.WebInputTaskItemKey;
import com.dwarfeng.fileio.stack.bean.dto.*;
import com.dwarfeng.fileio.stack.bean.key.TaskItemKey;
import com.dwarfeng.fileio.stack.service.ImportQosService;
import com.dwarfeng.springtelqos.sdk.command.CliCommand;
import com.dwarfeng.springtelqos.sdk.configuration.TelqosCommand;
import com.dwarfeng.springtelqos.sdk.util.CliCommandUtil;
import com.dwarfeng.springtelqos.stack.command.CommandDescriptor;
import com.dwarfeng.springtelqos.stack.command.CommandExecutor;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.lang3.tuple.Pair;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@TelqosCommand
public class ImportCommand extends CliCommand {

    @SuppressWarnings({"SpellCheckingInspection", "GrazieInspectionRunner", "RedundantSuppression"})
    private static final String IDENTITY = "import";

    // region 指令选项

    private static final String COMMAND_OPTION_CREATE_TASK = "ct";
    private static final String COMMAND_OPTION_CREATE_TASK_LONG_OPT = "create-task";
    private static final String COMMAND_OPTION_UPSERT_TASK_METADATA = "utm";
    private static final String COMMAND_OPTION_UPSERT_TASK_METADATA_LONG_OPT = "upsert-task-metadata";
    private static final String COMMAND_OPTION_UPLOAD_FILE = "uf";
    private static final String COMMAND_OPTION_UPLOAD_FILE_LONG_OPT = "upload-file";
    private static final String COMMAND_OPTION_EXECUTE_TASK = "st";
    private static final String COMMAND_OPTION_EXECUTE_TASK_LONG_OPT = "execute-task";
    private static final String COMMAND_OPTION_DOWNLOAD_FILE = "df";
    private static final String COMMAND_OPTION_DOWNLOAD_FILE_LONG_OPT = "download-file";

    private static final String[] COMMAND_OPTION_ARRAY = new String[]{
            COMMAND_OPTION_CREATE_TASK,
            COMMAND_OPTION_UPSERT_TASK_METADATA,
            COMMAND_OPTION_UPLOAD_FILE,
            COMMAND_OPTION_EXECUTE_TASK,
            COMMAND_OPTION_DOWNLOAD_FILE,
    };

    private static final String COMMAND_SUB_OPTION_JSON = "json";
    private static final String COMMAND_SUB_OPTION_JSON_FILE = "jf";
    private static final String COMMAND_SUB_OPTION_JSON_FILE_LONG_OPT = "json-file";

    // endregion

    private final ImportQosService importQosService;

    public ImportCommand(ImportQosService importQosService) {
        super(IDENTITY);
        this.importQosService = importQosService;
    }

    @Override
    protected DescriptionProvider provideDescriptionProvider() {
        return context -> "导入操作";
    }

    @Override
    protected CliSyntaxProvider provideCliSyntaxProvider() {
        return this::cliSyntaxProvider;
    }

    private String cliSyntaxProvider(CommandDescriptor.Context context) throws Exception {
        String identity = context.getRuntimeIdentity();
        String[] patterns = new String[]{
                identity + " " + CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_CREATE_TASK) + " [" +
                        CliCommandUtil.concatOptionPrefix(COMMAND_SUB_OPTION_JSON) + " json-string] [" +
                        CliCommandUtil.concatOptionPrefix(COMMAND_SUB_OPTION_JSON_FILE) + " json-file]",
                identity + " " + CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_UPSERT_TASK_METADATA) + " [" +
                        CliCommandUtil.concatOptionPrefix(COMMAND_SUB_OPTION_JSON) + " json-string] [" +
                        CliCommandUtil.concatOptionPrefix(COMMAND_SUB_OPTION_JSON_FILE) + " json-file]",
                identity + " " + CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_UPLOAD_FILE) + " [" +
                        CliCommandUtil.concatOptionPrefix(COMMAND_SUB_OPTION_JSON) + " json-string] [" +
                        CliCommandUtil.concatOptionPrefix(COMMAND_SUB_OPTION_JSON_FILE) + " json-file]",
                identity + " " + CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_EXECUTE_TASK) + " [" +
                        CliCommandUtil.concatOptionPrefix(COMMAND_SUB_OPTION_JSON) + " json-string] [" +
                        CliCommandUtil.concatOptionPrefix(COMMAND_SUB_OPTION_JSON_FILE) + " json-file]",
                identity + " " + CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_DOWNLOAD_FILE) + " [" +
                        CliCommandUtil.concatOptionPrefix(COMMAND_SUB_OPTION_JSON) + " json-string] [" +
                        CliCommandUtil.concatOptionPrefix(COMMAND_SUB_OPTION_JSON_FILE) + " json-file]"
        };
        return CliCommandUtil.cliSyntax(patterns);
    }

    @Override
    protected List<Option> provideOptions() {
        List<Option> list = new ArrayList<>();
        list.add(Option.builder(
                        COMMAND_OPTION_CREATE_TASK).longOpt(COMMAND_OPTION_CREATE_TASK_LONG_OPT)
                .desc("创建导入任务").build()
        );
        list.add(Option.builder(
                        COMMAND_OPTION_UPSERT_TASK_METADATA).longOpt(COMMAND_OPTION_UPSERT_TASK_METADATA_LONG_OPT)
                .desc("插入/更新导入任务元数据").build()
        );
        list.add(Option.builder(
                        COMMAND_OPTION_UPLOAD_FILE).longOpt(COMMAND_OPTION_UPLOAD_FILE_LONG_OPT)
                .desc("上传导入文件").build()
        );
        list.add(Option.builder(
                        COMMAND_OPTION_EXECUTE_TASK).longOpt(COMMAND_OPTION_EXECUTE_TASK_LONG_OPT)
                .desc("执行导入任务").build()
        );
        list.add(Option.builder(
                        COMMAND_OPTION_DOWNLOAD_FILE).longOpt(COMMAND_OPTION_DOWNLOAD_FILE_LONG_OPT)
                .desc("下载导入文件").build()
        );
        list.add(
                Option.builder(COMMAND_SUB_OPTION_JSON).desc("JSON 字符串").hasArg().type(String.class).build()
        );
        list.add(
                Option.builder(COMMAND_SUB_OPTION_JSON_FILE).longOpt(COMMAND_SUB_OPTION_JSON_FILE_LONG_OPT)
                        .desc("JSON 文件").hasArg().type(File.class).build()
        );
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
            case COMMAND_OPTION_CREATE_TASK:
                handleCreateTask(context, cmd);
                break;
            case COMMAND_OPTION_UPSERT_TASK_METADATA:
                handleUpsertTaskMetadata(context, cmd);
                break;
            case COMMAND_OPTION_UPLOAD_FILE:
                handleUploadFile(context, cmd);
                break;
            case COMMAND_OPTION_EXECUTE_TASK:
                handleExecuteTask(context, cmd);
                break;
            case COMMAND_OPTION_DOWNLOAD_FILE:
                handleDownloadFile(context, cmd);
                break;
            default:
                throw new IllegalStateException("不应该执行到此处, 请联系开发人员");
        }
    }

    @SuppressWarnings("DuplicatedCode")
    private void handleCreateTask(CommandExecutor.Context context, CommandLine cmd) throws Exception {
        TaskCreateInfo info;

        // 如果有 -json 选项，则从选项中获取 JSON，转化为 TaskCreateInfo。
        if (cmd.hasOption(COMMAND_SUB_OPTION_JSON)) {
            String json = (String) cmd.getParsedOptionValue(COMMAND_SUB_OPTION_JSON);
            info = WebInputTaskCreateInfo.toStackBean(
                    JSON.parseObject(json, WebInputTaskCreateInfo.class)
            );
        }
        // 如果有 --json-file 选项，则从选项中获取 JSON 文件，转化为 TaskCreateInfo。
        else if (cmd.hasOption(COMMAND_SUB_OPTION_JSON_FILE)) {
            File jsonFile = (File) cmd.getParsedOptionValue(COMMAND_SUB_OPTION_JSON_FILE);
            try (FileInputStream in = new FileInputStream(jsonFile)) {
                info = WebInputTaskCreateInfo.toStackBean(
                        JSON.parseObject(in, WebInputTaskCreateInfo.class)
                );
            }
        } else {
            // 暂时未实现。
            throw new UnsupportedOperationException("not supported yet");
        }

        // 创建任务。
        LongIdKey taskKey = importQosService.createTask(info);

        // 输出结果。
        context.sendMessage("任务创建成功, 任务 ID: " + taskKey.getLongId());
    }

    @SuppressWarnings("DuplicatedCode")
    private void handleUpsertTaskMetadata(CommandExecutor.Context context, CommandLine cmd) throws Exception {
        MetadataUpsertInfo info;

        // 如果有 -json 选项，则从选项中获取 JSON，转化为 MetadataUpsertInfo。
        if (cmd.hasOption(COMMAND_SUB_OPTION_JSON)) {
            String json = (String) cmd.getParsedOptionValue(COMMAND_SUB_OPTION_JSON);
            info = WebInputMetadataUpsertInfo.toStackBean(
                    JSON.parseObject(json, WebInputMetadataUpsertInfo.class)
            );
        }
        // 如果有 --json-file 选项，则从选项中获取 JSON 文件，转化为 MetadataUpsertInfo。
        else if (cmd.hasOption(COMMAND_SUB_OPTION_JSON_FILE)) {
            File jsonFile = (File) cmd.getParsedOptionValue(COMMAND_SUB_OPTION_JSON_FILE);
            try (FileInputStream in = new FileInputStream(jsonFile)) {
                info = WebInputMetadataUpsertInfo.toStackBean(
                        JSON.parseObject(in, WebInputMetadataUpsertInfo.class)
                );
            }
        } else {
            // 暂时未实现。
            throw new UnsupportedOperationException("not supported yet");
        }

        // 插入/更新任务元数据。
        importQosService.upsertTaskMetadata(info);

        // 输出结果。
        context.sendMessage("任务元数据插入/更新成功");
    }

    @SuppressWarnings("DuplicatedCode")
    private void handleUploadFile(CommandExecutor.Context context, CommandLine cmd) throws Exception {
        UploadInfo info;

        // 如果有 -json 选项，则从选项中获取 JSON，转化为 UploadInfo。
        if (cmd.hasOption(COMMAND_SUB_OPTION_JSON)) {
            String json = (String) cmd.getParsedOptionValue(COMMAND_SUB_OPTION_JSON);
            info = JSON.parseObject(json, UploadInfo.class);
        }
        // 如果有 --json-file 选项，则从选项中获取 JSON 文件，转化为 UploadInfo。
        else if (cmd.hasOption(COMMAND_SUB_OPTION_JSON_FILE)) {
            File jsonFile = (File) cmd.getParsedOptionValue(COMMAND_SUB_OPTION_JSON_FILE);
            try (FileInputStream in = new FileInputStream(jsonFile)) {
                info = JSON.parseObject(in, UploadInfo.class);
            }
        } else {
            // 暂时未实现。
            throw new UnsupportedOperationException("not supported yet");
        }

        // 上传文件。
        InputStream in = null;
        try {
            TaskItemKey key = WebInputTaskItemKey.toStackBean(info.getKey());
            File file = new File(info.getFilePath());
            String originName = file.getName();
            long length = file.length();
            in = Files.newInputStream(file.toPath());
            importQosService.uploadFileStream(new FileStreamUploadInfo(key, originName, length, in));
        } finally {
            if (Objects.nonNull(in)) {
                in.close();
            }
        }

        // 输出结果。
        context.sendMessage("文件上传成功");
    }

    @SuppressWarnings("DuplicatedCode")
    private void handleExecuteTask(CommandExecutor.Context context, CommandLine cmd) throws Exception {
        TaskExecuteInfo info;

        // 如果有 -json 选项，则从选项中获取 JSON，转化为 TaskExecuteInfo。
        if (cmd.hasOption(COMMAND_SUB_OPTION_JSON)) {
            String json = (String) cmd.getParsedOptionValue(COMMAND_SUB_OPTION_JSON);
            info = WebInputTaskExecuteInfo.toStackBean(
                    JSON.parseObject(json, WebInputTaskExecuteInfo.class)
            );
        }
        // 如果有 --json-file 选项，则从选项中获取 JSON 文件，转化为 TaskExecuteInfo。
        else if (cmd.hasOption(COMMAND_SUB_OPTION_JSON_FILE)) {
            File jsonFile = (File) cmd.getParsedOptionValue(COMMAND_SUB_OPTION_JSON_FILE);
            try (FileInputStream in = new FileInputStream(jsonFile)) {
                info = WebInputTaskExecuteInfo.toStackBean(JSON.parseObject(in, WebInputTaskExecuteInfo.class));
            }
        } else {
            // 暂时未实现。
            throw new UnsupportedOperationException("not supported yet");
        }

        // 执行任务。
        importQosService.executeTask(info);

        // 输出结果。
        context.sendMessage("任务执行成功");
    }

    @SuppressWarnings("DuplicatedCode")
    private void handleDownloadFile(CommandExecutor.Context context, CommandLine cmd) throws Exception {
        DownloadInfo info;

        // 如果有 -json 选项，则从选项中获取 JSON，转化为 DeviceFileStreamDownloadInfo。
        if (cmd.hasOption(COMMAND_SUB_OPTION_JSON)) {
            String json = (String) cmd.getParsedOptionValue(COMMAND_SUB_OPTION_JSON);
            info = JSON.parseObject(json, DownloadInfo.class);
        }
        // 如果有 --json-file 选项，则从选项中获取 JSON 文件，转化为 DeviceFileStreamDownloadInfo。
        else if (cmd.hasOption(COMMAND_SUB_OPTION_JSON_FILE)) {
            File jsonFile = (File) cmd.getParsedOptionValue(COMMAND_SUB_OPTION_JSON_FILE);
            try (FileInputStream in = new FileInputStream(jsonFile)) {
                info = JSON.parseObject(in, DownloadInfo.class);
            }
        } else {
            // 暂时未实现。
            throw new UnsupportedOperationException("not supported yet");
        }

        // 下载数据。
        InputStream in = null;
        OutputStream out = null;
        try {
            TaskItemKey key = WebInputTaskItemKey.toStackBean(info.getKey());
            FileStreamDownloadInfo downloadInfo = new FileStreamDownloadInfo(key);
            in = importQosService.downloadFileStream(downloadInfo).getContent();
            out = Files.newOutputStream(
                    new File(info.getFilePath()).toPath(),
                    StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING
            );
            IOUtil.trans(in, out, 4096);
        } finally {
            if (Objects.nonNull(in)) {
                in.close();
            }
            if (Objects.nonNull(out)) {
                out.close();
            }
        }

        // 输出结果。
        context.sendMessage("文件下载成功");
    }

    public static class UploadInfo implements Dto {

        private static final long serialVersionUID = -5767437419661890082L;

        @JSONField(name = "key")
        @NotNull
        @Valid
        private WebInputTaskItemKey key;

        @JSONField(name = "file_path")
        @NotNull
        @NotEmpty
        private String filePath;

        public UploadInfo() {
        }

        public WebInputTaskItemKey getKey() {
            return key;
        }

        public void setKey(WebInputTaskItemKey key) {
            this.key = key;
        }

        public String getFilePath() {
            return filePath;
        }

        public void setFilePath(String filePath) {
            this.filePath = filePath;
        }

        @Override
        public String toString() {
            return "UploadInfo{" +
                    "key=" + key +
                    ", filePath='" + filePath + '\'' +
                    '}';
        }
    }

    public static class DownloadInfo implements Dto {

        private static final long serialVersionUID = -6340649176175301219L;

        @JSONField(name = "key")
        @NotNull
        @Valid
        private WebInputTaskItemKey key;

        @JSONField(name = "file_path")
        @NotNull
        @NotEmpty
        private String filePath;

        public DownloadInfo() {
        }

        public WebInputTaskItemKey getKey() {
            return key;
        }

        public void setKey(WebInputTaskItemKey key) {
            this.key = key;
        }

        public String getFilePath() {
            return filePath;
        }

        public void setFilePath(String filePath) {
            this.filePath = filePath;
        }

        @Override
        public String toString() {
            return "DownloadInfo{" +
                    "key=" + key +
                    ", filePath='" + filePath + '\'' +
                    '}';
        }
    }
}
