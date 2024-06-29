package com.modern.devtools.java;

import lombok.Getter;

import java.util.Arrays;

/**
 * 指令
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangjun</a>
 * @since 1.0.0
 */
@Getter
public enum Command {

    CONFIG("config", "设置",
            new String[]{"--workPath", "--packagePreFix", "--file"},
            new String[]{"设置工作目录", "设置包名前缀", "从文件中配置"}),

    LOAD("load", "加载路径下的文件"),

    INFO("info", "查看信息",
            new String[]{"--java",},
            new String[]{"查看Java文件信息"}),

    LIST("list", "列出信息",
            new String[]{"--cmd", "--error", "--duplicate"},
            new String[]{"列出所有指令", "列出有问题的文件", "列出同名Java文件"}),

    FIX("fix", "修复",
            new String[]{"--package", "--import", "--replace"},
            new String[]{"修复 package 信息", "修复 import 信息", "替换文本，参数是 ${source} ${target}"}),

    HIS("his", "执行历史",
            new String[]{"--list", "--exec"},
            new String[]{"查看执行历史", "执行历史命令，参数是 ${指令序号}"}),

    BACKUP("backup", "存档",
            new String[]{"--save", "--read"},
            new String[]{"存档，参数是 ${文件路径}", "读挡，参数是 ${文件路径}"}),

    EXIT("exit", "推出"),

    ;

    private String key;
    private String desc;

    /**
     * 子指令
     */
    private String[] subKeys;
    /**
     * 子指令说明
     */
    private String[] subDescs;


    Command(String key, String desc) {
        this.key = key;
        this.desc = desc;
    }

    Command(String key, String desc, String[] subKeys, String[] subDescs) {
        this.key = key;
        this.desc = desc;
        this.subKeys = subKeys;
        this.subDescs = subDescs;
    }

    public static int getTotal() {
        return Arrays.stream(Command.values())
                .map(x -> 1 + (x.subKeys == null ? 0 : x.subKeys.length))
                .reduce(0, Integer::sum);
    }

}
