package com.github.zhangsiyao.FasterForge.ForgeBoot.Resources;

import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.client.resources.data.IMetadataSection;
import net.minecraft.client.resources.data.MetadataSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.Nullable;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


/**
 * 自定义资源包，用于存放加载的所有{@link BaseResource}
 * */
@SideOnly(Side.CLIENT)
public class BaseResourcePack implements IResourcePack {

    public static final BaseResourcePack INSTANCE= new BaseResourcePack("ForgeFrame");

    public Map<ResourceLocation, IResource> resources=new HashMap<>();

    private String packName;

    public BaseResourcePack(String packName){
        this.packName=packName;
    }

    @Override
    public InputStream getInputStream(ResourceLocation location) {
        InputStream inputStream = resources.get(location).getInputStream();
        return inputStream;
    }

    @Override
    public boolean resourceExists(ResourceLocation location) {
        if(resources.containsKey(location)){
            return true;
        }
        return false;
    }

    @Override
    public Set<String> getResourceDomains() {
        HashSet<String> strings = new HashSet<>();
        strings.add(packName);
        return strings;
    }

    @Nullable
    @Override
    public <T extends IMetadataSection> T getPackMetadata(MetadataSerializer metadataSerializer, String metadataSectionName) throws IOException {
        return null;
    }

    @Override
    public BufferedImage getPackImage() {
        return null;
    }

    @Override
    public String getPackName() {
        return packName;
    }
}
