package com.modern.devtools.java.command;

import com.modern.devtools.java.Command;
import com.modern.devtools.java.JavaFile;
import com.modern.devtools.java.utils.FileUtils;

import java.io.File;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ForkJoinPool;

/**
 * 推出
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangjun</a>
 * @since 1.0.0
 */
public class CmdLoad implements CmdExecutor {

    @Override
    public String supportCommand() {
        return Command.LOAD.getKey();
    }

    @Override
    public void execute(String command) {
        Map<String, List<JavaFile>> javaFileMap = getContext().getJavaFileMap();
        CountDownLatch countDownLatch = new CountDownLatch(1);
        File currentFile = new File(getContext().getWorkPath());
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
}
