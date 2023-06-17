package com.github.zhangsiyao.FasterForge.ForgeBoot.Utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.SimpleReloadableResourceManager;
import net.minecraftforge.client.resource.ISelectiveResourceReloadListener;

public class ResourcePackUtil {

    /**
     * 注册资源包加载器
     * @param listener 资源包加载器,实现自{@link net.minecraftforge.client.resource.ISelectiveResourceReloadListener}接口的类
     * */
    public static void resisterResourcePackListener(ISelectiveResourceReloadListener listener){
        SimpleReloadableResourceManager resourceManager = (SimpleReloadableResourceManager)Minecraft.getMinecraft().getResourceManager();
        resourceManager.registerReloadListener(listener);
    }
}
