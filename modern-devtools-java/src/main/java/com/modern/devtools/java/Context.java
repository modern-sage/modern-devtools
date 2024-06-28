package com.modern.devtools.java;

import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * 上下文
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangjun</a>
 * @since 1.0.0
 */
@Data
public class Context {

    private static Context CONTEXT = new Context();

    private boolean running;

    private Scanner scanner;

    /**
     * 配置
     */
    private Config config;

    Map<String, List<JavaFile>> javaFileMap = new HashMap<>();

    private Context() {
        this.running = true;
        scanner = new Scanner(System.in);
        this.config = new Config();
    }

    public void destroy() {
        this.running = false;
        this.scanner.close();
    }

    public String getWorkPath() {
        return getContext().getConfig().getWorkPath();
    }

    public String getPackagePreFix() {
        return getContext().getConfig().getPackagePreFix();
    }

    public static Context getContext() {
        return CONTEXT;
    }
}
