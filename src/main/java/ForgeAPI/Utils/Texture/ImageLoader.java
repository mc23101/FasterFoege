package ForgeAPI.Utils.Texture;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.IResourceManager;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

public class ImageLoader extends AbstractTexture {

    private BufferedImage image;

    public ImageLoader(File file) throws FileNotFoundException, IOException {
        this.image = TextureUtil.readBufferedImage(new FileInputStream(file));
    }

    public ImageLoader(BufferedImage image) {
        this.image = image;
    }

    public ImageLoader(String urlValue) throws IOException {
        URL url=new URL(urlValue);
        this.image=ImageIO.read(url);
    }

    @Override
    public void loadTexture(IResourceManager resourceManager) throws IOException {
        this.deleteGlTexture();
        TextureUtil.uploadTextureImageAllocate(this.getGlTextureId(), image, false, false);
    }




    /**
     * 绑定Texture
     * @param textureId texture的ID,通过ImageLoader.loadTexture获取
     * */
    public static void bindTexture(int textureId) {
        GlStateManager.bindTexture(textureId);
    }

    /**
     * 获取图片的textureID
     * @param file File类型的图片文件
     * */
    public static int loadTexture(File file) throws IOException {
        ITextureObject texture = new ImageLoader(file);
        texture.loadTexture(Minecraft.getMinecraft().getResourceManager());
        return texture.getGlTextureId();
    }

    public static int loadTexture(BufferedImage image) throws IOException {
        ITextureObject texture = new ImageLoader(image);
        texture.loadTexture(Minecraft.getMinecraft().getResourceManager());
        return texture.getGlTextureId();
    }

    public static int loadTexture(String url) throws IOException {
        ITextureObject texture = new ImageLoader(url);
        texture.loadTexture(Minecraft.getMinecraft().getResourceManager());
        return texture.getGlTextureId();
    }

    public static void deleteTexture(int textureId) {
        TextureUtil.deleteTexture(textureId);
    }
}
