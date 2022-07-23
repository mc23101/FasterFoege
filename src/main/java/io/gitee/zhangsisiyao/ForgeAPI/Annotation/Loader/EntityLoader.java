package io.gitee.zhangsisiyao.ForgeAPI.Annotation.Loader;

import io.gitee.zhangsisiyao.ForgeAPI.Annotation.MinecraftEntity;
import io.gitee.zhangsisiyao.ForgeAPI.Annotation.MinecraftEntityRender;
import io.gitee.zhangsisiyao.ForgeAPI.MinecraftCore;
import io.gitee.zhangsisiyao.ForgeAPI.Utils.ReflectionUtil;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ConfigurationBuilder;

import java.util.Set;

@SuppressWarnings("all")
public class EntityLoader {

    private static Logger logger= LogManager.getLogger("ForgeFrame");

    private static int success=0;

    private static int error=0;

    public static void EntityAnnotationLoader(Object o){
        logger.info("注册实体中.........");

        Package pack = o.getClass().getPackage();
        ConfigurationBuilder configuration = new ConfigurationBuilder().forPackages(pack.getName());
        configuration.addScanners(new SubTypesScanner()).addScanners(Scanners.FieldsAnnotated,Scanners.TypesAnnotated,Scanners.ConstructorsAnnotated,Scanners.MethodsAnnotated);
        Reflections reflections = new Reflections(configuration);

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

            boolean isRegistered=MinecraftCore.EntityManger.containEntity(registerName);
            boolean isExtended=ReflectionUtil.isExtendFrom(c,Entity.class);
            boolean canRegister=isExtended&&!isRegistered;

            if(canRegister){
                MinecraftCore.EntityManger.registerEntity(registerName,c,name,id,o,range,frequency,updates,primary,secondary);
                logger.debug("实体:"+modId+":"+name+"注册成功!");
                success++;
            }else if(!isExtended){
                error++;
                logger.error("在"+c.getName()+"处的MinecraftEntity注解使用错误,请将此注解作用在net.minecraft.entity.Entity的子类上!");
            }else if(isRegistered){
                error++;
                logger.error("在"+c.getName()+"处的modId:"+modId+",name:"+name+"已经被注册!!!");
            }

        }
        logger.info("一共注册"+classes.size()+"个实体。成功:"+success+"  失败:"+error);

        EntityRenderAnnotationLoader(o);
    }

    /**
     * 绑定Entity的渲染类
     * @param o mod主类
     * */
    private static void EntityRenderAnnotationLoader(Object o){

        logger.info("绑定实体渲染中.....");

        Package pack = o.getClass().getPackage();
        Reflections reflections=new Reflections(pack.getName());
        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(MinecraftEntityRender.class);
        for(Class c:classes){
            MinecraftEntityRender annotation = (MinecraftEntityRender) c.getAnnotation(MinecraftEntityRender.class);
            Class<? extends Entity> aClass = annotation.EntityClass();
            MinecraftCore.EntityManger.registerEntityRender(aClass,c);
        }

        logger.info("一共绑定"+classes.size()+"个实体渲染");
    }

}
