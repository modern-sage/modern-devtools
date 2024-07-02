package com.modern.devtools.java.command;

import com.modern.devtools.java.Command;
import com.modern.devtools.java.Context;
import com.modern.devtools.java.JavaFile;
import com.modern.devtools.java.third.clyoudu.consoletable.ConsoleTable;
import com.modern.devtools.java.third.clyoudu.consoletable.enums.Align;
import com.modern.devtools.java.third.clyoudu.consoletable.table.Cell;
import com.modern.devtools.java.utils.ConsoleUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static com.modern.devtools.java.constant.Base.CHARSET;

/**
 * 推出
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangjun</a>
 * @since 1.0.0
 */
public class CmdList implements CmdExecutor {

    @Override
    public String supportCommand() {
        return Command.LIST.getKey();
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
                case "--cmd":
                    List<String[]> commands = new LinkedList<>();
                    for (Command cmd : Command.values()) {
                        commands.add(new String[]{cmd.getKey(), cmd.getDesc()});
                        String[] sub = cmd.getSubKeys();
                        if (sub != null && sub.length > 0) {
                            String[] subDesc = cmd.getSubDescs();
                            for (int j = 0; j < sub.length; j++) {
                                commands.add(new String[]{"  " + sub[j], subDesc[j]});
                            }
                        }
                    }
                    ConsoleUtils.printKvs(commands);
                    break out;
                case "--error":
                    doListError(args);
                    break out;
                case "--duplicate":
                    doListDuplicate(args);
                    break out;

            }
        }

    }

    private void doListDuplicate(String[] args) {
        Map<String, List<JavaFile>> javaFileMap = getContext().getJavaFileMap();
        System.out.println("同类名的文件如下：");
        List<Cell> header = new ArrayList<>() {{
            add(new Cell("类名"));
            add(new Cell("路径"));
            add(new Cell("数量"));
        }};
        List<List<Cell>> body = new ArrayList<>();
        javaFileMap.values().stream().filter(x -> x.size() > 1)
                .forEach(x -> x.forEach(y -> body.add(new ArrayList<>() {
                    {
                        add(new Cell(y.getJavaSimpleName()));
                        add(new Cell(Align.LEFT, y.getJavaPath()));
                        add(new Cell(Align.LEFT, x.size() + ""));
                    }
                })));
        new ConsoleTable.ConsoleTableBuilder().addHeaders(header).addRows(body).build().print();
    }

    private void doListError(String[] args) {
        Map<String, List<JavaFile>> javaFileMap = getContext().getJavaFileMap();
        System.out.println("有问题的文件如下：");
        AtomicInteger errorNum = new AtomicInteger();
        javaFileMap.values().forEach(x -> x.forEach(y -> {
            File file = y.getFile();
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file, CHARSET))) {
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
