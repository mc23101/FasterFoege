package ForgeAPI.Utils.Texture;

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

public class TextureLoader extends AbstractTexture {

    private BufferedImage imageBuffer;

    public TextureLoader(File file)  {
        try {
            this.imageBuffer = TextureUtil.readBufferedImage(new FileInputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public TextureLoader(BufferedImage image) {
        this.imageBuffer = image;
    }

    public TextureLoader(String urlValue)  {
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
        this.deleteGlTexture();
        TextureUtil.uploadTextureImageAllocate(this.getGlTextureId(), imageBuffer, false, true);
    }


    public BufferedImage getImageBuffer() {
        return imageBuffer;
    }

    @Override
    public int getGlTextureId() {
        return this.glTextureId;
    }

    public void bindTexture(){
        try {
            this.loadTexture(Minecraft.getMinecraft().getResourceManager());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除textureId
     * 说明：删除textureId可以减少内存浪费，
     *      建议在onGuiClosed事件中调用，以便释放内存
     * */
    public  void deleteTexture() {
        if(this.getGlTextureId()==-1){
            TextureUtil.deleteTexture(this.glTextureId);
        }
    }
}
