package io.gitee.zhangsisiyao.ForgeAPI.Resources;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import io.gitee.zhangsisiyao.ForgeAPI.Utils.ResourcesUtil;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.client.resources.data.IMetadataSection;
import net.minecraft.client.resources.data.MetadataSerializer;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.Nullable;

import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


/**
 * 自定义资源包，用于存放加载的所有{@link CustomResource}
 * */
public class CustomResourcePack implements IResourcePack {
    public static final CustomResourcePack  INSTANCE= new CustomResourcePack();

    public Map<ResourceLocation, IResource> resources=new HashMap<>();

    public CustomResourcePack(){
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
        strings.add("custom");
        return strings;
    }

    @Nullable
    @Override
    public <T extends IMetadataSection> T getPackMetadata(MetadataSerializer metadataSerializer, String metadataSectionName) throws IOException {
        FileInputStream inputStream= (FileInputStream) ResourcesUtil.getResource("pack.mcmeta");

        JsonObject jsonobject;
        BufferedReader bufferedreader = null;

        try
        {
            bufferedreader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            jsonobject = (new JsonParser()).parse(bufferedreader).getAsJsonObject();
        }
        catch (RuntimeException runtimeexception)
        {
            throw new JsonParseException(runtimeexception);
        }
        finally
        {
            IOUtils.closeQuietly(bufferedreader);
        }

        return (T)metadataSerializer.parseMetadataSection(metadataSectionName, jsonobject);
    }

    @Override
    public BufferedImage getPackImage() {
        return null;
    }

    @Override
    public String getPackName() {
        return "custom";
    }
}
