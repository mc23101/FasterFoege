package com.SiYao.ForgeAPI.Resources;

import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.SimpleReloadableResourceManager;
import net.minecraftforge.client.resource.IResourceType;
import net.minecraftforge.client.resource.ISelectiveResourceReloadListener;

import java.util.function.Predicate;


/**
 * 自定义资源加载器
 * */
public class CustomResourceManger implements ISelectiveResourceReloadListener {
    @Override
    public void onResourceManagerReload(IResourceManager resourceManager, Predicate<IResourceType> resourcePredicate) {
        SimpleReloadableResourceManager Manager = (SimpleReloadableResourceManager) resourceManager;
        Manager.reloadResourcePack(CustomResourcePack.INSTANCE);
    }
}
