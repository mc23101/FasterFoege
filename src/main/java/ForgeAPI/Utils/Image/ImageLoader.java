package ForgeAPI.Utils.Image;

import net.coobird.thumbnailator.Thumbnails;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.IResourceManager;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;

public class ImageLoader extends AbstractTexture {

    private BufferedImage imageBuffer;
    private URL url;
    private File file;

    public ImageLoader(File file) throws IOException {
        this.imageBuffer = TextureUtil.readBufferedImage(new FileInputStream(file));
    }

    public ImageLoader(BufferedImage image) {
        this.imageBuffer = image;
    }

    public ImageLoader(String urlValue) throws IOException {
        URL url=new URL(urlValue);
        this.url=url;
        this.imageBuffer=ImageIO.read(url);
    }

    @Override
    public void loadTexture(IResourceManager resourceManager) throws IOException {
        this.deleteGlTexture();
        TextureUtil.uploadTextureImageAllocate(this.getGlTextureId(), imageBuffer, false, true);
    }

    public void setImageSize(int width,int height){
        try {
            if(this.imageBuffer!=null){
                this.imageBuffer= Thumbnails.of(this.imageBuffer).size(width,height).asBufferedImage();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void scaleImage(double scale){
        try {
            if(this.imageBuffer!=null){
                this.imageBuffer= Thumbnails.of(this.imageBuffer).scale(scale).asBufferedImage();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void scaleImage(double scaleWidth,double scaleHeight){
        try {
            if(this.imageBuffer!=null){
                this.imageBuffer= Thumbnails.of(this.imageBuffer).scale(scaleWidth,scaleHeight).asBufferedImage();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BufferedImage getImageBuffer() {
        return imageBuffer;
    }

    public URL getUrl() {
        return url;
    }

    public File getFile() {
        return file;
    }

}
