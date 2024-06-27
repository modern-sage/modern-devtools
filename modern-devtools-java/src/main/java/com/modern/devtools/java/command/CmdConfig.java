package com.modern.devtools.java.command;

import com.modern.devtools.java.Command;

import java.io.FileInputStream;
import java.util.Properties;

/**
 * 推出
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangjun</a>
 * @since 1.0.0
 */
public class CmdConfig implements CmdExecutor {

    @Override
    public String supportCommand() {
        return Command.CONFIG.getKey();
    }

    @Override
    public void execute(String command) {
        String[] args = command.substring(Command.CONFIG.getKey().length()).trim().split(" ");
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            switch (arg) {
                case "--workPath":
                    if(args.length > i + 1) {
                        getContext().getConfig().setWorkPath(args[i + 1]);
                    }
                    break;
                case "--packagePreFix":
                    if(args.length > i + 1) {
                        getContext().getConfig().setPackagePreFix(args[i + 1]);
                    }
                    break;
                case "--file":
                    String configFilePath = args[i + 1];
                    Properties properties = new Properties();
                    try(FileInputStream fis = new FileInputStream(configFilePath)) {
                        properties.load(fis);
                        getContext().getConfig().setWorkPath(properties.getProperty("workPath"));
                        getContext().getConfig().setPackagePreFix(properties.getProperty("packagePreFix"));
                    } catch (Throwable e) {
                        System.err.println();
                    }
                    break;
                default:
                    break;
            }
        }
        System.out.printf("设置完毕: %s%n", getContext().getConfig().toString());
    }
}
