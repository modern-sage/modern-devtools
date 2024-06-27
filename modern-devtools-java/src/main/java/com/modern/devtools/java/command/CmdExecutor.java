package com.modern.devtools.java.command;

import com.modern.devtools.java.Context;

/**
 * 命令执行器
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangjun</a>
 * @since 1.0.0
 */
public interface CmdExecutor {

    String supportCommand();

    void execute();

    default int getPriority() {
        return 0;
    }

    default Context getContext() {
        return Context.getContext();
    }
}
