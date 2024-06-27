package com.modern.devtools.java;

import lombok.Data;

/**
 * Progress
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangjun</a>
 * @since 1.0.0
 */
@Data
public class Progress {

    public static final int TOTAL = 100;
    public static final char BACK_GROUND = '░';
    public static final char FRONT_GROUND = '█';

    private long complete;

    private long total;

    public int getPercent() {
        return Math.min((int) (complete * 100.0 / total), 100);
    }

    public void addComplete() {
        this.complete += 1;
    }

    public void addTotal(long num) {
        this.total += num;
    }

}
