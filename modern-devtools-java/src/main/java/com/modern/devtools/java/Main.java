package com.modern.devtools.java;

import com.modern.devtools.java.command.CombinerCmdExecutors;
import com.modern.devtools.java.third.clyoudu.consoletable.ConsoleTable;
import com.modern.devtools.java.third.clyoudu.consoletable.enums.Align;
import com.modern.devtools.java.third.clyoudu.consoletable.table.Cell;
import com.modern.devtools.java.utils.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.function.Predicate;

import static java.io.File.separatorChar;

/**
 * $desc
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @since $version
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
