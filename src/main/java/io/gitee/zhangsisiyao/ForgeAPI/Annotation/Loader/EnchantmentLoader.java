package io.gitee.zhangsisiyao.ForgeAPI.Annotation.Loader;

import io.gitee.zhangsisiyao.ForgeAPI.Annotation.MinecraftEnchantment;
import io.gitee.zhangsisiyao.ForgeAPI.MinecraftCore;
import io.gitee.zhangsisiyao.ForgeAPI.Utils.ReflectionUtil;
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

    private static Logger logger= LogManager.getLogger("ForgeFrame");

    private static int success=0;

    private static int error=0;

    public static void EnchantmentAnnotationLoader(Reflections reflections){
        logger.info("注册附魔属性中.........");


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

                boolean isExtended=ReflectionUtil.isExtendFrom(c, Enchantment.class);
                boolean isRegistered=MinecraftCore.EntityManger.containEntity(registerName);
                boolean canRegister= isExtended && !isRegistered;

                if(canRegister){
                    Constructor constructor = c.getConstructor(Enchantment.Rarity.class, EnumEnchantmentType.class, EntityEquipmentSlot[].class);
                    Enchantment enchantment =(Enchantment) constructor.newInstance(rarity, type, slot);
                    MinecraftCore.EnchantmentManger.registerEnchantment(enchantment);
                    logger.debug("附魔:"+modId+":"+name+"注册成功!");
                    success++;
                }else if(!isExtended){
                    error++;
                    logger.error("在"+c.getName()+"处的MinecraftEnchantment注解使用错误,请将此注解作用在net.minecraft.enchantment.Enchantment子类上!");
                }else if(isRegistered){
                    error++;
                    logger.error("在"+c.getName()+"处的"+modId+":"+name+"已经被注册!!!");
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
                boolean isRegistered=MinecraftCore.EnchantmentManger.containEnchantment(registerName);
                boolean canRegister=isExtended && isStatic && !isRegistered;

                if(canRegister){
                    Object object = field.get(field.getDeclaringClass());
                    boolean isNull=(object==null);
                    if(!isNull){
                        Enchantment enchantment = (Enchantment) object;
                        enchantment.setRegistryName(new ResourceLocation(modId,name));
                        MinecraftCore.EnchantmentManger.registerEnchantment(enchantment);
                        logger.debug("附魔:"+modId+":"+name+"注册成功!");
                        success++;
                    }else{
                        error++;
                        logger.error("在"+field.getDeclaringClass().getName()+"中的字段:"+field.getName()+"为null");
                    }
                }else if(!isExtended){
                    error++;
                    logger.error("在"+field.getDeclaringClass().getName()+"处的MinecraftEnchantment注解使用错误,请将此注解作用在类型为net.minecraft.enchantment.Enchantment类或其子类的字段上!");
                }else if(isRegistered){
                    error++;
                    logger.error("在"+field.getDeclaringClass().getName()+"处的"+modId+":"+name+"已经被注册!!!");
                }else if(!isStatic){
                    logger.error("在"+field.getDeclaringClass().getName()+"中的字段:"+field.getName()+"注解MinecraftEnchantment注解使用错误，请作用在static字段上.");
                }

            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
