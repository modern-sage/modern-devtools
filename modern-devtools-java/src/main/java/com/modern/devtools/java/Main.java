package com.modern.devtools.java;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.function.Predicate;

/**
 * $desc
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @since $version
 */
public class Main {


    public static void main(String[] args) throws IOException {
        String workPath = "/Users/junjun/workspace/code/modern_S23001/modern-mom";

//        File tmp = new File("/Users/junjun/workspace/code/modern_S23001/modern-mom/modern-mom-manager/src/main/java/com/modern/mom/domain/manager/event/BackBroadcastMsg.java");
//        BufferedReader br = new BufferedReader(new FileReader(tmp));
//        System.out.println( br.readLine());


        Map<String, List<JavaFile>> javaFileMap = new HashMap<>();
        dfsFiles(new File(workPath), javaFileMap,
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


    private static void dfsFiles(File fileIter, Map<String, List<JavaFile>> javaFileMap, Predicate<File>... filters) {
        if (fileIter.isDirectory()) {
//            System.out.printf("目录: %s%n", fileIter.getAbsolutePath());
            File[] subFIleIters = fileIter.listFiles();
            List<File> directories = new LinkedList<>();
            Arrays.stream(subFIleIters)
                    .forEach(x -> {
                        if (x.isDirectory()) {
                            directories.add(x);
                        } else {
                            if (filters != null && Arrays.stream(filters).allMatch(f -> f.test(x))) {
                                JavaFile javaFile = new JavaFile();
                                javaFile.setFile(x);
                                String filePath = x.getAbsoluteFile().getParent();
                                String javaPath = filePath.substring(filePath.lastIndexOf("/java") + 6)
                                        .replace("/", ".");
                                javaFile.setJavaPath(javaPath);
                                String javaSimpleName = x.getName().replace(".java", "");
                                javaFile.setJavaSimpleName(javaSimpleName);
                                String javaName = javaPath + "." + javaSimpleName;
                                javaFile.setJavaName(javaName);
//                                if(javaFileMap.containsKey(javaSimpleName)) {
//                                    System.out.println(1);
//                                }
                                javaFileMap.computeIfAbsent(javaSimpleName, k -> new LinkedList<>()).add(javaFile);
//                                System.out.printf("\t文件: %s%n", javaFile);
                            }
                        }
                    });
            directories.forEach(x -> dfsFiles(x, javaFileMap, filters));
        } else {
            System.out.printf("path: %s, name: %s%n", fileIter.getAbsolutePath(), fileIter.getName());
        }
    }

}
