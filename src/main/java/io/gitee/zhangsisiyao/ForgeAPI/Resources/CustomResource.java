package io.gitee.zhangsisiyao.ForgeAPI.Resources;

import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.data.IMetadataSection;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 自定义资源,用于存放单个资源
 * */
public class CustomResource implements IResource {
    private InputStream inputStream;
    private ResourceLocation resourceLocation;
    private File file;
    private URL url;
    public CustomResource(ResourceLocation location,String absolutePath) {
        this.resourceLocation=location;
        this.file=new File(absolutePath);
    }

    public CustomResource(ResourceLocation location, URL url){
        this.resourceLocation=location;
        this.url=url;
    }

    public CustomResource(ResourceLocation location,File file){
        this.resourceLocation=location;
        this.file=file;
    }


    @Override
    public ResourceLocation getResourceLocation() {
        return resourceLocation;
    }

    @Override
    public InputStream getInputStream() {
        try {
            IOUtils.closeQuietly(inputStream);
            if(file!=null){
                this.inputStream=new FileInputStream(this.file);
            }
            if(url!=null){
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();//利用HttpURLConnection对象,我们可以从网络中获取网页数据.
                conn.setDoInput(true);
                conn.connect();
                this.inputStream = conn.getInputStream();

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
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
