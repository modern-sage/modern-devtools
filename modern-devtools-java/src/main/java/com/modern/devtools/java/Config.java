package com.modern.devtools.java;

import com.modernframework.core.utils.StringUtils;
import lombok.Data;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Config
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangjun</a>
 * @since 1.0.0
 */
@Data
public class Config implements Serializable {

    /**
     * 工作目录，在这里配置的文件夹路径，会扫描该目录下的所有文件，进行修复
     */
    private String workPath;

    /**
     * 需要修复的包名，如果配置了，则只会对配置的包名进行修复
     */
    private String includePackages;

    public boolean includePackage(String packageName) {
        if(includePackages == null || includePackages.isEmpty()) {
            return true;
        }
        if(StringUtils.isBlank(packageName)) {
            return false;
        }
        return Arrays.stream(includePackages.split(",")).anyMatch(packageName::startsWith);
    }

}
