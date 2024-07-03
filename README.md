# modern-devtools-java
## 背景
代码迁移的过程中，会遇到包名修改、类的位置发生变更等等情况，当项目庞大的时候，迁移的工作成本较大。
针对上面的情况，便有了开发这款工具的灵感，利用此工具可以使用命令批量修复代码迁移中的package、import信息。

## 打包

### windows

`x86_x64 Cross Tools Command Prompt for VS 2022`打开终端：

```bash
mvn clean package -P native -pl modern-devtools-java -am
```

## 使用
- 列出所有的指令: `list --cmd`
- 加载配置：`config --file ${path}`,配置是properties的文件，如下：
    ```properties
   # 代码项目路径
   workPath=/xxx/yyy/
   # 关注的包
   includePackages=com.xxx.yyy,com.zzz
   ```
- 加载: `load` 
- 修复 package：`fix --package`
- 修复 import：`fix --import`