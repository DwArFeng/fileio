# Shell Scripts - Shell 脚本

## fileio-start.sh

### 简介

fileio-start.sh 是本项目的启动脚本，用于启动本项目。 您可以调整脚本中的参数，以适应您的需求。

可调整的脚本参数包括：

- JVM 内存选项。
- JMX 远程选项。

脚本执行后，将会以 nohup 方式启动本项目，并将 PID 写入 fileio.pid 文件中。

脚本启动前会将工作目录切换到项目根目录，因此项目中的相对路径（例如 `lib`、`libext`、`conf` 等）总是指向正确的位置，
您可以从任意目录调用该脚本。

### 可配置变量

#### jvm_memory_opts

jvm_memory_opts 是本项目的 JVM 内存选项，默认值为：

```bash
jvm_memory_opts="\
-Xmx100m \
-XX:MaxMetaspaceSize=130m \
-XX:ReservedCodeCacheSize=15m \
-XX:CompressedClassSpaceSize=15m"
```

如果您希望修改本项目的 JVM 内存选项，可以修改此变量，变量中各选项含义如下：

- Xmx：最大堆内存。
- MaxMetaspaceSize：最大元空间大小。
- ReservedCodeCacheSize：保留代码缓存大小。
- CompressedClassSpaceSize：压缩类空间大小。

如果您希望由 JVM 自动分配内存，可以注释掉上述内容，并取消注释脚本中的以下内容：

```bash
jvm_memory_opts=""
```

#### java_jmxremote_opts

java_jmxremote_opts 是本项目的 JMX 远程选项，默认值为：

```bash
java_jmxremote_opts=""
```

该脚本默认关闭 JMX 远程选项，如果您需要开启 JMX 远程选项，可以修改此选项。

您可以通过该选项注释中的提示，修改该选项的值，以开启 JMX 远程选项。

下面是一种开启 JMX 远程选项的示例：

```bash
java_jmxremote_opts="\
-Dcom.sun.management.jmxremote.port=23000 \
-Dcom.sun.management.jmxremote.authenticate=false \
-Dcom.sun.management.jmxremote.ssl=false"
```

### 固定变量

#### basedir

basedir 是本项目的根目录，其值为：

```bash
basedir="$(cd "$(dirname "$0")/.." && pwd)"
```

当启动脚本运行时，basedir 被动态地指定为当前脚本所在目录的上一级。无论在哪个目录中部署本项目，basedir 总会指向正确的路径。
基于以上原因，您无需更改该变量的值。

#### java_logging_opts

java_logging_opts 是本项目的日志配置选项，其值为：

```bash
java_logging_opts="\
-Dlog4j2.configurationFile=confext/logging-settings.xml,conf/logging/settings.xml \
-Dlog4j.shutdownHookEnabled=false \
-Dlog4j2.is.webapp=false"
```

该选项为项目中的 log4j2 日志框架提供了相关的系统参数（System Property）。

如果想要调整该项目的日志行为，您应该修改日志的配置文件，而不是该变量，以下文件可供您修改：

- `conf/logging/settings.xml`：项目内部代码的日志行为推荐使用该文件更改。
- `confext/logging-settings.xml`：插件代码的日志行为推荐使用该文件更改，以便于与项目的日志配置文件隔离。

上述文件修改时，如果日志配置发生冲突，以 `conf/logging/settings.xml` 中的配置为准。

### 调试技巧

在安装与部署本项目时，您可能会遇到各种问题，其中部分问题是因为 JVM 启动失败导致的。

这些问题导致了项目无法启动，也无法通过追踪日志的方式定位问题，这时您可以通过以下方式重定位脚本的输出，以便于您定位问题。

找到脚本中的以下代码：

```bash
eval \
nohup /bin/java -classpath "lib/*:libext/*" \
"$jvm_memory_opts" \
"$java_jmxremote_opts" \
"$java_logging_opts" \
"${mainClass}" \
>/dev/null 2>&1 "&"
echo $! >"$basedir/fileio.pid"
```

将其修改为：

```bash
eval \
nohup /bin/java -classpath "lib/*:libext/*" \
"$jvm_memory_opts" \
"$java_jmxremote_opts" \
"$java_logging_opts" \
"${mainClass}" \
>out.log 2>&1 "&"
echo $! >"$basedir/fileio.pid"
```

然后重新启动项目，您将会在项目的安装目录下看到一个名为 `out.log` 的文件，该文件中记录了脚本的输出。

**排查完问题后，务必将脚本还原，因为 `out.log` 没有滚动机制，如不还原，该文件会越来越大，直至占满全部磁盘空间。**

## fileio-stop.sh

### 简介

fileio-stop.sh 是本项目的停止脚本，用于停止本项目。

脚本执行后，将会读取 fileio.pid 文件中的 PID，并使用 kill 命令停止本项目。

kill 命令向 JVM 发送 SIGTERM 信号，该信号不会强制中断程序，而是触发 JVM 的关闭钩子：

- 程序的启动入口 `ApplicationUtil.launch` 为 Spring 容器注册了关闭钩子（`ctx.registerShutdownHook()`），
  收到信号后会正常关闭容器，Dubbo、Telqos、连接池等资源随之释放。
- 容器关闭时 `TerminateHandler` 会收到 `ContextClosedEvent`，主线程据此结束等待并以正常状态退出。

因此，请勿使用 `kill -9` 等强制手段停止本项目，这样做会跳过上述关闭流程，可能导致正在处理的导入、导出任务状态无法被正确回写。

除本脚本外，您也可以在 Telqos 中执行 `shutdown -s` 指令停止本项目。
该指令会要求二次确认，输入 `Y` 后才会执行关闭动作，并支持通过 `-e` 指定退出代码、通过 `-c` 填写备注。

另外需要注意，kill 命令仅发送信号，脚本不会等待进程完全退出，如需确认停止结果，请观察日志或使用 `ps` 等命令检查进程状态。
脚本也不会删除 fileio.pid 文件，该文件会在下次启动时被覆盖。

如果本项目并未启动，fileio.pid 文件可能不存在或内容为空，此时脚本会因读取不到 PID 而报错，属正常现象。

### 可配置变量

该脚本没有可配置变量。

### 固定变量

#### basedir

basedir 是本项目的根目录，其值为：

```bash
basedir="$(cd "$(dirname "$0")/.." && pwd)"
```

当启动脚本运行时，basedir 被动态地指定为当前脚本所在目录的上一级。无论在哪个目录中部署本项目，basedir 总会指向正确的路径。
基于以上原因，您无需更改该变量的值。
