package com.modern.devtools.java;

import lombok.Getter;

import java.io.Serializable;
import java.util.*;
import java.util.function.Predicate;

/**
 * 指令执行历史
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangjun</a>
 * @since 1.0.0
 */

public class CmdHisList implements Serializable {
    private final int capacity;
    private final Map<String, Integer> usageCount;
    @Getter
    private final List<String> orderedList;

    public CmdHisList() {
        this(30);
    }

    public CmdHisList(int capacity) {
        this.capacity = capacity;
        this.usageCount = new LinkedHashMap<>();
        this.orderedList = new ArrayList<>();
    }

    public void add(String command, Predicate<String>... filter) {
        if (filter == null || Arrays.stream(filter).allMatch(x -> x.test(command))) {
            if (usageCount.containsKey(command)) {
                usageCount.compute(command, (k, count) -> count + 1);
                updateOrderedList();
            } else {
                // 如果是新元素
                if (usageCount.size() >= capacity) {
                    // 如果已达到容量上限，移除使用次数最少的元素
                    String leastUsed = orderedList.remove(orderedList.size() - 1);
                    usageCount.remove(leastUsed);
                }
                usageCount.put(command, 1);
                orderedList.add(0, command);
            }
        }
    }

    private void updateOrderedList() {
        orderedList.sort((a, b) -> usageCount.get(b).compareTo(usageCount.get(a)));
    }

    public String get(int index) {
        if (index < 0 || index >= orderedList.size()) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + orderedList.size());
        }
        return orderedList.get(index);
    }

    public int size() {
        return orderedList.size();
    }

}
