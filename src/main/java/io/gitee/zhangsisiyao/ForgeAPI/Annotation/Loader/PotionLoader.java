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
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Set;

public class PotionLoader {

    private static Logger logger= LogManager.getLogger("ForgeFrame");

    private static int success=0;

    private static int error=0;

    public static void PotionAnnotationLoader(Object o){

        logger.info("注册药水效果中......");

        Package pack = o.getClass().getPackage();
        Reflections reflections=new Reflections(pack.getName());

        loadFromClass(reflections);
        loadFromField(reflections);

        logger.info("一共注册"+(success+error)+"个药水效果。成功:"+success+"  失败:"+error);
    }


    private static void loadFromClass(Reflections reflections){
        try {
            Set<Class<?>> classes = reflections.getTypesAnnotatedWith(MinecraftPotion.class);
            for(Class c:classes){
                MinecraftPotion annotation = (MinecraftPotion) c.getAnnotation(MinecraftPotion.class);
                String id = annotation.modId();
                String name = annotation.name();
                boolean badEffect = annotation.isBadEffect();
                int color = annotation.liquidColor();
                ResourceLocation location = new ResourceLocation(id, name);

                boolean isExtended=ReflectionUtil.isExtendFrom(c,Potion.class);
                boolean isRegistered=MinecraftCore.PotionManger.containPotion(location);
                boolean canRegister= isExtended && !isRegistered;

                if(canRegister){
                    Constructor constructor = c.getConstructor(boolean.class, int.class);
                    constructor.setAccessible(true);
                    Potion potion = (Potion) constructor.newInstance(badEffect, color);
                    potion.setRegistryName(location);
                    MinecraftCore.PotionManger.registerPotion(potion);
                    logger.debug("药水效果:"+id+":"+name+"注册成功");
                }else if(!isExtended){
                    error++;
                    logger.error("在"+c.getName()+"处的MinecraftPotion注解使用错误,请将此注解作用在net.minecraft.potion.Potion的子类上!");
                }else if(isRegistered){
                    error++;
                    logger.error("在"+c.getName()+"处的modId:"+id+",name:"+name+"已经被注册!!!");
                }

            }
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private static void loadFromField(Reflections reflections){
        try {
            Set<Field> fieldsAnnotatedWith = reflections.getFieldsAnnotatedWith(MinecraftPotion.class);
            for(Field field:fieldsAnnotatedWith){
                MinecraftPotion annotation = field.getAnnotation(MinecraftPotion.class);
                String id = annotation.modId();
                String name = annotation.name();
                boolean badEffect = annotation.isBadEffect();
                int color = annotation.liquidColor();
                ResourceLocation location = new ResourceLocation(id, name);


                boolean isExtended=ReflectionUtil.isExtendFrom(field.getType(),Potion.class);
                boolean isRegistered=MinecraftCore.PotionManger.containPotion(location);

                boolean isStatic= Modifier.isStatic(field.getModifiers());
                boolean canRegister= isExtended && !isRegistered && isStatic;

                if(canRegister){
                    Object object=field.get(field.getDeclaringClass());
                    boolean isNull=(object==null);
                    if(!isNull){
                        Potion potion = (Potion) object;
                        potion.setRegistryName(location);
                        MinecraftCore.PotionManger.registerPotion(potion);
                        success++;
                    }else{
                        error++;
                        logger.error("在"+field.getDeclaringClass().getName()+"中的字段:"+field.getName()+"对象为null");
                    }
                }else if(!isExtended){
                    error++;
                    logger.error("在"+field.getDeclaringClass().getName()+"处的MinecraftPotion注解使用错误,请将此注解作用在net.minecraft.potion.Potion的对象上!");
                }else if(isRegistered){
                    error++;
                    logger.error("在"+field.getDeclaringClass().getName()+"处的modId:"+id+",name:"+name+"已经被注册!!!");
                }else if(!isStatic){
                    error++;
                    logger.error("在"+field.getDeclaringClass().getName()+"中的字段:"+field.getName()+"注解MinecraftPotion注解使用错误，请作用在static字段上.");
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


}
