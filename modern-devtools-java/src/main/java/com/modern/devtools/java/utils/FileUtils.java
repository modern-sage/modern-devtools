package com.modern.devtools.java.utils;

import com.modern.devtools.java.Context;
import com.modern.devtools.java.JavaFile;
import com.modern.devtools.java.Progress;
import com.modernframework.core.utils.ArrayUtils;
import com.modernframework.core.utils.CollectionUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Predicate;

import static com.modern.devtools.java.constant.Base.SEPARATOR_CHAR;

/**
 * JavaFileUtils
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @since 1.0.0
 */
public abstract class FileUtils {

    public static String readFile(String filePath) {
        return readFile(new File(filePath));
    }

    public static String readFile(File file) {
        StringBuilder javaContent = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                javaContent.append(line).append("\n");
            }
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
        return javaContent.toString();
    }

    public static void loadJavaFiles() {
        Context context = Context.getContext();
        Map<String, List<JavaFile>> javaFileMap = context.getJavaFileMap();
        javaFileMap.clear();
        CountDownLatch countDownLatch = new CountDownLatch(1);
        File currentFile = new File(context.getWorkPath());
        ForkJoinPool.commonPool().execute(() -> {
            FileUtils.dfsTraversalFiles(currentFile,
                    javaFileMap, (f) -> f.getAbsoluteFile().getName().endsWith(".java") && !f.getAbsolutePath().contains("node_modules")
            );
            countDownLatch.countDown();
        });

        String[] spinner = {"-", "\\", "|", "/"};
        int i = 0;
        while (countDownLatch.getCount() > 0) {
            System.out.printf("\r%s Loading %s", spinner[i % spinner.length], currentFile.getAbsolutePath());
            System.out.flush();
            i += 1;
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println();
        System.out.printf("加载完所有的 java 文件。数量：%d%n", javaFileMap.size());
    }

    /**
     * @param fileIter    当前遍历的文件
     * @param javaFileMap 文件Map
     * @param filters     过滤
     */
    @SafeVarargs
    public static void dfsTraversalFiles(File fileIter, Map<String, List<JavaFile>> javaFileMap,
                                         Predicate<File>... filters) {
        if (fileIter.isDirectory()) {
//             System.out.printf("目录: %s%n", fileIter.getAbsolutePath());
            File[] subFiles = fileIter.listFiles();
            if (ArrayUtils.isNotEmpty(subFiles)) {
                List<File> directories = new LinkedList<>();
                Arrays.stream(Objects.requireNonNull(subFiles)).forEach(x -> {
                    if (x.isDirectory()) {
                        directories.add(x);
                    } else {
                        if (filters != null && Arrays.stream(filters).allMatch(f -> f.test(x))) {
                            JavaFile javaFile = new JavaFile();
                            javaFile.setAbsoluteFilePath(x.getAbsolutePath());
                            String filePath = x.getAbsoluteFile().getParent();
                            String javaPath = filePath.substring(filePath.lastIndexOf(SEPARATOR_CHAR + "java") + 6)
                                    .replace(SEPARATOR_CHAR, ".");
                            javaFile.setJavaPath(javaPath);
                            String javaSimpleName = x.getName().replace(".java", "");
                            javaFile.setJavaSimpleName(javaSimpleName);
                            String javaName = javaPath + "." + javaSimpleName;
                            javaFile.setJavaName(javaName);
                            javaFileMap.computeIfAbsent(javaSimpleName, k -> new LinkedList<>()).add(javaFile);
//                                System.out.printf("\t文件: %s%n", javaFile);
                        }
                    }
                });
                directories.forEach(x -> dfsTraversalFiles(x, javaFileMap, filters));
            }
        } else {
            System.out.printf("path: %s, name: %s%n", fileIter.getAbsolutePath(), fileIter.getName());
//            progress.addComplete();
        }
    }


}
