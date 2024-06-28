package com.modern.devtools.java.command;

import com.modern.devtools.java.Command;
import com.modern.devtools.java.utils.ConsoleUtils;

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
    public void doExecute(String[] args) {
        if(args.length == 0) {
            System.out.println("当前配置: ");
            ConsoleUtils.printProperties(getContext().getConfig());
            return;
        }

        out: for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            switch (arg) {
                case "--workPath":
                    if (args.length > i + 1) {
                        getContext().getConfig().setWorkPath(args[i + 1]);
                    }
                    break out;
                case "--includePackages":
                    if (args.length > i + 1) {
                        getContext().getConfig().setIncludePackages(args[i + 1]);
                    }
                    break out;
                case "--file":
                    String configFilePath = args[i + 1];
                    Properties properties = new Properties();
                    try (FileInputStream fis = new FileInputStream(configFilePath)) {
                        properties.load(fis);
                        getContext().getConfig().setWorkPath(properties.getProperty("workPath"));
                        getContext().getConfig().setIncludePackages(properties.getProperty("includePackages"));
                    } catch (Throwable e) {
                        System.err.println();
                    }
                    break out;
            }
        }

        System.out.println("当前配置: ");
        ConsoleUtils.printProperties(getContext().getConfig());
    }

}
