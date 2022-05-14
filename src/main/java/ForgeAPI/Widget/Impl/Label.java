package ForgeAPI.Widget.Impl;


import ForgeAPI.Widget.BaseGui;
import ForgeAPI.Widget.TexturePos2D;
import ForgeAPI.Widget.ex.GuiBaseException;
import com.google.common.collect.Lists;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;

import java.util.List;

/**
 * Label无法修改文本框
 * */
@SuppressWarnings("all")
public class Label extends BaseGui {
    private List<String> labels = Lists.<String>newArrayList();;
    private boolean centered=false;
    private boolean labelBgEnabled=true;

    private final int textColor=-1;
    private TexturePos2D backTexturePos;
    private int backColor=-1;
    private int ulColor=0x00868B;
    private int brColor=0x00868B;
    private int border=1;

    public Label(String id,int x, int y,int width, int height,  int textColor) {
        if(x<0||y<0) throw new GuiBaseException("x坐标或y坐标值小于0");
        if(width<0||height<0) throw new GuiBaseException("宽度width或高度height小于0");
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        this.id = id;
    }


    /**
     * 在标签中添加文本
     * @param value 添加的文本
     * */
    public void addLine(String value)
    {
        this.labels.add(I18n.format(value));
    }

    /**
     * 设置Label文本居中
     * */
    public void setCentered(boolean value)
    {
        this.centered = value;
    }

    /**
     * 绘制Label背景
     * */
    protected void drawLabelBackground()
    {
        if (this.labelBgEnabled)
        {
            if(this.textureId!=-1&&this.enableTexture){

            }else{
                int BackWidth = this.width - this.border ;
                int BackHeight = this.height - this.border ;
                int BackX=this.x+this.border;
                int BackY=this.y+this.border;
                drawRect(BackX, BackY, this.x + BackWidth, this.y + BackHeight, 0xFF000000+this.backColor);

                //绘制水平边框
                drawRect(this.x,this.y,this.x+this.width,BackY,0xFF000000+this.ulColor);

                drawRect(this.x,this.y+BackHeight,this.x+this.width,this.y+this.height,0xFF000000+this.ulColor);

                //绘制垂直边框
                drawRect(this.x,this.y,BackX,this.y+this.height,0xFF000000+this.brColor);
                drawRect(this.x+BackWidth,this.y,this.x+this.width,this.y+this.height,0xFF000000+this.brColor);

            }

        }
    }

    /**
     * 设置边界大小
     * @param value 大小
     * */
    public void setBroderSize(int value){
        this.border=value;
    }

    /**
     * 显示Label背景
     * @param value 代表颜色的整数型数值
     * 1代表黑色,-1代表白色
     * */
    public void setBackEnabled(boolean value){
        this.labelBgEnabled=value;
    }

    /**
     * 设置背景边界颜色
     * @param brColor 竖直边界颜色
     * @param ulColor 水平边界颜色
     * */
    public void setBorderColor(int brColor,int ulColor){
            this.brColor=brColor;
            this.ulColor=ulColor;
    }

    /**
     * 设置背景的颜色
     * @param value 代表颜色的整数型数值
     * */
    public void setBackColor(int value){
            this.backColor=value;
    }

    /**
     * {@inheritDoc}
     * */
    @Override
    public void drawGUI(int mouseX, int mouseY, float partialTicks) {
        if (this.visible)
        {
            int BackWidth = this.width - this.border ;
            int BackHeight = this.height - this.border ;
            int BackX=this.x+this.border;
            int BackY=this.y+this.border;
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            this.drawLabelBackground();
            int MaxLine=(this.height-2*border)/10;
            int LineCount=this.labels.size()<=MaxLine?this.labels.size():MaxLine;
            for (int k = 0; k < LineCount; ++k)
            {
                if (this.centered)
                {
                    this.drawCenteredString(this.fontRenderer, this.labels.get(k), BackX + (this.width-2*border) / 2, BackY+10*k, this.textColor);
                }
                else
                {
                    this.drawString(this.fontRenderer, this.labels.get(k), BackX, BackY+10*k, this.textColor);
                }
            }
        }
    }

}
