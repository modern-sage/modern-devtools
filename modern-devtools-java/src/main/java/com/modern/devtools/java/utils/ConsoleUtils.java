package com.modern.devtools.java.utils;

import com.modernframework.core.convert.ConvertUtils;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * 数据工具
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangjun</a>
 * @since 1.0.0
 */
public abstract class ConsoleUtils {

    public static List<String[]> getObjKvList(Object obj) {
        List<String[]> kvList = new LinkedList<>();
        if (obj != null) {
            Arrays.stream(obj.getClass().getDeclaredFields())
                    .forEach(field -> {
                        field.setAccessible(true);
                        try {
                            kvList.add(new String[]{field.getName(),
                                    ConvertUtils.convertIfPossible(field.get(obj), String.class, "")});
                        } catch (IllegalAccessException ignore) {
                        }
                    });
        }
        return kvList;
    }
    /**
     * 键值对数据格式化输出
     */
    public static void printProperties(Object object) {
        printKvs(getObjKvList(object));
    }


    /**
     * 键值对数据格式化输出
     */
    public static void printKvs(List<String[]> kvList) {
        int maxLength = Integer.MIN_VALUE;
        for (String[] kv : kvList) {
            maxLength = Math.max(maxLength, kv[0].length());
        }
        String format = "%-" + (maxLength + 4) + "s%s%n";
        for (String[] kv : kvList) {
            System.out.printf(format, kv[0] + ":", kv[1]);
        }
    }

}
