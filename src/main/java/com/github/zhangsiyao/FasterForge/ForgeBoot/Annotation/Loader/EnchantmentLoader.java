package com.github.zhangsiyao.FasterForge.ForgeBoot.Annotation.Loader;

import com.github.zhangsiyao.FasterForge.ForgeBoot.Annotation.MinecraftEnchantment;
import com.github.zhangsiyao.FasterForge.Manager.EnchantmentManager;
import com.github.zhangsiyao.FasterForge.Utils.ReflectionUtil;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Set;

@SuppressWarnings("all")
public class EnchantmentLoader {

    private static final String errorType="附魔";

    private static Logger logger= LogManager.getLogger("FasterForge");

    private static int success=0;

    private static int error=0;

    public static void EnchantmentAnnotationLoader(Reflections reflections){
        loadFromClass(reflections);
        loadFromField(reflections);
        logger.info("一共注册"+(success+error)+"个附魔。成功:"+success+"  失败:"+error);
    }

    private static void loadFromClass(Reflections reflections){
        try {
            Set<Class<?>> classes = reflections.getTypesAnnotatedWith(MinecraftEnchantment.class);
            for(Class c:classes){
                MinecraftEnchantment annotation = (MinecraftEnchantment) c.getAnnotation(MinecraftEnchantment.class);
                String modId = annotation.modId();
                String name = annotation.name();
                Enchantment.Rarity rarity = annotation.rarity();
                EnumEnchantmentType type = annotation.type();
                EntityEquipmentSlot[] slot = annotation.slot();
                ResourceLocation registerName = new ResourceLocation(modId, name);

                boolean isExtended= ReflectionUtil.isExtendFrom(c, Enchantment.class);
                boolean isRegistered= EnchantmentManager.containEnchantment(registerName);
                boolean canRegister= isExtended && !isRegistered;

                if(canRegister){
                    Constructor constructor = c.getConstructor(Enchantment.Rarity.class, EnumEnchantmentType.class, EntityEquipmentSlot[].class);
                    Enchantment enchantment =(Enchantment) constructor.newInstance(rarity, type, slot);
                    EnchantmentManager.registerEnchantment(enchantment);
                    logger.debug("附魔:"+modId+":"+name+"注册成功!");
                    success++;
                }else if(!isExtended){
                    error++;
                    AnnotationFactory.throwException(logger,errorType,registerName,"附魔应为"+Enchantment.class.getName()+"的子类",c);
                }else if(isRegistered){
                    error++;
                    AnnotationFactory.throwException(logger,errorType,registerName,registerName+"名称已被注册",c);
                }
            }
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private static void loadFromField(Reflections reflections){
        try {
            Set<Field> fieldsAnnotatedWith = reflections.getFieldsAnnotatedWith(MinecraftEnchantment.class);
            for(Field field:fieldsAnnotatedWith){
                field.setAccessible(true);
                MinecraftEnchantment annotation = field.getAnnotation(MinecraftEnchantment.class);
                String modId = annotation.modId();
                String name = annotation.name();
                ResourceLocation registerName = new ResourceLocation(modId, name);

                boolean isExtended=ReflectionUtil.isExtendFrom(field.getType(), Block.class);
                boolean isStatic=Modifier.isStatic(field.getModifiers());
                boolean isRegistered=EnchantmentManager.containEnchantment(registerName);
                boolean canRegister=isExtended && isStatic && !isRegistered;
                Class DeclaringClass=field.getDeclaringClass();

                if(canRegister){
                    Object object = field.get(field.getDeclaringClass());
                    boolean isNull=(object==null);
                    if(!isNull){
                        Enchantment enchantment = (Enchantment) object;
                        enchantment.setRegistryName(new ResourceLocation(modId,name));
                        EnchantmentManager.registerEnchantment(enchantment);
                        logger.debug("附魔:"+modId+":"+name+"注册成功!");
                        success++;
                    }else{
                        error++;
                        AnnotationFactory.throwException(logger,errorType,registerName,"字段:"+field.getName()+"为Null",DeclaringClass,field.getName());
                    }
                }else if(!isExtended){
                    error++;
                    AnnotationFactory.throwException(logger,errorType,registerName,"附魔应为"+Enchantment.class.getName()+"的子类",DeclaringClass,field.getName());
                }else if(isRegistered){
                    error++;
                    AnnotationFactory.throwException(logger,errorType,registerName,registerName+"名称已被注册",DeclaringClass,field.getName());
                }else if(!isStatic){
                    AnnotationFactory.throwException(logger,errorType,registerName,"字段:"+field.getName()+"为非static",DeclaringClass,field.getName());
                }

            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
