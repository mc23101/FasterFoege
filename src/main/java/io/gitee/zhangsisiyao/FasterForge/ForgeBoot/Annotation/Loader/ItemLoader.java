package io.gitee.zhangsisiyao.FasterForge.ForgeBoot.Annotation.Loader;

import io.gitee.zhangsisiyao.FasterForge.ForgeBoot.Annotation.MinecraftItem;
import io.gitee.zhangsisiyao.FasterForge.Manager.ItemManager;
import io.gitee.zhangsisiyao.FasterForge.Utils.ReflectionUtil;
import net.minecraft.block.Block;
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

    private static final String errorType="物品";

    private static Logger logger= LogManager.getLogger("FasterForge");

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
                boolean isRegistered= ItemManager.containItem(location);
                boolean canRegister= isExtended && !isRegistered;

                if(canRegister){
                    Item item = (Item) c.newInstance();
                    item.setRegistryName(location);
                    ItemManager.registerItems(item);
                    logger.debug("物品:"+modId+":"+name+"注册成功!");
                    success++;
                }else if(!isExtended){
                    error++;
                    AnnotationFactory.throwException(logger,errorType,location,"物品应为"+ Block.class.getName()+"的子类",c);
                }else if(isRegistered){
                    error++;
                    AnnotationFactory.throwException(logger,errorType,location,location+"名称已被注册",c);
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
                boolean isRegistered= ItemManager.containBlock(location);
                boolean canRegister=isExtended && isStatic && !isRegistered;

                Class DeclaringClass=field.getDeclaringClass();

                if(canRegister){
                    Object object = field.get(field.getDeclaringClass());
                    boolean isNull=object==null;
                    if(!isNull){
                        Item item = (Item) object;
                        item.setRegistryName(location);
                        ItemManager.registerItems(item);
                        success++;
                    }else{
                        error++;
                        AnnotationFactory.throwException(logger,errorType,location,"字段:"+field.getName()+"为Null",DeclaringClass,field.getName());
                    }
                }else if(!isExtended){
                    error++;
                    AnnotationFactory.throwException(logger,errorType,location,"物品应为"+Item.class.getName()+"的子类",DeclaringClass,field.getName());
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
