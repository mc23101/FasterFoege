package io.gitee.zhangsisiyao.ForgeAPI.Annotation.Loader;

import io.gitee.zhangsisiyao.ForgeAPI.Annotation.MinecraftItem;
import io.gitee.zhangsisiyao.ForgeAPI.Manager.ItemManger;
import io.gitee.zhangsisiyao.ForgeAPI.Utils.ReflectionUtil;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.reflections.Reflections;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Set;

@SuppressWarnings("all")
public class ItemLoader {

    private static Logger logger= LogManager.getLogger("ForgeFrame");

    private static int success=0;

    private static int error=0;

    public static void ItemAnnotationLoader(Reflections reflections){
        loadFromClass(reflections);
        loadFromField(reflections);
        logger.info(("一共注册"+(success+error)+"个物品。成功:"+success+"  失败:"+error));
    }

    private static void loadFromClass(Reflections reflections){
        try {
            Set<Class<?>> classes = reflections.getTypesAnnotatedWith(MinecraftItem.class);
            for(Class c:classes){
                MinecraftItem annotation = (MinecraftItem) c.getAnnotation(MinecraftItem.class);
                String modId = annotation.modId();
                String name=annotation.name();
                ResourceLocation location = new ResourceLocation(modId, name);

                boolean isExtended=ReflectionUtil.isExtendFrom(c,Item.class);
                boolean isRegistered= ItemManger.containItem(location);
                boolean canRegister= isExtended && !isRegistered;

                if(canRegister){
                    Item item = (Item) c.newInstance();
                    item.setRegistryName(location);
                    ItemManger.registerItems(item);
                    logger.debug("物品:"+modId+":"+name+"注册成功!");
                    success++;
                }else if(!isExtended){
                    error++;
                    logger.error("在"+c.getName()+"处的MinecraftItem注解使用错误,请将此注解作用在net.minecraft.block.Item的子类上!");
                }else if(isRegistered){
                    error++;
                    logger.error("在"+c.getName()+"处的"+modId+":"+name+"已经被注册!!!");
                }
            }
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private static void loadFromField(Reflections reflections){
        try {
            Set<Field> fieldsAnnotatedWith = reflections.getFieldsAnnotatedWith(MinecraftItem.class);
            for(Field field:fieldsAnnotatedWith){
                field.setAccessible(true);
                MinecraftItem annotation = field.getAnnotation(MinecraftItem.class);
                String modId = annotation.modId();
                String name=annotation.name();
                ResourceLocation location = new ResourceLocation(modId, name);
                boolean isExtended=ReflectionUtil.isExtendFrom(field.getType(), Item.class);
                boolean isStatic=Modifier.isStatic(field.getModifiers());
                boolean isRegistered=ItemManger.containBlock(location);

                boolean canRegister=isExtended && isStatic && !isRegistered;

                if(canRegister){
                    Object object = field.get(field.getDeclaringClass());
                    boolean isNull=object==null;
                    if(!isNull){
                        Item item = (Item) object;
                        item.setRegistryName(location);
                        ItemManger.registerItems(item);
                        success++;
                    }else{
                        error++;
                        logger.error("在"+field.getDeclaringClass().getName()+"中的字段:"+field.getName()+"对象为null");
                    }
                }else if(!isExtended){
                    error++;
                    logger.error("在"+field.getDeclaringClass().getName()+"处的MinecraftItem注解使用错误,请将此注解作用在类型为net.minecraft.item.Item类或其子类的字段上!");
                }else if(isRegistered){
                    error++;
                    logger.error("在"+field.getDeclaringClass().getName()+"处的"+modId+":"+name+"已经被注册!!!");
                }else if(!isStatic){
                    error++;
                    logger.error("在"+field.getDeclaringClass().getName()+"中的字段:"+field.getName()+"注解MinecraftItem注解使用错误，请作用在static字段上.");
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }


}
