package com.modern.devtools.java.command;

import com.modern.devtools.java.Command;
import com.modern.devtools.java.Context;

import java.util.Arrays;
import java.util.Comparator;

/**
 * 推出
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangjun</a>
 * @since 1.0.0
 */
public class CmdList implements CmdExecutor {

    @Override
    public String supportCommand() {
        return Command.LIST.getKey();
    }

    @Override
    public void execute(String command) {
        StringBuilder sb = new StringBuilder();
        Arrays.stream(Command.values())
                .sorted(Comparator.comparingInt(Command::getSort))
                .forEach(x -> {
                    sb.append(x.getKey()).append(": ").append(x.getDesc()).append("\n");
                });
        System.out.println(sb);
    }
}
