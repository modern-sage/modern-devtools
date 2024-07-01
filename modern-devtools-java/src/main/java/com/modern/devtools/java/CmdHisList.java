package com.modern.devtools.java;

import lombok.Data;

import java.io.Serializable;
import java.util.*;
import java.util.function.Predicate;

/**
 * 指令执行历史
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangjun</a>
 * @since 1.0.0
 */
@Data
public class CmdHisList implements Serializable {
    private final int capacity;
    private final int[] numIndex;
    private final String[] commands;

    public CmdHisList() {
        this(30);
    }

    public CmdHisList(int capacity) {
        this.capacity = capacity;
        this.numIndex = new int[capacity];
        Arrays.fill(numIndex, Integer.MIN_VALUE);
        this.commands = new String[capacity];
    }

    public void add(String command, Predicate<String>... filter) {
        if (filter == null || Arrays.stream(filter).allMatch(x -> x.test(command))) {
            int index = -1;
            for (int i = 0; i < commands.length; i++) {
                if(Objects.equals(commands[i], command)) {
                    index = i;
                    break;
                }
            }
            if(index > -1) {
                numIndex[index] += 1;
            } else {
                boolean inserted = false;
                int minIndex = Integer.MAX_VALUE;
                for (int i = 0; i < commands.length; i++) {
                    if(commands[i] == null) {
                        commands[i] = command;
                        numIndex[i] = 1;
                        inserted = true;
                        break;
                    }
                    minIndex = Math.min(minIndex, numIndex[i]);
                }
                if(!inserted) {
                    commands[minIndex] = command;
                    numIndex[minIndex] = 1;
                }
            }
            sort();
        }
    }

    private void sort() {
        for (int i = 0; i < numIndex.length - 1; i++) {
            for (int j = 0; j < numIndex.length - i - 1; j++) {
                if (numIndex[j] < numIndex[j + 1]) {
                    int temp = numIndex[j];
                    numIndex[j] = numIndex[j + 1];
                    numIndex[j + 1] = temp;
                    String cmdTemp = commands[j];
                    commands[j] = commands[j + 1];
                    commands[j + 1] = cmdTemp;
                }
            }
        }
    }

    public String get(int index) {
        return commands[index];
    }

}
