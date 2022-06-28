package io.gitee.zhangsisiyao.ForgeAPI.Annotation.Loader;

import io.gitee.zhangsisiyao.ForgeAPI.Annotation.MinecraftItem;
import io.gitee.zhangsisiyao.ForgeAPI.MinecraftCore;
import net.minecraft.item.Item;
import org.reflections.Reflections;

import java.util.Set;

public class ItemLoader {
    public static void ItemAnnotationLoader(Object o){
        Package aPackage = o.getClass().getPackage();
        Reflections reflections=new Reflections(aPackage.getName());
        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(MinecraftItem.class);
        for(Class c:classes){
                try {
                    Item item = (Item) c.newInstance();
                    MinecraftCore.ItemManger.registerItems(item);
                    System.out.println(c.getSimpleName());
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
        }
    }
}
