package ForgeAPI.Widget.Impl;


import ForgeAPI.Utils.Texture.TexturePos2D;
import ForgeAPI.Widget.BaseGui;
import ForgeAPI.Widget.ex.GuiBaseException;
import com.google.common.collect.Lists;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;

import java.util.List;
/* =======================
||类名：Label
||状态：已完成
||作者：mc23
||最后一次修改时间：2022.5.16
==========================*/
/**
 * Label无法修改文本框
 * */
@SuppressWarnings("all")
public class Label extends BaseGui {
    /**
     * Label文本，每一个元素代表一行文本
     * */
    protected List<String> labels = Lists.<String>newArrayList();

    /**
     * 是否启用文本居中
     * */
    protected boolean centered=false;

    /**
     * 是否绘制Label背景
     * */
    protected boolean labelBgEnabled=true;

    /**
     * Gui背景材质位置
     * */
    protected TexturePos2D backTexturePos;

    /**
     * 文本字体颜色
     * */
    protected int textColor=0x000000;

    /**
     * Gui背景颜色
     * */
    protected int backColor=0xFFFFFF;

    /**
     * Gui竖直边框颜色
     * */
    protected int brColor=0x00868B;

    /**
     * Gui边框宽度
     * */
    protected int border=1;

    /**
     * 是否启用多行
     * */
    protected boolean enableLines=true;

    /**
     * 字体大小
     * */
    protected int fontSize=9;

    /**
     * 使用自定义大小的Labal
     * @param Gui的ID(可填写任意值，但不建议与其他Gui的值相同)
     * @param x 在屏幕上的横坐标X
     * @param y 在屏幕上的纵坐标Y
     * @param width 宽度
     * @param height 高度
     * @param fontSize 文本大小
     * */
    public Label(String id,int x, int y,int width, int height,int fontSize) {
        if(x<0||y<0) throw new GuiBaseException("x坐标或y坐标值小于0");
        if(width<0||height<0) throw new GuiBaseException("宽度width或高度height小于0");
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        this.id = id;
        this.fontSize=fontSize;
    }

    /**
     * {@inheritDoc}
     * */
    @Override
    public void drawGUI(int mouseX, int mouseY, float partialTicks) {
        if (this.visible)
        {
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            this.drawLabelBackground();
            this.drawLabelText();
        }
    }


    /**
     * 绘制Label文本
     * */
    protected  void drawLabelText(){
        int BackWidth = this.width - this.border ;
        int BackHeight = this.height - this.border ;
        int BackX=this.x+this.border;
        int BackY=this.y+this.border;
        float OldfontSize=10;
        GlStateManager.scale(fontSize/OldfontSize,fontSize/OldfontSize,0);
        if(enableLines){
            int MaxLine=(this.height-2*border)/fontSize;
            int LineCount=this.labels.size()<=MaxLine?this.labels.size():MaxLine;
            for (int k = 0; k < LineCount; ++k)
            {
                int X=this.centered?BackX + (this.width-2*border) / 2: BackX;
                this.drawString(this.fontRenderer, this.labels.get(k),X , BackY+fontSize*k, this.textColor);
            }
        }else{
            int X=this.centered?BackX + (this.width-2*border) / 2: BackX;
            String str= this.labels.size()>0?this.labels.get(0):"";
            this.drawString(this.fontRenderer, str, X, BackY, this.textColor);
        }
    }


    /**
     * 绘制Label背景
     * */
    protected void drawLabelBackground()
    {
        if (this.labelBgEnabled)
        {
            if(enableTexture){
                if(this.textureLoader!=null){
                    if(this.textureLoader.getGlTextureId()!=-1){
                        this.textureLoader.bindTexture();
                        this.drawCustomSizedTexture(x,y,width,height,backTexturePos);
                    }
                }
            }else{
                int BackWidth = this.width - this.border ;
                int BackHeight = this.height - this.border ;
                int BackX=this.x+this.border;
                int BackY=this.y+this.border;
                drawRect(BackX, BackY, this.x + BackWidth, this.y + BackHeight, 0xFF000000+this.backColor);

                //绘制水平边框
                drawRect(this.x,this.y,this.x+this.width,BackY,0xFF000000+this.brColor);

                drawRect(this.x,this.y+BackHeight,this.x+this.width,this.y+this.height,0xFF000000+this.brColor);

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
     * */
    public void setBorderColor(int brColor){
            this.brColor=brColor;
    }

    /**
     * 设置背景的颜色
     * @param value 代表颜色的整数型数值
     * */
    public void setBackColor(int value){
            this.backColor=value;
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
     * @param value 是否居中
     * */
    public void setCentered(boolean value)
    {
        this.centered = value;
    }


    /**
     * 是否启用多行
     * @param enableLines 是否启用多行
     * */
    public void setEnableLines(boolean enableLines) {
        this.enableLines = enableLines;
    }

    /**
     * 设置文本颜色
     * @param textColor 文本颜色
     * */
    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }
    
    /**
     * 设置文本大小
     * @param value 字体大小
     * */
    public void setFontSize(int value){
        this.fontSize=value;
    }
}
