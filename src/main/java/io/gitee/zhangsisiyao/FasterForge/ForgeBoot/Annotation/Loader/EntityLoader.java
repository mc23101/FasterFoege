package io.gitee.zhangsisiyao.FasterForge.ForgeBoot.Annotation.Loader;

import io.gitee.zhangsisiyao.FasterForge.ForgeBoot.Annotation.MinecraftEntity;
import io.gitee.zhangsisiyao.FasterForge.ForgeBoot.Annotation.MinecraftEntityRender;
import io.gitee.zhangsisiyao.FasterForge.Manager.EntityManager;
import io.gitee.zhangsisiyao.FasterForge.MinecraftCore;
import io.gitee.zhangsisiyao.FasterForge.Utils.ReflectionUtil;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.reflections.Reflections;

import java.util.Set;

@SuppressWarnings("all")
public class EntityLoader {

    private static final String errorType="实体";

    private static Logger logger= LogManager.getLogger("FasterForge");

    private static int success=0;

    private static int error=0;

    public static void EntityAnnotationLoader(Reflections reflections){
        loadEntityFromClass(reflections);
        loadEntityRender(reflections);
        logger.info("一共注册"+(success+error)+"个实体。成功:"+success+"  失败:"+error);

    }

    private static void loadEntityFromClass(Reflections reflections){
        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(MinecraftEntity.class);
        for(Class c:classes){
            MinecraftEntity annotation = (MinecraftEntity) c.getAnnotation(MinecraftEntity.class);
            String modId = annotation.modId();
            String name = annotation.name();
            ResourceLocation registerName = new ResourceLocation(modId, name);
            int id = annotation.Id();
            int range = annotation.trackingRange();
            int frequency = annotation.updateFrequency();
            boolean updates = annotation.sendsVelocityUpdates();
            int primary = annotation.eggPrimary();
            int secondary = annotation.eggSecondary();

            boolean isRegistered= EntityManager.containEntity(registerName);
            boolean isExtended=ReflectionUtil.isExtendFrom(c,Entity.class);
            boolean canRegister=isExtended&&!isRegistered;

            if(canRegister){
                EntityManager.registerEntity(registerName,c,name,id,MinecraftCore.mod,range,frequency,updates,primary,secondary);
                logger.debug("实体:"+modId+":"+name+"注册成功!");
                success++;
            }else if(!isExtended){
                error++;
                AnnotationFactory.throwException(logger,errorType,registerName,"实体应为"+ Entity.class.getName()+"的子类",c);
            }else if(isRegistered){
                error++;
                AnnotationFactory.throwException(logger,errorType,registerName,registerName+"名称已被注册",c);
            }

        }
    }

    /**
     * 绑定Entity的渲染类
     * @param o mod主类
     * */
    private static void loadEntityRender(Reflections reflections){
        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(MinecraftEntityRender.class);
        for(Class c:classes){
            MinecraftEntityRender annotation = (MinecraftEntityRender) c.getAnnotation(MinecraftEntityRender.class);
            Class<? extends Entity> aClass = annotation.EntityClass();
            EntityManager.registerEntityRender(aClass,c);
        }
        logger.info("一共绑定"+classes.size()+"个实体渲染");
    }

}
