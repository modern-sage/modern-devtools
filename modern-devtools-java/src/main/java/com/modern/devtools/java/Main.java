package com.modern.devtools.java;

import com.modern.devtools.java.command.CombinerCmdExecutors;
import com.modern.devtools.java.third.clyoudu.consoletable.ConsoleTable;
import com.modern.devtools.java.third.clyoudu.consoletable.enums.Align;
import com.modern.devtools.java.third.clyoudu.consoletable.table.Cell;
import com.modern.devtools.java.utils.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.function.Predicate;

import static java.io.File.separatorChar;

/**
 * $desc
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @since $version
 */
public class Main {

    public final static Context CONTEXT = Context.getContext();

    public static void main(String[] args) throws IOException {
//        Scanner scanner = new Scanner(System.in);

        while (CONTEXT.isRunning()) {
            System.out.print("请输入指令 (list 查看命令, exit 退出): \n");
//            System.out.print("1. 设置项目路径 \n");
//            System.out.print("2. 重复类查看 \n");
            String command = CONTEXT.getScanner().nextLine().trim().toLowerCase();
            CombinerCmdExecutors.execute(command);

//            switch (command) {
//                case "1":
//                    setWorkPath(scanner);
//                    break;
//                case "2":
//                    printRepeatedJavaFile();
//                    break;
//                case "help":
//                    printHelp();
//                    break;
//                case "hello":
//                    System.out.println("你好!");
//                    break;
//                case "date":
//                    System.out.println("当前日期: " + java.time.LocalDate.now());
//                    break;
//                case "exit":
//                    running = false;
//                    System.out.println("程序退出。");
//                    break;
//                default:
//                    System.out.println("未知指令，请输入 'help' 查看可用指令。");
//            }
        }

//        scanner.close();
    }

//    private static void setWorkPath(Scanner scanner) {
//        System.out.print("清输入项目路径: \n");
//        CONTEXT.setWorkPath(scanner.nextLine().trim());
//        System.out.printf("项目路径设置为: %s%n", CONTEXT.getWorkPath());
//    }

//    private static void printHelp() {
//        System.out.println("可用指令:");
//        System.out.println("  help  - 显示此帮助信息");
//        System.out.println("  hello - 显示问候语");
//        System.out.println("  date  - 显示当前日期");
//        System.out.println("  exit  - 退出程序");
//    }

//    public static void printRepeatedJavaFile() {
//        Map<String, List<JavaFile>> javaFileMap = new HashMap<>();
//        FileUtils.dfsTraversalFiles(new File(CONTEXT.getWorkPath()), javaFileMap,
//                (f) -> f.getAbsoluteFile().getName().endsWith(".java")
//        );
//        System.out.printf("加载完所有的 java 文件。数量：%d%n", javaFileMap.size());
//        System.out.println("同类名的文件如下：");
//
//        List<Cell> header = new ArrayList<>() {{
//            add(new Cell("类名"));
//            add(new Cell("路径"));
//            add(new Cell("数量"));
//        }};
//        List<List<Cell>> body = new ArrayList<>();
//
//        javaFileMap.values().stream().filter(x -> x.size() > 1)
//                .forEach(x -> x.forEach(y -> body.add(new ArrayList<>() {
//                    {
//                        add(new Cell(y.getJavaSimpleName()));
//                        add(new Cell(Align.LEFT, y.getJavaPath()));
//                        add(new Cell(Align.LEFT, x.size() + ""));
//                    }
//                })));
//        new ConsoleTable.ConsoleTableBuilder().addHeaders(header).addRows(body).build().print();
//    }


    public static void main1(String[] args) throws IOException {
        Map<String, List<JavaFile>> javaFileMap = new HashMap<>();
        FileUtils.dfsTraversalFiles(new File(CONTEXT.getWorkPath()), javaFileMap,
                (f) -> f.getAbsoluteFile().getName().endsWith(".java")
        );

        System.out.printf("加载完所有的 java 文件。数量：%d%n", javaFileMap.size());
        System.out.println("同类名的文件如下：");
        javaFileMap.values().stream().filter(x -> x.size() > 1)
                .forEach(x -> {
                    System.out.printf("类名: %s, 文件数量：%d%n",
                            x.get(0).getJavaSimpleName(), x.size());
                    x.forEach(y -> System.out.printf("    %s%n", y.getJavaPath()));
                });
//        System.out.println(javaFileMap);

        // 修复 import
        javaFileMap.values().forEach(xx -> xx.forEach(x -> {
            File file = x.getFile();
            System.out.println("开始修复文件: " + file.getAbsolutePath());
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
//                 BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8))
            ) {
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    if (line.startsWith("import")) {
                        System.out.println("修复 import: " + line);
                    } else if (line.startsWith("package")) {
                        System.out.println("修复 package: " + line);
                    } else if (line.startsWith("public")) {
                        break;
                    }
                }
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }


            // 读取 package 并修复


            // 读取 import 并修复


        }));


    }

}
