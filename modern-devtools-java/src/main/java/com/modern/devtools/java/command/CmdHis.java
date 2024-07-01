
package com.modern.devtools.java.command;

import com.modern.devtools.java.Command;
import com.modern.devtools.java.JavaFile;
import com.modern.devtools.java.utils.ConsoleUtils;
import com.modern.devtools.java.utils.FileUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * CmdInfoJava
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangjun</a>
 * @since 1.0.0
 */
public class CmdHis implements CmdExecutor {

    @Override
    public String supportCommand() {
        return Command.HIS.getKey();
    }

    @Override
    public void doExecute(String[] args) {
        if (args.length == 0) {
            doHisList();
            return;
        }
        out:
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            switch (arg) {
                case "--list":
                    doHisList();
                    break out;
                case "--exec":
                    if (args.length > i + 1) {
                        doHisExec(args[i + 1]);
                    }
                    break out;
            }
        }
    }

    private void doHisList() {
        List<String[]> kvs = new LinkedList<>();
        for (int i = 0; i < getContext().getCmdHisList().getCapacity(); i++) {
            if(getContext().getCmdHisList().get(i) != null) {
                kvs.add(new String[]{String.valueOf(i), getContext().getCmdHisList().get(i)});
            }
        }
        ConsoleUtils.printKvs(kvs);
    }

    private void doHisExec(String index) {
        try {
            int i = Integer.parseInt(index);
            String cmd = getContext().getCmdHisList().get(i);
            CombinerCmdExecutors.execute(cmd);
        } catch (Throwable e) {
            System.err.println("无效的下标");
        }
    }

}
