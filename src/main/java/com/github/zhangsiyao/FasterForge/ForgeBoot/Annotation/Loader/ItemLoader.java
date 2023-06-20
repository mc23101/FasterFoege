package com.github.zhangsiyao.FasterForge.ForgeBoot.Annotation.Loader;

import com.github.zhangsiyao.FasterForge.ForgeBoot.Annotation.AnnotationLoader;
import com.github.zhangsiyao.FasterForge.ForgeBoot.Manager.ItemManager;
import com.github.zhangsiyao.FasterForge.Minecraft.Annotation.MinecraftItem;
import com.github.zhangsiyao.FasterForge.FasterForge;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

@SuppressWarnings("all")
@AnnotationLoader
public class ItemLoader extends AbstractLoader {


    @Override
    public void loadFromClass() {
        try {
            Set<Class<?>> classes = FasterForge.reflection.getTypesAnnotatedWith(MinecraftItem.class);
            System.out.println(classes.size());
            for(Class c:classes){
                if(!checkClass(c,Item.class,MinecraftItem.class)){
                    continue;
                }
                MinecraftItem annotation = (MinecraftItem) c.getAnnotation(MinecraftItem.class);
                Item item = (Item) c.newInstance();
                item=createItem(item,annotation);
                ItemManager.registerItem(item);
            }
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadFromField() {
        try {
            Set<Field> fieldsAnnotatedWith = FasterForge.reflection.getFieldsAnnotatedWith(MinecraftItem.class);
            for(Field field:fieldsAnnotatedWith){
                field.setAccessible(true);
                MinecraftItem annotation = field.getAnnotation(MinecraftItem.class);
                Item item = (Item) field.get(field.getDeclaringClass());
                item=createItem(item,annotation);
                ItemManager.registerItem(item);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadFromMethod() {
        try {
            Set<Method> methodsAnnotatedWith = FasterForge.reflection.getMethodsAnnotatedWith(MinecraftItem.class);
            for(Method method:methodsAnnotatedWith){
                if(!checkMethod(method,Item.class,MinecraftItem.class)){
                    continue;
                }
                method.setAccessible(true);
                MinecraftItem annotation = method.getAnnotation(MinecraftItem.class);
                Item item = (Item) method.invoke(null);
                item=createItem(item,annotation);
                ItemManager.registerItem(item);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
    private static Item createItem(Item item,MinecraftItem minecraftItem){
        String modId = minecraftItem.modId();
        String name=minecraftItem.name();
        ResourceLocation location = new ResourceLocation(modId, name);
        item.setRegistryName(location);
        return item;
    }
}
