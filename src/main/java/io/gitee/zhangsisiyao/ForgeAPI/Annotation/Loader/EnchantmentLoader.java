package io.gitee.zhangsisiyao.ForgeAPI.Annotation.Loader;

import io.gitee.zhangsisiyao.ForgeAPI.Annotation.MinecraftEnchantment;
import io.gitee.zhangsisiyao.ForgeAPI.MinecraftCore;
import io.gitee.zhangsisiyao.ForgeAPI.Utils.ReflectionUtil;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;

public class EnchantmentLoader {

    private static Logger logger= LogManager.getLogger();

    public static void EnchantmentAnnotationLoader(Object o){
        logger.info("注册附魔属性中.........");
        Package pack = o.getClass().getPackage();
        Reflections reflections=new Reflections(pack.getName());
        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(MinecraftEnchantment.class);
        int success=0;
        int error=0;
        for(Class c:classes){
            MinecraftEnchantment annotation = (MinecraftEnchantment) c.getAnnotation(MinecraftEnchantment.class);
            String modId = annotation.modId();
            String name = annotation.name();
            Enchantment.Rarity rarity = annotation.rarity();
            EnumEnchantmentType type = annotation.type();
            EntityEquipmentSlot[] slot = annotation.slot();
            ResourceLocation registerName = new ResourceLocation(modId, name);
            if(ReflectionUtil.isExtendFrom(c, Enchantment.class)&&!MinecraftCore.EnchantmentManger.containEnchantment(registerName)){
                try {
                    Constructor constructor = c.getConstructor(Enchantment.Rarity.class, EnumEnchantmentType.class, EntityEquipmentSlot[].class);
                    Enchantment enchantment =(Enchantment) constructor.newInstance(rarity, type, slot);
                    MinecraftCore.EnchantmentManger.registerEnchantment(enchantment);
                    logger.debug("附魔:"+modId+":"+name+"注册成功!");
                    success++;
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }else if(!ReflectionUtil.isExtendFrom(c,Enchantment.class)){
                error++;
                logger.error("在"+c.getName()+"处的MinecraftEnchantment注解使用错误,请将此注解作用在net.minecraft.entity.Enchantment的子类上!");
            }else if(MinecraftCore.EntityManger.containEntity(registerName)){
                error++;
                logger.error("在"+c.getName()+"处的modId:"+modId+",name:"+name+"已经被注册!!!");
            }

        }
        logger.info("一共注册"+classes.size()+"个附魔。成功:"+success+"  失败:"+error);
    }

    private static void loadFromClass(Reflections reflections){

    }

    private static void loadFromField(Reflections reflections){

    }

}
