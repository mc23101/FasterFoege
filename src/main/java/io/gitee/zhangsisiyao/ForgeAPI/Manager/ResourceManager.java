package io.gitee.zhangsisiyao.ForgeAPI.Manager;

import io.gitee.zhangsisiyao.ForgeAPI.MinecraftCore;
import io.gitee.zhangsisiyao.ForgeAPI.Resources.CustomResourcePack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.FallbackResourceManager;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.client.resources.SimpleReloadableResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.client.resource.ISelectiveResourceReloadListener;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
/**
 * 资源管理器
 * */
public class ResourceManager {
    /**
     * 注册自定义资源加载器
     * @param loader 实现{@link ISelectiveResourceReloadListener} 接口的类
     * */
    @SideOnly(Side.CLIENT)
    public static void registerCustomResourceManger(ISelectiveResourceReloadListener loader){
        SimpleReloadableResourceManager resourceManager = (SimpleReloadableResourceManager) net.minecraft.client.Minecraft.getMinecraft().getResourceManager();
        resourceManager.registerReloadListener(loader);
    }

    @SideOnly(Side.CLIENT)
    public static IResource getResource(ResourceLocation location){
        try {
            Class simpleReloadableResourceManagerClass = SimpleReloadableResourceManager.class;
            Field domainResourceManagers = simpleReloadableResourceManagerClass.getDeclaredField("domainResourceManagers");
            domainResourceManagers.setAccessible(true);
            Map<String, FallbackResourceManager> resourcePackMap = (Map<String, FallbackResourceManager>) domainResourceManagers.get(Minecraft.getMinecraft().getResourceManager());
            FallbackResourceManager fallbackResourceManager = resourcePackMap.get(MinecraftCore.MODID);

            return fallbackResourceManager.getResource(location);
        } catch (NoSuchFieldException | IOException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    @SideOnly(Side.CLIENT)
    public static boolean containResource(ResourceLocation location){
        boolean bool=false;
        try {
            Class simpleReloadableResourceManagerClass = SimpleReloadableResourceManager.class;
            Field domainResourceManagers = simpleReloadableResourceManagerClass.getDeclaredField("domainResourceManagers");
            domainResourceManagers.setAccessible(true);
            Map<String, FallbackResourceManager> resourcePackMap = (Map<String, FallbackResourceManager>) domainResourceManagers.get(Minecraft.getMinecraft().getResourceManager());
            FallbackResourceManager fallbackResourceManager = resourcePackMap.get(MinecraftCore.MODID);

            Class<FallbackResourceManager> fallbackResourceManagerClass = FallbackResourceManager.class;
            Field resourcePacksField = fallbackResourceManagerClass.getDeclaredField("resourcePacks");
            resourcePacksField.setAccessible(true);
            List<IResourcePack> resourcePacks = (List<IResourcePack>) resourcePacksField.get(fallbackResourceManager);
            for(IResourcePack resourcePack:resourcePacks){
                if(resourcePack.resourceExists(location)){
                    bool=true;
                    break;
                }
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return bool;
    }


    /**
     * 注册自定义模型加载器
     * @param customModelLoader 实现{@link ICustomModelLoader}接口的类
     * */
    @SideOnly(Side.CLIENT)
    public static void registerCustomModelLoder(ICustomModelLoader customModelLoader){
        ModelLoaderRegistry.registerLoader(customModelLoader);
    }

    /**
     * <p>注册自定义资源(纹理，模型等)</p>
     * @param location 资源在Minecraft中表示的位置
     * @param resource 资源
     * */
    @SideOnly(Side.CLIENT)
    public static void registerResource(ResourceLocation location, IResource resource){

        try {
            //获取Mod容器
            Class simpleReloadableResourceManagerClass = SimpleReloadableResourceManager.class;
            Field domainResourceManagers = simpleReloadableResourceManagerClass.getDeclaredField("domainResourceManagers");
            domainResourceManagers.setAccessible(true);
            Map<String, FallbackResourceManager> resourcePackMap = (Map<String, FallbackResourceManager>) domainResourceManagers.get(Minecraft.getMinecraft().getResourceManager());
            FallbackResourceManager fallbackResourceManager = resourcePackMap.get(MinecraftCore.MODID);

            //获取资源包
            Class<FallbackResourceManager> fallbackResourceManagerClass = FallbackResourceManager.class;
            Field resourcePacksField = fallbackResourceManagerClass.getDeclaredField("resourcePacks");
            resourcePacksField.setAccessible(true);
            List<IResourcePack> resourcePacks = (List<IResourcePack>) resourcePacksField.get(fallbackResourceManager);
            CustomResourcePack pack=null;
            for(IResourcePack resourcePack: resourcePacks){
                if(resourcePack.getPackName().equals("ForgeFrame")){
                    pack= (CustomResourcePack) resourcePack;
                    break;
                }
            }

            if(pack==null){
                pack=new CustomResourcePack("ForgeFrame");
                resourcePacks.add(pack);
            }

            //添加资源
            if(!pack.resourceExists(location)){
                pack.resources.put(location,resource);
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
