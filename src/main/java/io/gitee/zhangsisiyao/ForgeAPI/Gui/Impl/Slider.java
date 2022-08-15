package io.gitee.zhangsisiyao.ForgeAPI.Gui.Impl;

import io.gitee.zhangsisiyao.ForgeAPI.Gui.BaseGui;
import io.gitee.zhangsisiyao.ForgeAPI.Gui.ex.NullTextureException;
import io.gitee.zhangsisiyao.ForgeAPI.Gui.ex.NullTexturePositionException;
import io.gitee.zhangsisiyao.ForgeAPI.Gui.ex.TextureNotFoundException;
import io.gitee.zhangsisiyao.ForgeAPI.Texture.GuiTexturePos2D;
import net.minecraft.client.renderer.GlStateManager;

/**
 * Gui控件：滑动条
 * */
public class Slider extends BaseGui {

    /**
     * 滑块的位置
     * */
    protected float sliderPosition = 1.0F;

    /**
     * 鼠标是否按下
     * */
    protected boolean isMouseDown;

    /**
     * 滑动条的最大值
     * */
    private float min = 0;

    /**
     * 滑动条的最小值
     * */
    private  float max = 0;

    /**
     * 滑动条显示的文本
     * */
    protected String displayString;

    protected int buttonColor=0;

    protected int buttonHoveredColor=0;

    protected int backgroundColor=0;


    protected GuiTexturePos2D buttonTexturePos;

    protected GuiTexturePos2D buttonHoveredTexturePos;

    protected GuiTexturePos2D backgroundTexturePos;



    /**
     * 自定义大小的滑动条
     * @param idIn Gui的ID(可填写任意值，但不建议与其他Gui的值相同)
     * @param x 在屏幕上的横坐标X
     * @param y 在屏幕上的纵坐标Y
     * @param width 宽度
     * @param height 高度
     * @param displayString 显示的文本
     * @param minIn 滑动条的最小值
     * @param maxIn 滑动条的最大值
     * @param defaultValue 滑动条的初始值
     * */
    public Slider(Frame frame,int x, int y, int width,int height,String displayString, float minIn, float maxIn, float defaultValue) {
        this.displayString=displayString;
        this.min = minIn;
        this.max = maxIn;
        this.sliderPosition = (defaultValue - minIn) / (maxIn - minIn);
        this.buttonTexturePos=new GuiTexturePos2D(0,46,200,20,256,256);
        this.buttonHoveredTexturePos=new GuiTexturePos2D(0,46,200,20,256,256);
    }

    /**
     * {@inheritDoc}
     * */
    @Override
    public void drawGUI( int mouseX, int mouseY, float partialTicks) throws NullTextureException, TextureNotFoundException, NullTexturePositionException {
        super.drawGUI(mouseX,mouseY,partialTicks);
    }

    private void drawColor(){

    }

    private void drawTexture(){

    }

    /**
     * 获取滑动条的数值
     * @return
     * */
    public float getSliderValue()
    {
        return this.min + (this.max - this.min) * this.sliderPosition;
    }

    /**
     * 设置滑动条的数值
     * @param value 数值
     * */
    public void setSliderValue(float value)
    {
        this.sliderPosition = (value - this.min) / (this.max - this.min);
    }

    /**
     * 获取滑块的位置
     * @return
     * */
    public float getSliderPosition()
    {
        return this.sliderPosition;
    }


    private String getDisplayString()
    {
        return displayString;
    }

    /**
     * {@inheritDoc}
     * */
    @Override
    public void mouseDragged(int mouseX, int mouseY,int mouseButton)
    {
        if (this.visible)
        {
            if (this.isMouseDown)
            {
                this.sliderPosition = (float)(mouseX - (this.x + 4)) / (float)(this.width - 8);

                if (this.sliderPosition < 0.0F)
                {
                    this.sliderPosition = 0.0F;
                }

                if (this.sliderPosition > 1.0F)
                {
                    this.sliderPosition = 1.0F;
                }

                this.displayString = this.getDisplayString();
            }

            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            //绘画滑块材质


            int w= (this.width*0.05)<4? 4: (int) (this.width * 0.05);
            GuiTexturePos2D guiTexturePos2D = new GuiTexturePos2D(20, 66, w, this.height, 256, 256);
            this.drawCustomSizedTexture(this.x + (int)(this.sliderPosition * (float)(this.width-w)), this.y, w,this.height , guiTexturePos2D);
        }
    }

    /**
     * 设置滑块的位置
     * @param position 滑块的位置
     * */
    public void setSliderPosition(float position)
    {
        this.sliderPosition = position;
        this.displayString = this.getDisplayString();
    }

    /**
     * {@inheritDoc}
     * */
    @Override
    public boolean mousePressed(int mouseX, int mouseY,int mouseButton) {
        this.sliderPosition = (float)(mouseX - (this.x + 4)) / (float)(this.width - 8);

        if (this.sliderPosition < 0.0F)
        {
            this.sliderPosition = 0.0F;
        }

        if (this.sliderPosition > 1.0F)
        {
            this.sliderPosition = 1.0F;
        }

        this.displayString = this.getDisplayString();
        this.isMouseDown = true;
        return true;

    }

    /**
     * {@inheritDoc}
     * */
    @Override
    public void mouseReleased(int mouseX, int mouseY,int mouseButton)
    {
        this.isMouseDown = false;
    }


}
