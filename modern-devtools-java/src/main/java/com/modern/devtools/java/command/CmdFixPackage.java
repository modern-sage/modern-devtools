
package com.modern.devtools.java.command;

import com.modern.devtools.java.Command;
import com.modern.devtools.java.JavaFile;
import com.modernframework.core.utils.StringUtils;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * CmdInfoJava
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangjun</a>
 * @since 1.0.0
 */
public class CmdFixPackage implements CmdExecutor {

    @Override
    public String supportCommand() {
        return Command.FIX_PACKAGE.getKey();
    }

    @Override
    public void execute(String command) {
        Map<String, List<JavaFile>> javaFileMap = getContext().getJavaFileMap();
        AtomicInteger fixNum = new AtomicInteger();
        javaFileMap.values().forEach(x -> x.forEach(y -> {
            File file = y.getFile();
            StringBuilder javaContent = new StringBuilder();
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    javaContent.append(line).append("\n");
                }
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }

            String[] contents = javaContent.toString().split("\n");
            String sourceImport = "";
            for (String content : contents) {
                if (content.startsWith("package ")) {
                    sourceImport = content;
                    break;
                }
            }

            if(StringUtils.isBlank(sourceImport)) {
                System.err.printf("文件 %s 缺少 package 信息%n", y.getJavaName());
            } else {
                try {
                    String packageText = sourceImport.substring("package".length() + 1, sourceImport.lastIndexOf(";")).trim();
                    if (!packageText.equals(y.getJavaPath())) {
                        String targetImport = "package " + y.getJavaPath() + ";";
                        String targetContext = javaContent.toString().replace(sourceImport, targetImport);
                        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                            bw.write(targetContext);
                            bw.flush();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        // 修复
                        System.out.printf("修复 %s package 信息，%s -> %s%n", y.getJavaSimpleName(), sourceImport, targetImport);
                        fixNum.addAndGet(1);
                    }
                } catch (StringIndexOutOfBoundsException e) {
                    e.printStackTrace();
                }
            }


        }));
        System.out.printf("修复完成，修复文件数: %s%n", fixNum.get());
    }
}
