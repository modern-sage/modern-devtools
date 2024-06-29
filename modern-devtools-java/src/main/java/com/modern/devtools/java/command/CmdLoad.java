package com.modern.devtools.java.command;

import com.modern.devtools.java.Command;
import com.modern.devtools.java.utils.FileUtils;


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
    public void doExecute(String[] args) {
        FileUtils.loadJavaFiles();
    }
}
