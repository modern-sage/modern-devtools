package com.modern.devtools.java;

import lombok.Data;

import java.io.File;
import java.io.Serializable;

/**
 * JavaFile
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @since 1.0.0
 */
@Data
public class JavaFile implements Serializable {

    /**
     * 文件路径
     */
    private String absoluteFilePath;

    /**
     * 路径
     */
    private String javaPath;

    /**
     * 全类名
     */
    private String javaName;

    /**
     * 简单名称
     */
    private String javaSimpleName;

    /**
     * 模块名称
     */
    private String moduleName;

    public File getFile() {
        return new File(absoluteFilePath);
    }

}
