package com.modern.devtools.java;

import lombok.Getter;

/**
 * 指令
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangjun</a>
 * @since 1.0.0
 */
@Getter
public enum Command {

    SET_WORK_PATH("set", "设置项目目录", 1),
    CONFIG("config", "设置", 2),
    LOAD("load", "加载路径下的文件", 3),

//    LIST_1("list1", "列出文件目录结构", 4),
    LIST_2("list2", "列出同名Java文件", 4),
    LIST_ERROR("list error", "列出有问题的文件", 5),

    INFO_JAVA("info java", "查看Java文件信息", 50),

    FIX_PACKAGE("fix package", "修复 package 信息", 100),
    FIX_IMPORT("fix import", "修复 import 信息", 101),

    LIST("list cmd", "列出所有指令", Integer.MAX_VALUE - 1),

    EXIT("exit", "推出", Integer.MAX_VALUE),

    ;

    private String key;
    private String desc;
    private int sort;

    Command(String key, String desc, int sort) {
        this.key = key;
        this.desc = desc;
        this.sort = sort;
    }

}
