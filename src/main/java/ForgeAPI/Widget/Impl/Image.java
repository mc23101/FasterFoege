package ForgeAPI.Widget.Impl;

import ForgeAPI.Utils.Texture.TextureLoader;
import ForgeAPI.Widget.BaseGui;
import ForgeAPI.Widget.TexturePos2D;
import ForgeAPI.Widget.ex.GuiBaseException;
/* =======================
||类名：Image
||状态：已完成
||作者：mc23
||最后一次修改时间：2022.5.15
==========================*/
/**
 * Gui控件：图片框
 * */
public class Image extends BaseGui {

    protected TextureLoader imageTextureLoader;
    protected TexturePos2D imageTexture;

    /**
     * @param id Gui的ID(可填写任意值，但不建议与其他Gui的值相同)
     * @param x 进度条在屏幕上的横坐标X
     * @param y 进度条在屏幕上的纵坐标Y
     * @param width 按钮的宽度
     * @param height 按钮的高度
     * */
    public Image(String id, int x, int y, int width, int height, TextureLoader textureLoader) {
        if(x<0||y<0) throw new GuiBaseException("x坐标或y坐标值小于0");
        if(width<0||height<0) throw new GuiBaseException("宽度width或高度height小于0");
        if(textureLoader ==null) throw new NullPointerException("ImageLoader的值为null");
        this.id=id;
        this.x=x;
        this.y=y;
        this.width=width;
        this.height=height;
        this.imageTextureLoader=textureLoader;
        imageTexture=new TexturePos2D(0,0, textureLoader.getImageBuffer().getWidth(), textureLoader.getImageBuffer().getHeight(), textureLoader.getImageBuffer().getWidth(), textureLoader.getImageBuffer().getHeight());
    }


    /**
     * {@inheritDoc}
     * */
    @Override
    public void drawGUI(int mouseX, int mouseY, float partialTicks) {
        imageTextureLoader.bindTexture();
        this.drawCustomSizedTexture(x,y,width,height,imageTexture);
    }

    public void setImageTexture(TexturePos2D imageTexture) {
        this.imageTexture = imageTexture;
    }

    /**
     * {@inheritDoc}
     * */
    @Override
    public void onGuiClosed() {
        imageTextureLoader.deleteTexture();
    }

}
