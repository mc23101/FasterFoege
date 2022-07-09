package io.gitee.zhangsisiyao.ForgeAPI.Annotation.Loader;

import io.gitee.zhangsisiyao.ForgeAPI.Annotation.MinecraftEntity;
import io.gitee.zhangsisiyao.ForgeAPI.MinecraftCore;
import net.minecraft.util.ResourceLocation;
import org.reflections.Reflections;

import java.util.Set;

public class EntityLoader {
    public static void EntityAnnotationLoader(Object o){
        Package pack = o.getClass().getPackage();
        Reflections reflections=new Reflections(pack.getName());
        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(MinecraftEntity.class);
        for(Class c:classes){
            MinecraftEntity annotation = (MinecraftEntity) c.getAnnotation(MinecraftEntity.class);
            String modId = annotation.modId();
            String name = annotation.name();
            ResourceLocation registerName = new ResourceLocation(modId, name);
            int id = annotation.Id();
            int range = annotation.trackingRange();
            int frequency = annotation.updateFrequency();
            boolean updates = annotation.sendsVelocityUpdates();
            int primary = annotation.eggPrimary();
            int secondary = annotation.eggSecondary();
            MinecraftCore.EntityManger.registerEntity(registerName,c,name,id,o,range,frequency,updates,primary,secondary);
        }
    }

    public static void EntityRenderAnnotationLoader(Object o){

    }
}
