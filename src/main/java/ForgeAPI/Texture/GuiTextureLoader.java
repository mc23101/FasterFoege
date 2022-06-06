package ForgeAPI.Texture;

import ForgeAPI.Utils.TextureUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.IResourceManager;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class GuiTextureLoader extends AbstractTexture {
    
    private BufferedImage imageBuffer;
    private List<BufferedImage> gifFrames;
    private List<Integer> gifTimes;
    private Thread gifTread;
    private int gifFrameIndex=0;

    public GuiTextureLoader(File file)  {
        if(TextureUtils.isGif(file)){
            gifFrames=TextureUtils.getGifFrame(file);
            gifTimes=TextureUtils.getGifTime(file);
            startGifTread();
        }
        try {
            this.imageBuffer = TextureUtil.readBufferedImage(new FileInputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public GuiTextureLoader(BufferedImage image) {
        this.imageBuffer = image;
    }

    public GuiTextureLoader(String urlValue)  {
        URL url= null;
        try {
            url = new URL(urlValue);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            this.imageBuffer=ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @Override
    public void loadTexture(IResourceManager resourceManager) throws IOException {
        this.deleteTexture();
        TextureUtil.uploadTextureImageAllocate(this.getGlTextureId(), imageBuffer, false, true);
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
        Runnable runnable = new Runnable() {
            public void run() {
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
}
