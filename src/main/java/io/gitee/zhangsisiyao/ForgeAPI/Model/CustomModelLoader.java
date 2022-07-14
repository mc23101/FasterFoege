package io.gitee.zhangsisiyao.ForgeAPI.Model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ItemLayerModel;
import net.minecraftforge.client.resource.IResourceType;

import java.util.function.Predicate;

public class CustomModelLoader implements ICustomModelLoader {

    @Override
    public void onResourceManagerReload(IResourceManager resourceManager) {

    }

    @Override
    public void onResourceManagerReload(IResourceManager resourceManager, Predicate<IResourceType> resourcePredicate) {
        ICustomModelLoader.super.onResourceManagerReload(resourceManager, resourcePredicate);
    }

    @Override
    public boolean accepts(ResourceLocation modelLocation) {
        if(modelLocation.getResourceDomain().equals("custom")){
            return true;
        }
        return false;
    }
    @Override
    public IModel loadModel(ResourceLocation modelLocation) {
        ImmutableList<ResourceLocation> build = ImmutableList.of(new ResourceLocation("custom","example"));
        return new ItemLayerModel(build);
    }
}
