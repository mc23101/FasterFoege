package ForgeAPI.Widget.Impl;

import ForgeAPI.Utils.ResourcesUtil;
import ForgeAPI.Utils.Texture.TextureLoader;
import ForgeAPI.Widget.BaseGui;
import ForgeAPI.Widget.TexturePos2D;
import ForgeAPI.Widget.ex.GuiBaseException;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.File;
/* =======================
||类名：Bar
||状态：已完成
||作者：mc23
||最后一次修改时间：2022.5.15
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
    protected TexturePos2D texturePos;

    /**
     * 按钮聚焦时的材质位置
     * */
    protected TexturePos2D hoveredTexturePos;

    /**
     * Gui边框的宽度
     * */
    protected int border=0;

    /**
     * Gui边框的颜色
     * */
    protected int brColor=0x00868B;


    /**
     *  使用原版自带大小按钮
     * @param Gui的ID(可填写任意值，但不建议与其他Gui的值相同)
     * @param x 按钮在屏幕上的横坐标X
     * @param y 按钮在屏幕上的纵坐标Y
     * @param buttonText 按钮上显示的文本
     * */
    public Button(String buttonId, int x, int y, String buttonText)
    {
        this(buttonId, x, y, 200, 20, buttonText);
        TextureLoader textureLoader = new TextureLoader(new File(ResourcesUtil.getResourcesPath("assets/texture/Weight/Button/widgets.png")));
        this.textureLoader=textureLoader;
        this.texturePos=new TexturePos2D(0,66,200,20,256,256);
        this.hoveredTexturePos=new TexturePos2D(0,86,200,20,256,256);
    }

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
        if(x<0||y<0) throw new GuiBaseException("x坐标或y坐标值小于0");
        if(widthIn<0||heightIn<0) throw new GuiBaseException("宽度width或高度height小于0");
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
            int i = this.getHoverState();
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);

            //绘制按钮材质或按钮颜色
            if(i==1){
                if(this.textureLoader.getGlTextureId()!=-1&&enableTexture){
                    this.textureLoader.bindTexture();
                    this.drawCustomSizedTexture(this.x, this.y,texturePos );
                }else{
                    int BackWidth = this.width - this.border ;
                    int BackHeight = this.height - this.border ;
                    int BackX=this.x+this.border;
                    int BackY=this.y+this.border;
                    drawRect(BackX, BackY, this.x + BackWidth, this.y + BackHeight, 0xFF000000+this.buttonColor);

                    //绘制水平边框
                    drawRect(this.x,this.y,this.x+this.width,BackY,0xFF000000+this.brColor);

                    drawRect(this.x,this.y+BackHeight,this.x+this.width,this.y+this.height,0xFF000000+this.brColor);

                    //绘制垂直边框
                    drawRect(this.x,this.y,BackX,this.y+this.height,0xFF000000+this.brColor);
                    drawRect(this.x+BackWidth,this.y,this.x+this.width,this.y+this.height,0xFF000000+this.brColor);

                }
            }else if(i==2){
                if(this.textureLoader.getGlTextureId()!=-1&&enableTexture){
                    this.textureLoader.bindTexture();
                    this.drawCustomSizedTexture(this.x, this.y,hoveredTexturePos);
                }else{
                    int BackWidth = this.width - this.border ;
                    int BackHeight = this.height - this.border ;
                    int BackX=this.x+this.border;
                    int BackY=this.y+this.border;
                    drawRect(BackX, BackY, this.x + BackWidth, this.y + BackHeight, 0xFF000000+this.buttonHorveredColor);

                    //绘制水平边框
                    drawRect(this.x,this.y,this.x+this.width,BackY,0xFF000000+this.brColor);

                    drawRect(this.x,this.y+BackHeight,this.x+this.width,this.y+this.height,0xFF000000+this.brColor);

                    //绘制垂直边框
                    drawRect(this.x,this.y,BackX,this.y+this.height,0xFF000000+this.brColor);
                    drawRect(this.x+BackWidth,this.y,this.x+this.width,this.y+this.height,0xFF000000+this.brColor);

                }
            }
            this.mouseDragged(mouseX, mouseY,0);

            //绘制字体颜色
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
            this.drawCenteredString(fontrenderer, this.displayString, this.x + this.width / 2, this.y + (this.height - 8) / 2, j);
        }
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
     * @param texturePos 按钮材质的位置
     * */
    public void setTexturePos(TexturePos2D texturePos) {
        this.texturePos = texturePos;
    }

    /**
     * 设置聚焦状态的按钮材质位置
     * @param hoveredTexturePos 按钮材质位置
     * */
    public void setHoveredTexturePos(TexturePos2D hoveredTexturePos) {
        this.hoveredTexturePos = hoveredTexturePos;
    }

    public void setDisplayString(String displayString) {
        this.displayString = displayString;
    }

    /**
     * 设置按钮未聚焦的颜色
     * @param buttonColor RGB颜色
     * */
    public void setButtonColor(int buttonColor) {
        this.buttonColor = buttonColor;
    }

    /**
     * 设置按钮聚焦的颜色
     * @param buttonHorveredColor RGB颜色
     * */
    public void setButtonHorveredColor(int buttonHorveredColor) {
        this.buttonHorveredColor = buttonHorveredColor;
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


