package io.gitee.zhangsisiyao.ForgeAPI.Gui.Impl;

import io.gitee.zhangsisiyao.ForgeAPI.Gui.BaseGui;
import io.gitee.zhangsisiyao.ForgeAPI.Texture.GuiTextureLoader;
import io.gitee.zhangsisiyao.ForgeAPI.Texture.GuiTexturePos2D;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
/* =======================
||类名：Bar
||状态：已完成
||作者：mc23
||最后一次修改时间：2022.5.26
==========================*/
/**
 * Gui控件：按钮
 * */
@SuppressWarnings("all")
@SideOnly(Side.CLIENT)
public class Button extends BaseGui
{
    /**
     * 按钮显示的文本
     * */
    public String displayString;

    /**
     * 按钮是否启用，如果为false则按钮不能被操作
     * */
    public boolean enabled;

    /**
     * FML
     * */
    public int packedFGColour;

    /**
     * 按钮未聚焦时的颜色
     * */
    protected int buttonColor=0xBBFFFF;

    /**
     * 按钮聚焦时的颜色
     * */
    protected int buttonHorveredColor=0x96CDCD;

    /**
     * 按钮未聚焦时的材质位置
     * */
    protected GuiTexturePos2D texturePos;

    /**
     * 按钮聚焦时的材质位置
     * */
    protected GuiTexturePos2D hoveredTexturePos;

    /**
     * Gui边框的宽度
     * */
    protected int border=0;

    /**
     * Gui边框的颜色
     * */
    protected int brColor=0x00868B;

    /**
     * 使用自定义大小的按钮
     * @param Gui的ID(可填写任意值，但不建议与其他Gui的值相同)
     * @param x 按钮在屏幕上的横坐标X
     * @param y 按钮在屏幕上的纵坐标Y
     * @param widthIn 按钮的宽度
     * @param heightIn 按钮的高度
     * @param buttonText 按钮上显示的文本
     * */
    public Button(String buttonId, int x, int y, int widthIn, int heightIn, String buttonText)
    {
        this.width = 200;
        this.height = 20;
        this.enabled = true;
        this.visible = true;
        this.id = buttonId;
        this.x = x;
        this.y = y;
        this.width = widthIn;
        this.height = heightIn;
        this.displayString = buttonText;
        this.guiTextureLoader = new GuiTextureLoader(new ResourceLocation("textures/gui/widgets.png"));
        this.texturePos=new GuiTexturePos2D(0,66,200,20,256,256);
        this.hoveredTexturePos=new GuiTexturePos2D(0,86,200,20,256,256);

    }

    /**
     * {@inheritDoc}
     * */
    @Override
    public void drawGUI(int mouseX, int mouseY, float partialTicks) {
        if (this.visible)
        {
            FontRenderer fontrenderer = mc.fontRenderer;
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            if(this.guiTextureLoader.getGlTextureId()!=-1&&enableTexture){
                drawButtonTexture();
            }else{
                drawButtonColor();
            }
            this.mouseDragged(mouseX, mouseY,0);
            drawText();
        }
    }

    /**
     * 绘制按钮材质
     * */
    private void drawButtonTexture(){
        this.guiTextureLoader.bindTexture();
        GuiTexturePos2D guiTexturePos2D =(getHoverState()==1)?this.texturePos:this.hoveredTexturePos;
        this.drawCustomSizedTexture(this.x, this.y,this.width,this.height, guiTexturePos2D);
    }

    /**
     * 绘制按钮颜色
     * */
    private  void drawButtonColor(){
        int backWidth = this.width - this.border ;
        int backHeight = this.height - this.border ;
        int backX=this.x+this.border;
        int backY=this.y+this.border;
        int color=getHoverState()==1?this.buttonColor:this.buttonHorveredColor;
        drawRect(backX, backY, this.x + backWidth, this.y + backHeight, 0xFF000000+color);

        //绘制水平边框
        drawRect(this.x,this.y,this.x+this.width,backY,0xFF000000+this.brColor);

        drawRect(this.x,this.y+backHeight,this.x+this.width,this.y+this.height,0xFF000000+this.brColor);

        //绘制垂直边框
        drawRect(this.x,this.y,backX,this.y+this.height,0xFF000000+this.brColor);
        drawRect(this.x+backWidth,this.y,this.x+this.width,this.y+this.height,0xFF000000+this.brColor);
    }

    /**
     * 绘制按钮文本
     * */
    private void drawText(){
        int j = 14737632;

        if (packedFGColour != 0)
        {
            j = packedFGColour;
        }
        else
        if (!this.enabled)
        {
            j = 10526880;
        }
        else if (this.hovered)
        {
            j = 16777120;
        }
        this.drawCenteredString(this.fontRenderer, I18n.format(this.displayString), this.x + this.width / 2, this.y + (this.height - 8) / 2, j);
    }

    /**
     * 获取鼠标聚焦状态
     * @return 鼠标聚焦状态 0:未启用 1:未聚焦 2:聚焦
     * */
    protected int getHoverState()
    {
        int i = 1;

        if (!this.enabled)
        {
            i = 0;
        }
        else if (this.hovered)
        {
            i = 2;
        }

        return i;
    }

    /**
     * 判断Button是否为聚焦状态
     * @return Button聚焦状态
     * */
    public boolean isMouseOvered()
    {
        return this.hovered;
    }

    /**
     * 设置未聚焦状态的按钮材质位置
     * @param
     * @param texturePos 按钮未聚焦材质的位置
     * @param hoveredTexturePos 按钮聚焦材质的位置
     * */
    public void setTexturePos(GuiTexturePos2D texturePos, GuiTexturePos2D hoveredTexturePos) {
        this.texturePos = texturePos;
        this.hoveredTexturePos = hoveredTexturePos;
    }

    /**
     * 设置按钮上显示的文本
     * @param displayString  显示的文本
     * */
    public void setDisplayString(String displayString) {
        this.displayString = displayString;
    }

    /**
     * 设置按钮未聚焦的颜色
     * @param buttonColor 未聚焦按钮的RGB颜色
     * @param buttonHorveredColor 聚焦的按钮RGB颜色
     * */
    public void setButtonColor(int buttonColor,int buttonHorveredColor) {
        this.buttonColor = buttonColor;
        this.buttonHorveredColor=buttonHorveredColor;
    }

    /**
     * 设置Gui边框宽度
     * @param width 宽度
     * */
    public void setBorder(int width) {
        this.border = border;
    }

    /**
     * 设置边框颜色
     * @param brColor RGB颜色
     * */
    public void setBrColor(int brColor) {
        this.brColor = brColor;
    }
}


