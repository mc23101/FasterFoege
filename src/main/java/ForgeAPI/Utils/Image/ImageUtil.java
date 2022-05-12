package ForgeAPI.Utils.Image;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;

public class ImageUtil {
    /**
     * 绑定Texture
     * @param textureId texture的ID,通过ImageLoader.loadTexture获取
     * */
    public static void bindTexture(int textureId) {
        GlStateManager.bindTexture(textureId);
    }

    /**
     * 获取图片的textureID
     * @param imageLoader 图片加载器
     * */
    public static int loadTexture(ImageLoader imageLoader) throws IOException {
        ITextureObject texture =imageLoader;
        texture.loadTexture(Minecraft.getMinecraft().getResourceManager());
        return texture.getGlTextureId();
    }

    public static int getResourceLocationTextureId(ResourceLocation resource){
        ITextureObject itextureobject = Minecraft.getMinecraft().getTextureManager().getTexture(resource);

        if (itextureobject == null||resource==null)
        {
            return -1;
        }

        return itextureobject.getGlTextureId();
    }

    /**
     * 删除textureId
     * 说明：删除textureId可以减少内存浪费，
     *      建议在onGuiClosed事件中调用，以便释放内存
     * */
    public static void deleteTexture(int textureId) {
        if(textureId!=-1){
            TextureUtil.deleteTexture(textureId);
        }
    }
}
