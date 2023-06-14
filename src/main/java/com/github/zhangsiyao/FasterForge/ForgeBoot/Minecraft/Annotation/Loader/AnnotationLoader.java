package com.github.zhangsiyao.FasterForge.ForgeBoot.Minecraft.Annotation.Loader;

import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public abstract class AnnotationLoader implements IAnnotationLoader{
    final Logger logger = LogManager.getLogger("FasterForge");
    public boolean isStaticField(Field field, ResourceLocation location) throws IllegalAccessException {
        field.setAccessible(true);
        Object object=field.get(field.getDeclaringClass());
        if (!Modifier.isStatic(field.getModifiers())){
            logger.error("=================================================================================");
            logger.error("资源："+location+"注册失败");
            logger.error("失败原因：此字段为非static字段");
            logger.error("错误位置:"+field.getDeclaringClass().getName()+"类中的"+field.getName()+"字段");
            logger.error("=================================================================================");
            return false;
        }else if(object==null){
            logger.error("=================================================================================");
            logger.error("资源："+location+"注册失败");
            logger.error("失败原因：此字段的数据为null");
            logger.error("错误位置:"+field.getDeclaringClass().getName()+"类中的"+field.getName()+"字段");
            logger.error("=================================================================================");
            return false;
        }else {
            return true;
        }
    }

}
