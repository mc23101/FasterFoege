package ForgeAPI.Resources;

import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.SimpleReloadableResourceManager;
import net.minecraftforge.client.resource.IResourceType;
import net.minecraftforge.client.resource.ISelectiveResourceReloadListener;

import java.util.function.Predicate;

public class CustomResourceManger implements ISelectiveResourceReloadListener {
    @Override
    public void onResourceManagerReload(IResourceManager resourceManager, Predicate<IResourceType> resourcePredicate) {
        SimpleReloadableResourceManager Manager = (SimpleReloadableResourceManager) resourceManager;
        Manager.reloadResourcePack(CustomResourcePack.INSTANCE);
    }
}
