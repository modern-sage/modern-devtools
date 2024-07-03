# modern-devtools-java
## 说明
modern-devtools-java 是一个终端工具，旨在简化 Java 项目代码迁移过程。它主要帮助开发人员快速修改 import 语句、package 信息，并进行全局文本替换。此工具还提供了历史命令功能和存档功能。

## 主要功能

### 配置设置
- `config --workPath`: 设置工作目录
- `config --packagePreFix`: 设置包名前缀
- `config --file`: 从文件中加载配置

### 文件操作
- `load`: 加载指定路径下的文件

### 信息查看
- `info --java`: 查看 Java 文件信息
- `list --cmd`: 列出所有可用指令
- `list --error`: 列出有问题的文件
- `list --duplicate`: 列出同名 Java 文件

### 代码修复
- `fix --package`: 修复 package 信息
- `fix --import`: 修复 import 语句
- `fix --replace ${source} ${target}`: 替换文本

### 历史命令
- `his --list`: 查看执行历史
- `his --exec ${指令序号}`: 执行历史命令

### 存档功能
- `backup --save ${文件路径}`: 保存当前状态到文件
- `backup --read ${文件路径}`: 从文件中读取保存的状态

### 其他
- `exit`: 退出程序

## 打包

### windows

`x86_x64 Cross Tools Command Prompt for VS 2022`打开终端：

```bash
mvn clean package -P native -pl modern-devtools-java -am
```

## 使用示例
- 列出所有的指令: 
    ```bash
    list --cmd
    ```

- 加载配置：`config --file /path/to/your/properties/file`,配置是properties的文件，如下：

    ```properties
    # 代码项目路径
    workPath=/xxx/yyy/
    # 关注的包
    includePackages=com.xxx.yyy,com.zzz
    ```

- 加载: 
    ```bash
    load
    ```

- 修复 package：
    ```bash
    fix --package
    ```

- 修复 import：

    ```bash
    fix --import
    ```

- 替换特定文本：

    ```bash
    fix --replace com.oldpackage com.newpackage
    ```

- 查看执行历史：
    ```bash
    his --list
    ```

- 执行历史命令：
    ```bash
    his --exec 2
    ```

- 保存当前状态：
    ```bash
    backup --save /path/to/save/state.bak
    ```

    

## 注意事项

- 在进行任何操作之前，请确保已正确设置工作目录。
- 建议在操作前备份您的项目，以防意外发生。
- 使用存档功能可以保存你的修复命令。

## 贡献

欢迎提交 issues 和 pull requests 来帮助改进这个工具。
