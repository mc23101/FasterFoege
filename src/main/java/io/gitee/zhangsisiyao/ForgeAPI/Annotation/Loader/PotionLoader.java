package io.gitee.zhangsisiyao.ForgeAPI.Annotation.Loader;

import io.gitee.zhangsisiyao.ForgeAPI.Annotation.MinecraftPotion;
import io.gitee.zhangsisiyao.ForgeAPI.Manager.PotionManager;
import io.gitee.zhangsisiyao.ForgeAPI.Utils.ReflectionUtil;
import net.minecraft.block.Block;
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

    private static final String errorType="药水效果";
    private static Logger logger= LogManager.getLogger("ForgeFrame");

    private static int success=0;

    private static int error=0;

    public static void PotionAnnotationLoader(Reflections reflections){
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
                boolean isRegistered= PotionManager.containPotion(location);
                boolean canRegister= isExtended && !isRegistered;

                if(canRegister){
                    Constructor constructor = c.getConstructor(boolean.class, int.class);
                    constructor.setAccessible(true);
                    Potion potion = (Potion) constructor.newInstance(badEffect, color);
                    potion.setRegistryName(location);
                    PotionManager.registerPotion(potion);
                    logger.debug("药水效果:"+id+":"+name+"注册成功");
                }else if(!isExtended){
                    error++;
                    AnnotationFactory.throwException(logger,errorType,location,"药水效果应为"+ Block.class.getName()+"的子类",c);
                }else if(isRegistered){
                    error++;
                    AnnotationFactory.throwException(logger,errorType,location,location+"名称已被注册",c);
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
                boolean isRegistered=PotionManager.containPotion(location);

                boolean isStatic= Modifier.isStatic(field.getModifiers());
                boolean canRegister= isExtended && !isRegistered && isStatic;

                Class DeclaringClass=field.getDeclaringClass();

                if(canRegister){
                    Object object=field.get(field.getDeclaringClass());
                    boolean isNull=(object==null);
                    if(!isNull){
                        Potion potion = (Potion) object;
                        potion.setRegistryName(location);
                        PotionManager.registerPotion(potion);
                        success++;
                    }else{
                        error++;
                        AnnotationFactory.throwException(logger,errorType,location,"字段:"+field.getName()+"为Null",DeclaringClass,field.getName());
                    }
                }else if(!isExtended){
                    error++;
                    AnnotationFactory.throwException(logger,errorType,location,"方块应为"+Potion.class.getName()+"的子类",DeclaringClass,field.getName());
                }else if(isRegistered){
                    error++;
                    AnnotationFactory.throwException(logger,errorType,location,location+"名称已被注册",DeclaringClass,field.getName());
                }else if(!isStatic){
                    error++;
                    AnnotationFactory.throwException(logger,errorType,location,"字段:"+field.getName()+"为非static",DeclaringClass,field.getName());
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


}
