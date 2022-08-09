package io.gitee.zhangsisiyao.ForgeAPI.Gui.Impl;

import io.gitee.zhangsisiyao.ForgeAPI.Gui.BaseGui;
import io.gitee.zhangsisiyao.ForgeAPI.Gui.ex.GuiBaseException;
import io.gitee.zhangsisiyao.ForgeAPI.Texture.GuiTextureLoader;
import io.gitee.zhangsisiyao.ForgeAPI.Texture.GuiTexturePos2D;
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

    /**
     * 图片加载器
     * */
    protected GuiTextureLoader imageGuiTextureLoader;

    /**图片的位置*/
    protected GuiTexturePos2D imageTexture;

    /**
     * @param x 进度条在屏幕上的横坐标X
     * @param y 进度条在屏幕上的纵坐标Y
     * @param width 按钮的宽度
     * @param height 按钮的高度
     * */
    public Image( int x, int y, int width, int height, GuiTextureLoader guiTextureLoader) {
        if(x<0||y<0) throw new GuiBaseException("x坐标或y坐标值小于0");
        if(width<0||height<0) throw new GuiBaseException("宽度width或高度height小于0");
        if(guiTextureLoader ==null) throw new NullPointerException("ImageLoader的值为null");
        this.x=x;
        this.y=y;
        this.width=width;
        this.height=height;
        this.imageGuiTextureLoader = guiTextureLoader;
        imageTexture=new GuiTexturePos2D(0,0, guiTextureLoader.getImageBuffer().getWidth(), guiTextureLoader.getImageBuffer().getHeight(), guiTextureLoader.getImageBuffer().getWidth(), guiTextureLoader.getImageBuffer().getHeight());
    }

    /**
     * {@inheritDoc}
     * */
    @Override
    public void drawGUI(int mouseX, int mouseY, float partialTicks) {
        if(this.visible){
            imageGuiTextureLoader.bindTexture();
            this.drawCustomSizedTexture(x,y,width,height,imageTexture);
        }
    }

    /**
     * 设置Image图片
     * @param guiTextureLoader 图片加载器
     * @param imageTexture 图片位置
     * */
    public void setImageTexture(GuiTextureLoader guiTextureLoader, GuiTexturePos2D imageTexture) {
        this.imageGuiTextureLoader = guiTextureLoader;
        this.imageTexture = imageTexture;
    }

    /**
     * {@inheritDoc}
     * */
    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        imageGuiTextureLoader.deleteTexture();
        imageGuiTextureLoader.stopGifTread();
    }

}
