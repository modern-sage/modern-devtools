
package com.modern.devtools.java.command;

import com.modern.devtools.java.Command;
import com.modern.devtools.java.JavaFile;
import com.modern.devtools.java.utils.ConsoleUtils;
import com.modern.devtools.java.utils.FileUtils;
import com.modernframework.core.utils.CollectionUtils;
import com.modernframework.core.utils.StringUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * CmdInfoJava
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangjun</a>
 * @since 1.0.0
 */
public class CmdFix implements CmdExecutor {

    @Override
    public String supportCommand() {
        return Command.FIX.getKey();
    }

    @Override
    public void doExecute(String[] args) {
        if (args.length == 0) {
            return;
        }
        out:
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            switch (arg) {
                case "--package":
                    doFixPackage(args);
                    break out;
                case "--import":
                    doFixImport(args);
                    break out;
                case "--replace":
                    if (args.length > i + 2) {
                        doFixReplace(args[i + 1], args[i + 2]);
                    }
                    break out;
            }
        }
    }

    private void doFixReplace(String source, String target) {
        Map<String, List<JavaFile>> javaFileMap = getContext().getJavaFileMap();
        AtomicInteger fixNum = new AtomicInteger();
        javaFileMap.values().forEach(x -> x.forEach(y -> {
            File file = y.getFile();
            String javaContent = FileUtils.readFile(file);
            String targetJavaContent = null;
            if (javaContent.contains(source)) {
                targetJavaContent = javaContent.replaceAll(source, target);
            }
            if(targetJavaContent != null) {
                try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                    bw.write(targetJavaContent);
                    bw.flush();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                // 修复
                System.out.printf("修复 %s，%s -> %s%n", y.getJavaSimpleName(), source, target);
                fixNum.addAndGet(1);
            }
        }));
        System.out.printf("修复完成，修复文件数: %s%n", fixNum.get());
    }


    private void doFixPackage(String[] args) {
        Map<String, List<JavaFile>> javaFileMap = getContext().getJavaFileMap();
        AtomicInteger fixNum = new AtomicInteger();
        javaFileMap.values().forEach(x -> x.forEach(y -> {
            File file = y.getFile();
            String javaContent = FileUtils.readFile(file);
            String[] contents = javaContent.split("\n");
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
                        String targetContext = javaContent.replace(sourceImport, targetImport);
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
                    // debug ...
                    e.printStackTrace();
                }
            }


        }));
        System.out.printf("修复完成，修复文件数: %s%n", fixNum.get());
    }

    private void doFixImport(String[] args) {
        Map<String, List<JavaFile>> javaFileMap = getContext().getJavaFileMap();
        AtomicInteger fixNum = new AtomicInteger();
        javaFileMap.values().forEach(x -> x.forEach(y -> {
            File file = y.getFile();
            String javaContent = FileUtils.readFile(file);
            String[] contents = javaContent.split("\n");
            List<String> sourceImports = new LinkedList<>();
            for (String content : contents) {
                if (content.startsWith("import ")) {
                    sourceImports.add(content);
                }
            }

            if (CollectionUtils.isEmpty(sourceImports)) {
                System.err.printf("文件 %s 缺少 import 信息%n", y.getJavaName());
            } else {
                try {
                    Map<String, String> replaceMap = new HashMap<>();
                    sourceImports.forEach(s -> {
                        String importText;
                        String simpleJavaName;
                        if (s.startsWith("import static ")) {
                            importText = s.substring("import static ".length(), s.lastIndexOf(";")).trim();
                            importText = importText.substring(0, importText.lastIndexOf("."));
                            simpleJavaName = importText.substring(importText.lastIndexOf(".") + 1);
                        } else {
                            importText = s.substring("import ".length(), s.lastIndexOf(";")).trim();
                            simpleJavaName = importText.substring(importText.lastIndexOf(".") + 1);
                        }
                        if (javaFileMap.containsKey(simpleJavaName) && (
                                // 前缀匹配
                                getContext().getConfig().includePackage(importText)
                        )) {
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
                                JavaFile matchJavaFile = null;
                                if (matchJavaFiles.size() > 1) {
                                    boolean chose = false;
                                    while (!chose) {
                                        try {
                                            System.out.println("匹配到多个文件，请选择其中一个用来修复 \n");
                                            for (int i = 0; i < matchJavaFiles.size(); i++) {
                                                System.out.printf("%d - %s%n", i, matchJavaFiles.get(i).getJavaName());
                                            }
                                            String choose = getContext().getScanner().nextLine().trim();
                                            int chooseNum = Integer.parseInt(choose);
                                            matchJavaFile = matchJavaFiles.get(chooseNum);
                                            chose = true;
                                        } catch (Throwable e) {
                                            chose = false;
                                        }
                                    }
                                } else {
                                    matchJavaFile = matchJavaFiles.get(0);
                                }
                                String targetImport = "import " + matchJavaFile.getJavaName() + ";";
                                replaceMap.put(s, targetImport);
                            }
                        }

                        if (!replaceMap.isEmpty()) {
                            fixNum.addAndGet(1);
                            System.out.printf("修复 %s import 信息%n", y.getJavaName());
                            String targetContext = javaContent.toString();
                            for (String key : replaceMap.keySet()) {
                                String target = replaceMap.get(key);
                                targetContext = targetContext.replace(key, target);
                                System.out.printf("%s -> %s%n", key, target);
                            }
                            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                                bw.write(targetContext);
                                bw.flush();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    });
                } catch (StringIndexOutOfBoundsException e) {
                    e.printStackTrace();
                }
            }
        }));
        System.out.printf("修复完成，修复文件数: %s%n", fixNum.get());
    }


}
