package com.modern.devtools.java.command;

import com.modern.devtools.java.Command;
import com.modern.devtools.java.JavaFile;
import com.modern.devtools.java.third.clyoudu.consoletable.ConsoleTable;
import com.modern.devtools.java.third.clyoudu.consoletable.enums.Align;
import com.modern.devtools.java.third.clyoudu.consoletable.table.Cell;

import java.util.*;

/**
 * 推出
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangjun</a>
 * @since 1.0.0
 */
public class CmdListDuplicateFile implements CmdExecutor {

    @Override
    public String supportCommand() {
        return Command.LIST_2.getKey();
    }

    @Override
    public void execute(String command) {
        Map<String, List<JavaFile>> javaFileMap = getContext().getJavaFileMap();
        System.out.println("同类名的文件如下：");
        List<Cell> header = new ArrayList<>() {{
            add(new Cell("类名"));
            add(new Cell("路径"));
            add(new Cell("数量"));
        }};
        List<List<Cell>> body = new ArrayList<>();
        javaFileMap.values().stream().filter(x -> x.size() > 1)
                .forEach(x -> x.forEach(y -> body.add(new ArrayList<>() {
                    {
                        add(new Cell(y.getJavaSimpleName()));
                        add(new Cell(Align.LEFT, y.getJavaPath()));
                        add(new Cell(Align.LEFT, x.size() + ""));
                    }
                })));
        new ConsoleTable.ConsoleTableBuilder().addHeaders(header).addRows(body).build().print();
    }
}
