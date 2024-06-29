package com.modern.devtools.java;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.modern.devtools.java.utils.FileUtils;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
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
public class Context implements Serializable {

    private static Context CONTEXT = new Context();

    private boolean running;

    @JsonIgnore
    private Scanner scanner;

    private Config config;

    private CmdHisList cmdHisList;

    @JsonIgnore
    Map<String, List<JavaFile>> javaFileMap = new HashMap<>();

    public Context() {
        this.running = true;
        scanner = new Scanner(System.in);
        this.config = new Config();
        this.cmdHisList = new CmdHisList();
    }

    public void destroy() {
        this.running = false;
        this.scanner.close();
    }

    public String getWorkPath() {
        return getContext().getConfig().getWorkPath();
    }

    public static Context getContext() {
        return CONTEXT;
    }

    public static void restore(ContextMemento memento) {
        CONTEXT.setConfig(memento.getConfig());
        CONTEXT.setCmdHisList(memento.getCmdHisList());
        if(StringUtils.isNoneBlank(CONTEXT.getWorkPath())) {
            FileUtils.loadJavaFiles();
        }
    }

}
