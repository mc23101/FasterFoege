package com.github.zhangsiyao.FasterForge.Utils;

import java.io.InputStream;

public class ResourcesUtil {

    /**
     * 获取resources资源的绝对路径
     * @param path 来自源跟的相对路径
     * @return 资源的绝对路径
     * */
    public static InputStream getResource(String path){
        return ResourcesUtil.class.getClassLoader().getResourceAsStream(path);
    }
}
