package com.github.zhangsiyao.FasterForge.ForgeBoot.Gui.Impl;

import com.github.zhangsiyao.FasterForge.ForgeBoot.Gui.BaseGui;
import com.github.zhangsiyao.FasterForge.ForgeBoot.Texture.GuiTexturePos2D;
import com.github.zhangsiyao.FasterForge.ForgeBoot.Gui.Exception.NullTextureException;
import com.github.zhangsiyao.FasterForge.ForgeBoot.Gui.Exception.NullTexturePositionException;
import com.github.zhangsiyao.FasterForge.ForgeBoot.Gui.Exception.TextureNotFoundException;
import net.minecraft.client.renderer.GlStateManager;

/**
 * Gui控件：滑动条
 * */
@SuppressWarnings("all")
public class Slider extends BaseGui {


    /**
     * 滑块的位置
     * */
    protected float sliderPosition = 1.0F;

    /**
     * 鼠标是否按下
     * */
    protected boolean isMouseDown;

    protected boolean isHovered;

    /**
     * 滑动条的最大值
     * */
    private float min = 0;

    /**
     * 滑动条的最小值
     * */
    private  float max = 0;

    private int buttonWidth=0;

    private int buttonHeight=0;


    /**
     * 滑动条显示的文本
     * */
    protected String displayString;

    protected int buttonColor=0xBBFFFF;

    protected int buttonHoveredColor=0x96CDCD;

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
    public Slider(String id,int x, int y, int width,int height,String displayString, float minIn, float maxIn, float defaultValue) {
        this.displayString=displayString;
        this.x=x;
        this.y=y;
        this.id=id;
        this.min = minIn;
        this.max = maxIn;
        this.width=width;
        this.height=height;
        this.buttonWidth= (int) (width*0.05);
        this.buttonHeight=height;
        this.sliderPosition = (defaultValue - minIn) / (maxIn - minIn);
    }

    /**
     * {@inheritDoc}
     * */
    @Override
    public void drawGUI( int mouseX, int mouseY, float partialTicks) throws NullTextureException, TextureNotFoundException, NullTexturePositionException {
        if(visible){
            this.hovered=mouseX>=this.x&&mouseX<=this.x+this.width&&mouseY>=this.y&&mouseY<=this.y+this.height;
            if(enableTexture){
                drawTexture();
            }else {
                drawColor();
            }
        }
    }

    private void drawColor(){
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.drawRect(this.x,this.y,this.x+this.width,this.y+this.height,0xFF000000+backgroundColor);
        int left=this.x + (int)(this.sliderPosition * (float)(this.width-this.buttonWidth));
        if(this.hovered){
            this.drawRect(left, this.y, left+this.buttonWidth,this.y+this.height , 0xFF000000+buttonHoveredColor);
        }else{
            this.drawRect(left, this.y, left+this.buttonWidth,this.y+this.height , 0xFF000000+buttonColor);
        }

    }

    private void drawTexture() throws NullTextureException, TextureNotFoundException, NullTexturePositionException {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        if(guiTextureLoader!=null){
            if(guiTextureLoader.getGlTextureId()!=-1){
                if(buttonTexturePos!=null&&buttonHoveredTexturePos!=null&&backgroundTexturePos!=null){
                    this.guiTextureLoader.bindTexture();
                    this.drawCustomSizedTexture(this.x,this.y,this.width,this.height,this.backgroundTexturePos);
                    if(this.hovered){
                        this.drawCustomSizedTexture(this.x + (int)(this.sliderPosition * (float)(this.width-this.buttonWidth)), this.y, this.buttonWidth,this.height , buttonHoveredTexturePos);
                    }else{
                        this.drawCustomSizedTexture(this.x + (int)(this.sliderPosition * (float)(this.width-this.buttonWidth)), this.y, this.buttonWidth,this.height , buttonTexturePos);
                    }
                }else {
                    throw new NullTexturePositionException();
                }
            }else {
                throw new TextureNotFoundException(guiTextureLoader.getResourceLocation());
            }
        }else{
            throw new NullTextureException();
        }


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
                this.sliderPosition = (float)(mouseX - (this.x + this.buttonWidth/2)) / (float)(this.width-this.buttonWidth);

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
        if(mouseX>=this.x&&mouseX<=this.x+this.width&&mouseY>=this.y&&mouseY<=this.y+this.height){
            this.sliderPosition = (float)(mouseX - (this.x + this.buttonWidth/2)) / (float)(this.width-this.buttonWidth);

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
        return false;
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
