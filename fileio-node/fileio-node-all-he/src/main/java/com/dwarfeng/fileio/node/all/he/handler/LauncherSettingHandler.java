package com.dwarfeng.fileio.node.all.he.handler;

import com.dwarfeng.subgrade.stack.handler.Handler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class LauncherSettingHandler implements Handler {

    @Value("${com.dwarfeng.fileio.launcher.reset_exporter_support}")
    private boolean resetExporterSupport;
    @Value("${com.dwarfeng.fileio.launcher.reset_importer_support}")
    private boolean resetImporterSupport;
    @Value("${com.dwarfeng.fileio.launcher.reset_reader_support}")
    private boolean resetReaderSupport;
    @Value("${com.dwarfeng.fileio.launcher.reset_writer_support}")
    private boolean resetWriterSupport;

    @Value("${com.dwarfeng.fileio.launcher.start_reset_delay}")
    private long startResetDelay;

    @Value("${com.dwarfeng.fileio.launcher.online_task_check_delay}")
    private long onlineTaskCheckDelay;
    @Value("${com.dwarfeng.fileio.launcher.enable_task_check_delay}")
    private long enableTaskCheckDelay;

    @Value("${com.dwarfeng.fileio.launcher.online_purge_delay}")
    private long onlinePurgeDelay;
    @Value("${com.dwarfeng.fileio.launcher.enable_purge_delay}")
    private long enablePurgeDelay;

    public boolean isResetExporterSupport() {
        return resetExporterSupport;
    }

    public boolean isResetImporterSupport() {
        return resetImporterSupport;
    }

    public boolean isResetReaderSupport() {
        return resetReaderSupport;
    }

    public boolean isResetWriterSupport() {
        return resetWriterSupport;
    }

    public long getStartResetDelay() {
        return startResetDelay;
    }

    public long getOnlineTaskCheckDelay() {
        return onlineTaskCheckDelay;
    }

    public long getEnableTaskCheckDelay() {
        return enableTaskCheckDelay;
    }

    public long getOnlinePurgeDelay() {
        return onlinePurgeDelay;
    }

    public long getEnablePurgeDelay() {
        return enablePurgeDelay;
    }

    @Override
    public String toString() {
        return "LauncherSettingHandler{" +
                "resetExporterSupport=" + resetExporterSupport +
                ", resetImporterSupport=" + resetImporterSupport +
                ", resetReaderSupport=" + resetReaderSupport +
                ", resetWriterSupport=" + resetWriterSupport +
                ", startResetDelay=" + startResetDelay +
                ", onlineTaskCheckDelay=" + onlineTaskCheckDelay +
                ", enableTaskCheckDelay=" + enableTaskCheckDelay +
                ", onlinePurgeDelay=" + onlinePurgeDelay +
                ", enablePurgeDelay=" + enablePurgeDelay +
                '}';
    }
}
