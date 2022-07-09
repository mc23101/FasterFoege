package io.gitee.zhangsisiyao.ForgeAPI.Annotation.Loader;

import io.gitee.zhangsisiyao.ForgeAPI.Annotation.MinecraftItem;
import io.gitee.zhangsisiyao.ForgeAPI.MinecraftCore;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.reflections.Reflections;

import java.util.Set;

public class ItemLoader {

    private static Logger logger= LogManager.getLogger(ItemLoader.class);

    public static void ItemAnnotationLoader(Object o){

        logger.debug("注册物品中.........");

        Package aPackage = o.getClass().getPackage();
        Reflections reflections=new Reflections(aPackage.getName());
        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(MinecraftItem.class);
        for(Class c:classes){
                try {
                    MinecraftItem annotation = (MinecraftItem) c.getAnnotation(MinecraftItem.class);
                    String modId = annotation.modId();
                    String name=annotation.name();
                    Item item = (Item) c.newInstance();
                    item.setRegistryName(new ResourceLocation(modId,name));
                    MinecraftCore.ItemManger.registerItems(item);
                    logger.debug("物品:"+modId+":"+name+"注册成功!");
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
        }

        logger.debug("一共注册"+classes.size()+"个物品。");
    }
}
