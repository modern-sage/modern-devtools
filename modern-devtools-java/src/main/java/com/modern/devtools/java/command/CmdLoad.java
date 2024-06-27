package com.modern.devtools.java.command;

import com.modern.devtools.java.Command;
import com.modern.devtools.java.JavaFile;
import com.modern.devtools.java.utils.FileUtils;

import java.io.File;
import java.util.*;

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
    public void execute() {
        Map<String, List<JavaFile>> javaFileMap = getContext().getJavaFileMap();
        FileUtils.dfsTraversalFiles(new File(getContext().getWorkPath()),
                javaFileMap, (f) -> f.getAbsoluteFile().getName().endsWith(".java")
        );
        System.out.printf("加载完所有的 java 文件。数量：%d%n", javaFileMap.size());
    }
}
