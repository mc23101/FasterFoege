package com.github.zhangsiyao.FasterForge.ForgeBoot.Annotation.Loader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public abstract class AnnotationLoader implements IAnnotationLoader{
    final Logger logger = LogManager.getLogger("FasterForge");
    public boolean checkField(Field field, Class parent,Class annotation) throws IllegalAccessException {
        field.setAccessible(true);
        Object object=field.get(field.getDeclaringClass());
        Class child=field.getType();
        while (!child.getName().equals(Object.class.getName())){
            if(child.getName().equals(parent.getName())){
                break;
            }else{
                child=child.getSuperclass();
            }
        }
        if(child.getName().equals(Object.class.getName())){
            logger.error("=================================================================================");
            logger.error("注解："+annotation.getSimpleName()+"使用错误");
            logger.error("失败原因："+field.getName()+"字段的类型应为"+parent.getName()+"的子类");
            logger.error("错误位置:"+field.getDeclaringClass().getName()+"类中的"+field.getName()+"字段");
            logger.error("=================================================================================");
            return false;
        }else if (!Modifier.isStatic(field.getModifiers())){
            logger.error("=================================================================================");
            logger.error("注解："+annotation.getSimpleName()+"使用错误");
            logger.error("失败原因："+annotation.getSimpleName()+"应作用在static字段上");
            logger.error("错误位置:"+field.getDeclaringClass().getName()+"类中的"+field.getName()+"字段");
            logger.error("=================================================================================");
            return false;
        }else if(object==null){
            logger.error("=================================================================================");
            logger.error("注解："+annotation.getSimpleName()+"使用错误");
            logger.error("失败原因："+annotation.getSimpleName()+"作用的字段数据为null");
            logger.error("错误位置:"+field.getDeclaringClass().getName()+"类中的"+field.getName()+"字段");
            logger.error("=================================================================================");
            return false;
        }else {
            return true;
        }
    }

    public boolean checkClass(Class child,Class parent,Class annotation){
        while (!child.getName().equals(Object.class.getName())){
            if(child.getName().equals(parent.getName())){
                return true;
            }else{
                child=child.getSuperclass();
            }
        }
        logger.error("=================================================================================");
        logger.error("注解："+annotation.getSimpleName()+"使用错误");
        logger.error("失败原因："+annotation.getSimpleName()+"应作用在"+parent.getName()+"上");
        logger.error("错误位置:"+"在"+child.getName()+"类上");
        logger.error("=================================================================================");
        return false;
    }

}
