package com.modern.devtools.java.command;

import com.modern.devtools.java.Command;
import com.modern.devtools.java.JavaFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 推出
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangjun</a>
 * @since 1.0.0
 */
public class CmdListError implements CmdExecutor {

    @Override
    public String supportCommand() {
        return Command.LIST_ERROR.getKey();
    }

    @Override
    public void execute(String command) {
        Map<String, List<JavaFile>> javaFileMap = getContext().getJavaFileMap();
        System.out.println("有问题的文件如下：");
        AtomicInteger errorNum = new AtomicInteger();
        javaFileMap.values().forEach(x -> x.forEach(y -> {
            File file = y.getFile();
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
                String line;
                StringBuilder sb = new StringBuilder();
                while ((line = bufferedReader.readLine()) != null) {
                    if (line.startsWith("//")) {
                        continue;
                    } else if (line.startsWith("import ")) {
                        String importText;
                        String simpleJavaName;
                        if (line.startsWith("import static ")) {
                            importText = line.substring("import static ".length(), line.lastIndexOf(";")).trim();
                            importText = importText.substring(0, importText.lastIndexOf("."));
                            simpleJavaName = importText.substring(importText.lastIndexOf(".") + 1);
                        } else {
                            importText = line.substring("import ".length(), line.lastIndexOf(";")).trim();
                            simpleJavaName = importText.substring(importText.lastIndexOf(".") + 1);
                        }

                        if (javaFileMap.containsKey(simpleJavaName)) {
                            List<JavaFile> matchJavaFiles = javaFileMap.get(simpleJavaName);
                            boolean matched = false;
                            for (JavaFile matchJavaFile : matchJavaFiles) {
                                String matchJavaFilePath = matchJavaFile.getJavaName();
                                if (matchJavaFilePath.equals(importText)) {
                                    matched = true;
                                    break;
                                }
                            }
                            if (!matched) {
                                sb.append(line).append(" ... (X) ").append("\n");
                            }
                        }
                    } else if (line.startsWith("package")) {
                        String packageText = line.substring("package".length() + 1, line.lastIndexOf(";")).trim();
                        if (!packageText.equals(y.getJavaPath())) {
                            sb.append(packageText).append(" ... (X) ").append("\n");
                        }
                    } else if (line.startsWith("public")) {
                        break;
                    }
                }

                if (sb.length() > 0) {
                    errorNum.addAndGet(1);
                    StringBuilder print = new StringBuilder();
                    print.append("模块: ").append("\n");
                    print.append("文件: ").append(y.getJavaName()).append("\n");
                    print.append("问题: \n").append(sb).append("\n");
                    System.out.println(print);
                }

            } catch (Throwable e) {
                throw new RuntimeException(e);
            }

        }));

        StringBuilder print = new StringBuilder();
        print.append("-------------------------------------").append("\n");
        print.append("合计: ").append("\n");
        print.append("问题文件数量: ").append(errorNum.get()).append("\n");
        print.append("-------------------------------------").append("\n");
        System.out.println(print);
    }
}
