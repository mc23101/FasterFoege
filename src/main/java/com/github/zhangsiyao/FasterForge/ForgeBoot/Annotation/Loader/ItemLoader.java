package com.github.zhangsiyao.FasterForge.ForgeBoot.Annotation.Loader;

import com.github.zhangsiyao.FasterForge.ForgeBoot.Manager.ItemManager;
import com.github.zhangsiyao.FasterForge.Minecraft.Annotation.MinecraftItem;
import com.github.zhangsiyao.FasterForge.MinecraftCore;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

import java.lang.reflect.Field;
import java.util.Set;

@SuppressWarnings("all")
public class ItemLoader extends AnnotationLoader{


    @Override
    public void loadFromClass() {
        try {
            Set<Class<?>> classes = MinecraftCore.reflection.getTypesAnnotatedWith(MinecraftItem.class);
            for(Class c:classes){
                if(!checkClass(c,Item.class,MinecraftItem.class)){
                    continue;
                }
                MinecraftItem annotation = (MinecraftItem) c.getAnnotation(MinecraftItem.class);
                String modId = annotation.modId();
                String name=annotation.name();
                ResourceLocation location = new ResourceLocation(modId, name);
                Item item = (Item) c.newInstance();
                item.setRegistryName(location);
                ItemManager.registerItem(item);
            }
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadFromField() {
        try {
            Set<Field> fieldsAnnotatedWith = MinecraftCore.reflection.getFieldsAnnotatedWith(MinecraftItem.class);
            for(Field field:fieldsAnnotatedWith){
                field.setAccessible(true);
                MinecraftItem annotation = field.getAnnotation(MinecraftItem.class);
                String modId = annotation.modId();
                String name=annotation.name();
                ResourceLocation location = new ResourceLocation(modId, name);
                Item item = (Item) field.get(field.getDeclaringClass());
                item.setRegistryName(location);
                ItemManager.registerItem(item);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadFromMethod() {

    }
}
