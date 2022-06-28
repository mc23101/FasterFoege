package io.gitee.zhangsisiyao.ForgeAPI.Annotation.Loader;

import io.gitee.zhangsisiyao.ForgeAPI.Annotation.MinecraftBlock;
import io.gitee.zhangsisiyao.ForgeAPI.MinecraftCore;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import org.reflections.Reflections;

import java.util.Set;

public class BlockLoader {
    public static void BlockAnnotationLoader(Object o){
        Package pack = o.getClass().getPackage();
        Reflections reflections=new Reflections(pack.getName());
        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(MinecraftBlock.class);
        for(Class c:classes){
            try {
                MinecraftBlock annotation = (MinecraftBlock) c.getAnnotation(MinecraftBlock.class);
                String modId = annotation.modId();
                String name=annotation.name();
                if (modId.equals("")||name.equals("")){
                    throw new NullPointerException("modId和name不能为空");
                }
                Block block = (Block) c.newInstance();
                block.setRegistryName(new ResourceLocation(modId,name));
                MinecraftCore.ItemManger.registerBlocks(block);
                MinecraftCore.ItemManger.registerItems(new ItemBlock(block).setRegistryName(new ResourceLocation(modId,name)));
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
