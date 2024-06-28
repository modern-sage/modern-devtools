package com.modern.devtools.java.command;

import com.modern.devtools.java.Command;
import com.modern.devtools.java.Context;

/**
 * 命令执行器
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangjun</a>
 * @since 1.0.0
 */
public interface CmdExecutor {

    String supportCommand();

    default void execute(String command) {
        String[] args = command.trim().split(" ");
        String[] args2 = new String[args.length - 1];
        System.arraycopy(args, 1, args2, 0, args.length - 1);
        doExecute(args2);
    }

    void doExecute(String[] args);

    default int getPriority() {
        return 0;
    }

    default Context getContext() {
        return Context.getContext();
    }
}
