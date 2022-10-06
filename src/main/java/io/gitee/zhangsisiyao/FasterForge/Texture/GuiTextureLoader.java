package io.gitee.zhangsisiyao.FasterForge.Texture;

import io.gitee.zhangsisiyao.FasterForge.Manager.ResourceManager;
import io.gitee.zhangsisiyao.FasterForge.Utils.TextureUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class GuiTextureLoader extends AbstractTexture {

    private static Logger logger= LogManager.getLogger("ForgeFrame");
    
    private BufferedImage imageBuffer;
    private List<BufferedImage> gifFrames;
    private List<Integer> gifTimes;
    private Thread gifTread;
    private int gifFrameIndex=0;
    private IResource resource;
    private ResourceLocation location;


    public GuiTextureLoader(ResourceLocation location){
        try {
            this.location=location;
            if(ResourceManager.containResource(location)){
                resource =Minecraft.getMinecraft().getResourceManager().getResource(location);
                InputStream  stream=resource.getInputStream();
                ByteArrayOutputStream byteArrayOutputStream = TextureUtils.cloneInputStream(stream);
                InputStream inputStream1=new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
                InputStream inputStream2=new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
                InputStream inputStream3=new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
                InputStream inputStream4=new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
                if(TextureUtils.isGif(inputStream1)){
                    gifFrames=TextureUtils.getGifFrame(inputStream2);
                    gifTimes=TextureUtils.getGifTime(inputStream3);
                    startGifTread();
                }
                this.imageBuffer = TextureUtil.readBufferedImage(inputStream4);
                IOUtils.closeQuietly(stream,inputStream1,inputStream2,inputStream3,inputStream4);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadTexture(IResourceManager resourceManager) throws IOException {
        this.deleteTexture();
        if(this.imageBuffer!=null){
            TextureUtil.uploadTextureImageAllocate(this.getGlTextureId(), imageBuffer, false, true);
        }
    }


    public BufferedImage getImageBuffer() {
        return imageBuffer;
    }


    public void bindTexture(){
        try {
            this.loadTexture(Minecraft.getMinecraft().getResourceManager());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startGifTread(){
        long timeInterval = gifTimes.get(gifFrameIndex);
        Runnable runnable = () -> {
            while (true) {
                imageBuffer=gifFrames.get(gifFrameIndex);
                gifFrameIndex++;
                if(gifFrameIndex==gifFrames.size()){
                    gifFrameIndex=0;
                }
                try {
                    // sleep()：同步延迟数据，并且会阻塞线程
                    Thread.sleep(timeInterval);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        //创建定时器
        gifTread = new Thread(runnable);
        gifTread.setDaemon(true);
        //开始执行
        gifTread.start();
    }

    public void stopGifTread(){
        if(gifTread!=null){
            if(gifTread.isAlive()){
                this.gifTread.interrupt();
            }
        }
    }


    public int getTextureId(){
        return this.glTextureId;
    }

    /**
     * 删除textureId
     * 说明：删除textureId可以减少内存浪费，
     *      建议在onGuiClosed事件中调用，以便释放内存
     * */
    public  void deleteTexture() {
        if(this.getGlTextureId()!=-1){
            TextureUtil.deleteTexture(this.glTextureId);
        }
    }

    public ResourceLocation getResourceLocation() {
        return location;
    }
}
