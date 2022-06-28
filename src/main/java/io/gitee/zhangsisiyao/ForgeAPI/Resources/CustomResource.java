package io.gitee.zhangsisiyao.ForgeAPI.Resources;

import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.data.IMetadataSection;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * 自定义资源,用于存放单个资源
 * */
public class CustomResource implements IResource {
    private InputStream inputStream;
    private ResourceLocation resourceLocation;
    private File file;
    public CustomResource(ResourceLocation location,String absolutePath) {
        this.resourceLocation=location;
        this.file=new File(absolutePath);
    }

    @Override
    public ResourceLocation getResourceLocation() {
        return resourceLocation;
    }

    @Override
    public InputStream getInputStream() {
        IOUtils.closeQuietly(inputStream);
        try {
            this.inputStream=new FileInputStream(this.file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return inputStream;
    }

    @Override
    public boolean hasMetadata() {
        return false;
    }

    @Nullable
    @Override
    public <T extends IMetadataSection> T getMetadata(String sectionName) {
       return null;
    }

    @Override
    public String getResourcePackName() {
        return "custom";
    }

    @Override
    public void close() {
        IOUtils.closeQuietly(this.inputStream);
    }
}
