package com.modern.devtools.java.command;

import com.modern.devtools.java.Command;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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

                    } catch (Throwable e) {
                        System.err.println();
                    }
                    properties.load(new FileInputStream(configFilePath));

                    Properties properties = getContext().getConfig().loadConfig(configFilePath);
                    break;
                default:
                    break;
            }
        }

        System.out.print("清输入项目路径: \n");
        getContext().setWorkPath(getContext().getScanner().nextLine().trim());
        System.out.printf("项目路径设置为: %s%n", getContext().getWorkPath());
    }
}
