
package com.modern.devtools.java.command;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.modern.devtools.java.Command;
import com.modern.devtools.java.Context;
import com.modern.devtools.java.ContextMemento;
import com.modern.devtools.java.JavaFile;
import com.modern.devtools.java.utils.FileUtils;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * CmdBackup
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangjun</a>
 * @since 1.0.0
 */
public class CmdBackup implements CmdExecutor {

    @Override
    public String supportCommand() {
        return Command.BACKUP.getKey();
    }

    @Override
    public void doExecute(String[] args) {
        if (args.length == 0) {
            return;
        }
        out:
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            switch (arg) {
                case "--save":
                    if (args.length > i + 1) {
                        doSaveFile(args[i + 1]);
                    }
                    break out;
                case "--read":
                    if (args.length > i + 1) {
                        doReadFile(args[i + 1]);
                    }
                    break out;
            }
        }
    }

    private void doSaveFile(String filename) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonString = objectMapper.writeValueAsString(getContext());
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
                bw.write(jsonString);
                bw.flush();
                System.out.println("存档成功: " + filename);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void doReadFile(String filename) {
        try (FileInputStream fis = new FileInputStream(filename)) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            ContextMemento backup = objectMapper.readValue(fis, ContextMemento.class);
            Context.restore(backup);
            System.out.println("读档成功: " + filename);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }

    }

}
