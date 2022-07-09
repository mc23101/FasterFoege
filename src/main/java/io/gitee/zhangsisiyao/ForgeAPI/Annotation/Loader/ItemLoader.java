package io.gitee.zhangsisiyao.ForgeAPI.Annotation.Loader;

import io.gitee.zhangsisiyao.ForgeAPI.Annotation.MinecraftItem;
import io.gitee.zhangsisiyao.ForgeAPI.MinecraftCore;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import org.reflections.Reflections;

import java.util.Set;

public class ItemLoader {
    public static void ItemAnnotationLoader(Object o){
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
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
        }
    }
}
