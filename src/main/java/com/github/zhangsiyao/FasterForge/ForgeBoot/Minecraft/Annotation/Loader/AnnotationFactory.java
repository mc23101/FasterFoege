package com.github.zhangsiyao.FasterForge.ForgeBoot.Minecraft.Annotation.Loader;

import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.Logger;

@SuppressWarnings("all")
public class AnnotationFactory {

    public static void throwException(Logger logger, String errorType, ResourceLocation location, String reason, Class aClass){
        logger.error("=================================================================================");
        logger.error(errorType+":"+location+"注册失败");
        logger.error("失败原因:"+reason);
        logger.error("错误位置:"+aClass.getName()+"类");
        logger.error("=================================================================================");
    }

    public static void throwException(Logger logger, String errorType,ResourceLocation location, String reason,Class aClass,String fieldName){
        logger.error("=================================================================================");
        logger.error(errorType+":"+location+"注册失败");
        logger.error("失败原因:"+reason);
        logger.error("错误位置:"+aClass.getName()+"类中的"+fieldName+"字段");
        logger.error("=================================================================================");
    }
}
