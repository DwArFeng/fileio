package com.dwarfeng.fileio.node.all.he.launcher;

import com.dwarfeng.springterminator.sdk.util.ApplicationUtil;

/**
 * Application launcher.
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class Launcher {

    public static void main(String[] args) {
        ApplicationUtil.launch("classpath:spring/application-context*.xml");
    }
}
