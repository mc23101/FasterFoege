package io.gitee.zhangsisiyao.ForgeAPI.Annotation.Loader;

import io.gitee.zhangsisiyao.ForgeAPI.Annotation.MinecraftPotion;
import io.gitee.zhangsisiyao.ForgeAPI.MinecraftCore;
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
        for(Class c:classes){
            try {
                MinecraftPotion annotation = (MinecraftPotion) c.getAnnotation(MinecraftPotion.class);
                String id = annotation.modId();
                String name = annotation.name();
                boolean badEffect = annotation.isBadEffect();
                int color = annotation.liquidColor();
                Constructor constructor = c.getConstructor(boolean.class, int.class);
                constructor.setAccessible(true);
                Potion potion = (Potion) constructor.newInstance(badEffect, color);
                potion.setRegistryName(new ResourceLocation(id,name));
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
        }

        logger.info("一共注册"+classes.size()+"个药水效果");
    }
}
