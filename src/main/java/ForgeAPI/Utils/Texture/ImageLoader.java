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


    public static void bindTexture(int textureId) {
        GlStateManager.bindTexture(textureId);
    }

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

    public static void deleteTexture(int textureId) {
        TextureUtil.deleteTexture(textureId);
    }
}
