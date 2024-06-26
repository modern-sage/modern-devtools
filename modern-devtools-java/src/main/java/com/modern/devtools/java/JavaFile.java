package com.modern.devtools.java;

import java.io.File;

/**
 * JavaFile
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @since 1.0.0
 */
public class JavaFile {
    private File file;
    private String javaPath;
    private String javaName;
    private String javaSimpleName;

    public JavaFile() {
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getJavaPath() {
        return javaPath;
    }

    public void setJavaPath(String javaPath) {
        this.javaPath = javaPath;
    }

    public String getJavaName() {
        return javaName;
    }

    public void setJavaName(String javaName) {
        this.javaName = javaName;
    }

    @Override
    public String toString() {
        return "JavaFile{" +
                "file=" + file +
                ", javaPath='" + javaPath + '\'' +
                ", importStr='" + javaName + '\'' +
                '}';
    }

    public String getJavaSimpleName() {
        return javaSimpleName;
    }

    public void setJavaSimpleName(String javaSimpleName) {
        this.javaSimpleName = javaSimpleName;
    }
}
