package ForgeAPI.Widget.Impl;

import ForgeAPI.Utils.Image.ImageLoader;
import ForgeAPI.Utils.Image.ImageUtil;
import ForgeAPI.Widget.BaseGui;
import ForgeAPI.Widget.ex.GuiBaseException;

import java.io.IOException;

public class Image extends BaseGui {

    protected int textureId;
    protected int imageWidth;
    protected int imageHeight;

    public Image(int id, int x, int y, int width, int height, ImageLoader imageLoader) throws IOException {
        if(x<0||y<0) throw new GuiBaseException("x坐标或y坐标值小于0");
        if(width<0||height<0) throw new GuiBaseException("宽度width或高度height小于0");
        if(imageLoader==null) throw new NullPointerException("ImageLoader的值为null");
        this.id=id;
        this.x=x;
        this.y=y;
        this.width=width;
        this.height=height;
        this.textureId= ImageUtil.loadTexture(imageLoader.getImageBuffer());
        this.imageWidth=imageLoader.getImageBuffer().getWidth();
        this.imageHeight=imageLoader.getImageBuffer().getHeight();
    }


    /**
     * {@inheritDoc}
     * */
    @Override
    public void drawGUI(int mouseX, int mouseY, float partialTicks) {
        ImageUtil.bindTexture(textureId);
        this.drawCustomSizedTexture(x,y,0,0,width,height,imageWidth,imageHeight);
    }

    /**
     * {@inheritDoc}
     * */
    @Override
    public void onGuiClosed() {
        ImageUtil.deleteTexture(textureId);
    }
}
