package com.modern.devtools.java.constant;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * 常量
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangjun</a>
 * @since 1.0.0
 */
public interface Base {

    String SEPARATOR_CHAR = File.separatorChar + "";

    Charset CHARSET = StandardCharsets.UTF_8;


}
