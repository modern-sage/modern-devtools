package com.modern.devtools.java;

import lombok.Data;

import java.io.Serializable;

/**
 * 上下文备份
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangjun</a>
 * @since 1.0.0
 */
@Data
public class ContextMemento implements Serializable {

    private Config config;

    private CmdHisList cmdHisList;

    public ContextMemento() {
    }
}
