package io.gitee.zhangsisiyao.ForgeAPI.Annotation.Loader;

import io.gitee.zhangsisiyao.ForgeAPI.Annotation.MinecraftPotion;
import io.gitee.zhangsisiyao.ForgeAPI.MinecraftCore;
import io.gitee.zhangsisiyao.ForgeAPI.Utils.ReflectionUtil;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;

public class PotionLoader {

    private static Logger logger= LogManager.getLogger(PotionLoader.class);

    public static void PotionAnnotationLoader(Object o){

        logger.info("注册药水效果中......");

        Package pack = o.getClass().getPackage();
        Reflections reflections=new Reflections(pack.getName());
        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(MinecraftPotion.class);
        int success=0;
        int error=0;
        for(Class c:classes){
            MinecraftPotion annotation = (MinecraftPotion) c.getAnnotation(MinecraftPotion.class);
            String id = annotation.modId();
            String name = annotation.name();
            boolean badEffect = annotation.isBadEffect();
            int color = annotation.liquidColor();
            ResourceLocation location = new ResourceLocation(id, name);
            if(ReflectionUtil.isExtendFrom(c,Potion.class)&&!MinecraftCore.PotionManger.containPotion(location)){
                try {
                    Constructor constructor = c.getConstructor(boolean.class, int.class);
                    constructor.setAccessible(true);
                    Potion potion = (Potion) constructor.newInstance(badEffect, color);
                    potion.setRegistryName(location);
                    MinecraftCore.PotionManger.registerPotion(potion);
                    logger.info("药水效果:"+id+":"+name+"注册成功");
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }else if(!ReflectionUtil.isExtendFrom(c, Potion.class)){
                error++;
                logger.error("在"+c.getName()+"处的MinecraftPotion注解使用错误,请将此注解作用在net.minecraft.block.Potion的子类上!");
            }else if(MinecraftCore.PotionManger.containPotion(location)){
                error++;
                logger.error("在"+c.getName()+"处的modId:"+id+",name:"+name+"已经被注册!!!");
            }

        }

        logger.info("一共注册"+classes.size()+"个药水效果。成功:"+success+"  失败:"+error);
    }
}
