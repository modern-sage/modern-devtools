package com.modern.devtools.java;

import lombok.Data;

import java.io.File;

/**
 * JavaFile
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @since 1.0.0
 */
@Data
public class JavaFile {
    private File file;
    private String javaPath;
    private String javaName;
    private String javaSimpleName;

    /**
     * 模块名称
     */
    private String moduleName;

    @Override
    public String toString() {
        return "JavaFile{" +
                "file=" + file +
                ", javaPath='" + javaPath + '\'' +
                ", importStr='" + javaName + '\'' +
                '}';
    }
}
