##  安装 GraalVM 

官方下载地址：[官网下载 GraalVM](https://www.graalvm.org/downloads/ "官网下载 GraalVM")     [仓库下载 graalvm/graalvm-ce-builds (github.com)](https://github.com/graalvm/graalvm-ce-builds/releases "仓库下载 graalvm/graalvm-ce-builds (github.com)")

Spring支持Graalvm native-image 功能最低要求 springboot3.x ，而支持Springboot3.x功能的最低要求jdk17+  

注意官网主页只有Java17和20版本的，教程使用的是Java8的 （jdk8运行可能还需要带着jar包，jdk17的打包后可能只需二进制文件就可以运行），需要到这里下载：[Oracle GraalVM Downloads](https://www.oracle.com/downloads/graalvm-downloads.html "Oracle GraalVM Downloads")

要下载 GraalVM 社区版，请访问 [GitHub 上的 GraalVM 版本存储库](https://github.com/graalvm/graalvm-ce-builds/releases "GitHub 上的 GraalVM 版本存储库")。选择 Java 版本（8、11 或 16）并下载。 

安装 Native Image
-------------------

版本信息正确后执行命令安装 native-image

```bash
gu.cmd install native-image
```

也可以选择离线安装 

下载native-image [Oracle GraalVM Downloads](https://www.oracle.com/downloads/graalvm-downloads.html "Oracle GraalVM Downloads")

执行安装命令

```bash
gu.cmd -L install native-image-installable-svm-svmee-java8-windows-amd64-21.3.7.jar
```

 检查是否安装成功

```bash
C:\Users\Administrator>gu.cmd list
ComponentId              Version             Component name                Stability                     Origin
---------------------------------------------------------------------------------------------------------------------------------
graalvm                  23.0.4              GraalVM Core                  Supported
native-image             23.0.4              Native Image                  Early adopter
```

```bash
C:\Users\Administrator>native-image
Please specify options for native-image building or use --help for more info.
```

安装 Visual Studio
--------------------

需要安装运行环境 Visual Studio 和 Microsoft [Visual](https://visualstudio.microsoft.com/vs/ "Visual") C++ （MSVC）有两个安装选择：

\* 使用 Windows 10 SDK 安装 Visual Studio Build Tools

\* 使用 Windows 10 SDK 安装 Visual Studio

可以使用 Visual Studio 2017 版本 15.9 或更高版本。

[下载Visual Studio 2022 IDE - 适用于软件开发人员的编程工具 (microsoft.com)](https://visualstudio.microsoft.com/zh-hans/vs/ "下载Visual Studio 2022 IDE - 适用于软件开发人员的编程工具 (microsoft.com)")  

![image-20240702151254808](./windows%20%E4%BD%BF%E7%94%A8%20GraalVM%20Native%20Image%E6%89%93%E5%8C%85%E5%8F%AF%E6%89%A7%E8%A1%8C%E7%A8%8B%E5%BA%8F.assets/image-20240702151254808.png)

Windows 10 SDK也可以。

注意：VS的安装盘符下会生成 Windows Kits 文件夹，如果找不到可以去注册表里面查看

> 计算机\\HKEY\_LOCAL\_MACHINE\\SOFTWARE\\WOW6432Node\\Microsoft\\Microsoft SDKs\\Windows\\v10.0

设置环境变量
-------------------------------------------------------------------------------

- 新增 INCLUDE

```bash
D:\Windows Kits\10\Include\10.0.22621.0\ucrt
D:\Windows Kits\10\Include\10.0.22621.0\um
D:\Windows Kits\10\Include\10.0.22621.0\shared
D:\Microsoft Visual Studio\2022\Community\VC\Tools\MSVC\14.37.32822\include
```

- 新增 LIB

```bash
D:\Windows Kits\10\Lib\10.0.22621.0\um\x64
D:\Windows Kits\10\Lib\10.0.22621.0\ucrt\x64
D:\Microsoft Visual Studio\2022\Community\VC\Tools\MSVC\14.37.32822\lib\x64
```

- 修改增加 Path

```bash
D:\Microsoft Visual Studio\2022\Community\VC\Tools\MSVC\14.37.32822\bin\Hostx64\x64
```

执行脚本

构建器仅在从 **x64 Native Tools Command Prompt** 执行时才有效

```cobol
D:\Microsoft Visual Studio\2022\Community\VC\Auxiliary\Build\vcvars64.bat
```

 或者通过此命令提示符cd到项目路径：（此方式可以不用配置环境变量）

至此Windows就已经全部配置完成了

打包
-------------

```bash
native-image --no-fallback -jar modern-devtools-java-1.0.0-SNAPSHOT.jar modernDevtoolsJava-1.0.0
```

## 参考

- [Java使用GraalVM Native Image打包可执行程序_java native image-CSDN博客](https://blog.csdn.net/guo__hang/article/details/132738330)