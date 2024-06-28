package com.modern.devtools.java;

import lombok.Data;

/**
 * Config
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangjun</a>
 * @since 1.0.0
 */
@Data
public class Config {

    /**
     * 工作目录，在这里配置的文件夹路径，会扫描该目录下的所有文件，进行修复
     */
    private String workPath;

    /**
     * package前缀，如果配置了则在修复的时候只会对该前缀的类、import信息进行修复与匹配
     */
    private String packagePreFix;



}
