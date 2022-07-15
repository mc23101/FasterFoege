package io.gitee.zhangsisiyao.ForgeAPI.Annotation.Loader;

import io.gitee.zhangsisiyao.ForgeAPI.Annotation.MinecraftItem;
import io.gitee.zhangsisiyao.ForgeAPI.MinecraftCore;
import io.gitee.zhangsisiyao.ForgeAPI.Utils.ReflectionUtil;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.reflections.Reflections;

import java.util.Set;

public class ItemLoader {

    private static Logger logger= LogManager.getLogger(ItemLoader.class);

    public static void ItemAnnotationLoader(Object o){

        logger.info("注册物品中.........");

        Package aPackage = o.getClass().getPackage();
        Reflections reflections=new Reflections(aPackage.getName());
        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(MinecraftItem.class);
        int success=0;
        int error=0;
        for(Class c:classes){
            MinecraftItem annotation = (MinecraftItem) c.getAnnotation(MinecraftItem.class);
            String modId = annotation.modId();
            String name=annotation.name();
            ResourceLocation location = new ResourceLocation(modId, name);
            if(ReflectionUtil.isExtendFrom(c,Item.class)&&!MinecraftCore.ItemManger.containItem(location)){
                try {
                    Item item = (Item) c.newInstance();
                    item.setRegistryName(location);
                    MinecraftCore.ItemManger.registerItems(item);
                    logger.debug("物品:"+modId+":"+name+"注册成功!");
                    success++;
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }else if(!ReflectionUtil.isExtendFrom(c,Item.class)){
                error++;
                logger.error("在"+c.getName()+"处的MinecraftItem注解使用错误,请将此注解作用在net.minecraft.block.Item的子类上!");
            }else if(MinecraftCore.ItemManger.containItem(location)){
                error++;
                logger.error("在"+c.getName()+"处的modId:"+modId+",name:"+name+"已经被注册!!!");
            }
        }

        logger.info(("一共注册"+classes.size()+"个物品。成功:"+success+"  失败:"+error));
    }
}
