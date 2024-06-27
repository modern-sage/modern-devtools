package com.modern.devtools.java.command;

import com.modern.devtools.java.Command;
import com.modern.devtools.java.JavaFile;
import com.modernframework.core.utils.CollectionUtils;
import com.modernframework.core.utils.StringUtils;

import java.util.List;

/**
 * CmdInfoJava
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangjun</a>
 * @since 1.0.0
 */
public class CmdInfoJava implements CmdExecutor {

    @Override
    public String supportCommand() {
        return Command.INFO_JAVA.getKey();
    }

    @Override
    public void execute(String command) {
        String infoJava = Command.INFO_JAVA.getKey();
        String javaSimpleName = command.substring(infoJava.length() + 1).trim();
        if(StringUtils.isBlank(javaSimpleName)) {
            System.out.println("请输入要查看的Java类名");
        }
        List<JavaFile> javaFiles = getContext().getJavaFileMap().get(javaSimpleName);
        if(CollectionUtils.isEmpty(javaFiles)) {
            System.out.printf("类名 %s 不存在。%n", javaSimpleName);
        } else {
            javaFiles.forEach(System.out::println);
        }
    }
}
