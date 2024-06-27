package com.modern.devtools.java.command;

import com.modern.devtools.java.Command;

import java.util.Arrays;
import java.util.Comparator;

/**
 * 推出
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangjun</a>
 * @since 1.0.0
 */
public class CmdSetWorkPath implements CmdExecutor {

    @Override
    public String supportCommand() {
        return Command.SET_WORK_PATH.getKey();
    }

    @Override
    public void execute() {
        System.out.print("清输入项目路径: \n");
        getContext().setWorkPath(getContext().getScanner().nextLine().trim());
        System.out.printf("项目路径设置为: %s%n", getContext().getWorkPath());
    }
}
