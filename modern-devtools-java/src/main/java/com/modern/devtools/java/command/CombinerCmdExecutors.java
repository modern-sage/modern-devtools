package com.modern.devtools.java.command;

import java.util.*;

/**
 * 命令执行器
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangjun</a>
 * @since 1.0.0
 */
public abstract class CombinerCmdExecutors {

    private static final Map<String, List<CmdExecutor>> DEPOSITORY;

    static {
        DEPOSITORY = new HashMap<>();
        ServiceLoader.load(CmdExecutor.class).stream().forEach(x -> CombinerCmdExecutors.register(x.get()));
        DEPOSITORY.forEach((k, v) -> {
            v.sort(Comparator.comparingInt(CmdExecutor::getPriority));
        });
    }

    private static void register(CmdExecutor cmdExecutor) {
        DEPOSITORY.computeIfAbsent(cmdExecutor.supportCommand(), k -> new ArrayList<>()).add(cmdExecutor);
    }

    public static void execute(String command) {
        List<CmdExecutor> executors = DEPOSITORY.keySet().stream().filter(command::startsWith)
                .findFirst().map(DEPOSITORY::get).orElse(null);
        if (executors != null) {
            try {
                executors.forEach(x -> x.execute(command));
            } catch (Throwable e) {
                System.err.println("报错啦!");
                e.printStackTrace();
            }
        } else {
            System.out.println("无效指令");
        }
    }

}
