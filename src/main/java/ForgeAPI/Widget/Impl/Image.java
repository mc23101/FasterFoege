package ForgeAPI.Widget.Impl;

import ForgeAPI.Utils.Image.ImageLoader;
import ForgeAPI.Utils.Image.ImageUtil;
import ForgeAPI.Widget.BaseGui;

import java.io.IOException;

public class Image extends BaseGui {

    protected int textureId;
    protected int imageWidth;
    protected int imageHeight;

    public Image(int id, int x, int y, int width, int height, ImageLoader imageLoader) throws IOException {
        this.id=id;
        this.x=x;
        this.y=y;
        this.width=width;
        this.height=height;
        this.textureId= ImageUtil.loadTexture(imageLoader.getImageBuffer());
        this.imageWidth=imageLoader.getImageBuffer().getWidth();
        this.imageHeight=imageLoader.getImageBuffer().getHeight();
    }


    @Override
    public void drawGUI(int mouseX, int mouseY, float partialTicks) {
        ImageUtil.bindTexture(textureId);
        this.drawCustomSizedTexture(x,y,0,0,width,height,imageWidth,imageHeight);
    }


    @Override
    public void onGuiClosed() {
        ImageUtil.deleteTexture(textureId);
    }
}
