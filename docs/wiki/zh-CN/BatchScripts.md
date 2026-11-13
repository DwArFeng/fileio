# Batch Scripts - 批处理脚本

## fileio-start.bat

### 简介

fileio-start.bat 是本项目的启动脚本，用于启动本项目。 您可以调整脚本中的参数，以适应您的需求。

可调整的脚本参数包括：

- JVM 内存选项。
- JMX 远程选项。

脚本执行后，会以命令提示符的方式启动本项目，即打开一个标题为 `Fileio` 的命令提示符窗口，并最大化显示。

需要注意的是，本项目以命令提示符的方式启动后，如果关闭命令提示符，本项目也会随之关闭。

本项目运行过程中，请不要点击命令提示符窗口中的区域，以免阻塞本项目的输入/输出流。

本项目没有提供 `fileio-stop.bat` 停止脚本，在 Windows 系统中停止本项目的方式请参考本文档的“关闭程序的技巧”一节。

### 可配置变量

#### jvm_memory_opts

jvm_memory_opts 是本项目的 JVM 内存选项，默认值为：

```bat
SET jvm_memory_opts=^
-Xmx100m ^
-XX:MaxMetaspaceSize=130m ^
-XX:ReservedCodeCacheSize=15m ^
-XX:CompressedClassSpaceSize=15m
```

如果您希望修改本项目的 JVM 内存选项，可以修改此变量，变量中各选项含义如下：

- Xmx：最大堆内存。
- MaxMetaspaceSize：最大元空间大小。
- ReservedCodeCacheSize：保留代码缓存大小。
- CompressedClassSpaceSize：压缩类空间大小。

如果您希望由 JVM 自动分配内存，可以注释掉上述内容，并取消注释脚本中的以下内容：

```bat
rem SET jvm_memory_opts=
```

#### java_jmxremote_opts

java_jmxremote_opts 是本项目的 JMX 远程选项，默认值为：

```bat
SET java_jmxremote_opts=
```

该脚本默认关闭 JMX 远程选项，如果您需要开启 JMX 远程选项，可以修改此选项。

您可以通过该选项注释中的提示，修改该选项的值，以开启 JMX 远程选项。

下面是一种开启 JMX 远程选项的示例：

```bat
SET java_jmxremote_opts=^
-Dcom.sun.management.jmxremote.port=23000 ^
-Dcom.sun.management.jmxremote.authenticate=false ^
-Dcom.sun.management.jmxremote.ssl=false
```

### 固定变量

#### basedir

basedir 是本项目的根目录，其值为：

```bat
cd /d "%~dp0.."
SET "basedir=%cd%"
```

当启动脚本运行时，basedir 被动态地指定为当前脚本所在目录的上一级。无论在哪个目录中部署本项目，basedir 总会指向正确的路径。
基于以上原因，您无需更改该变量的值。

#### java_logging_opts

java_logging_opts 是本项目的日志配置选项，其值为：

```bat
SET java_logging_opts=^
-Dlog4j2.configurationFile=confext/logging-settings.xml,conf/logging/settings.xml ^
-Dlog4j.shutdownHookEnabled=false ^
-Dlog4j2.is.webapp=false
```

该选项为项目中的 log4j2 日志框架提供了相关的系统参数（System Property）。

如果想要调整该项目的日志行为，您应该修改日志的配置文件，而不是该变量，以下文件可供您修改：

- `conf\logging\settings.xml`：项目内部代码的日志行为推荐使用该文件更改。
- `confext\logging-settings.xml`：插件代码的日志行为推荐使用该文件更改，以便于与项目的日志配置文件隔离。

上述文件修改时，如果日志配置发生冲突，以 `conf\logging\settings.xml` 中的配置为准。

### 关闭程序的技巧

与 Linux 系统不同，本项目在 Windows 系统中，默认是以命令提示符的方式在前台运行的，并且没有提供批处理版本的停止脚本。

请勿直接点击命令提示符窗口的关闭按钮，这样做相当于 Linux 系统中的 kill -9 命令，会强制关闭程序，跳过项目的优雅停机流程，
并造成正在处理的导入、导出任务状态无法被正确回写。

推荐的关闭方式是：

1. 在命令提示符窗口区域内单击，进入输入状态。
2. 输入 `Ctrl + C`，此时程序进入正常退出状态。
3. 观察命令提示符的输出，等待程序正常退出。

使用 `Ctrl + C` 关闭程序时，JVM 会执行关闭钩子：
程序启动入口 `ApplicationUtil.launch` 为 Spring 容器注册了关闭钩子（`ctx.registerShutdownHook()`），
容器会被正常关闭，Dubbo、Telqos、连接池等资源随之释放。

除本脚本外，您也可以在 Telqos 中执行 `shutdown -s` 指令停止本项目。
该指令会要求二次确认，输入 `Y` 后才会执行关闭动作，并支持通过 `-e` 指定退出代码、通过 `-c` 填写备注。
