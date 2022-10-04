package io.gitee.zhangsisiyao.ForgeAPI.Resources;

import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.SimpleReloadableResourceManager;
import net.minecraftforge.client.resource.IResourceType;
import net.minecraftforge.client.resource.ISelectiveResourceReloadListener;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.function.Predicate;


/**
 * 自定义资源加载器
 * */
@SideOnly(Side.CLIENT)
public class CustomResourceListener implements ISelectiveResourceReloadListener {
    @Override
    public void onResourceManagerReload(IResourceManager resourceManager, Predicate<IResourceType> resourcePredicate) {
        SimpleReloadableResourceManager Manager = (SimpleReloadableResourceManager) resourceManager;
        Manager.reloadResourcePack(CustomResourcePack.INSTANCE);
    }
}
