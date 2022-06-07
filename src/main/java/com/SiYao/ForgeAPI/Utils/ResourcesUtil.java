package com.SiYao.ForgeAPI.Utils;

public class ResourcesUtil {

    /**
     * 获取resources资源的绝对路径
     * @param path 来自源跟的相对路径
     * @return 资源的绝对路径
     * */
    public static String getResourcesPath(String path){
        String s=ResourcesUtil.class.getResource("/"+path).getPath();
        return s;
    }
}
