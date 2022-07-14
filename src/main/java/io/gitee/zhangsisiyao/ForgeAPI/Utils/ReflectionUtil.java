package io.gitee.zhangsisiyao.ForgeAPI.Utils;

public class ReflectionUtil {
    public static boolean isExtendFrom(Class child, Class parent){
        while (!child.getName().equals(Object.class.getName())){
            if(child.getName().equals(parent.getName())){
                return true;
            }else{
                child=child.getSuperclass();
            }
        }
        return false;
    }
}
