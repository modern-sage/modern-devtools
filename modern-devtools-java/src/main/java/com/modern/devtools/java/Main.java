package com.modern.devtools.java;

import com.modern.devtools.java.command.CombinerCmdExecutors;

/**
 * Main <br/>
 * native-image --no-fallback -jar modern-devtools-java-1.0.0-SNAPSHOT.jar modernDevtoolsJava-1.0.0 <br/>
 *
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @since 1.0.0
 */
public class Main {
    public final static Context CONTEXT = Context.getContext();

    public static void main(String[] args) {

        while (CONTEXT.isRunning()) {
            System.out.print("请输入指令 (list --cmd 查看命令, exit 退出): \n");
            String command = CONTEXT.getScanner().nextLine().trim();
            CombinerCmdExecutors.execute(command);
        }
    }

}
