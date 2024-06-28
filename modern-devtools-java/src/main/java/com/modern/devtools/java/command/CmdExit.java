package com.modern.devtools.java.command;

import com.modern.devtools.java.Command;
import com.modern.devtools.java.Context;

/**
 * 推出
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangjun</a>
 * @since 1.0.0
 */
public class CmdExit implements CmdExecutor {

    @Override
    public String supportCommand() {
        return Command.EXIT.getKey();
    }

    @Override
    public void doExecute(String[] args) {
        Context.getContext().destroy();
        System.out.println("程序退出。");
    }
}
