package ForgeAPI.Widget.Impl;


import com.google.common.collect.Lists;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;

import java.util.List;

/**
 * Label无法修改文本框
 * */
@SuppressWarnings("all")
public class Label extends BaseGui {
    private List<String> labels = Lists.<String>newArrayList();;
    private boolean centered;
    private boolean labelBgEnabled;
    private final int textColor;
    private int backColor;
    private int ulColor;
    private int brColor;
    private int border;

    public Label(FontRenderer fontRendererObj, int id, int x, int y, int width, int height, int textColor)
    {
        this.fontRenderer = fontRendererObj;
        this.id = id;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.centered = false;
        this.labelBgEnabled = false;
        this.textColor = textColor;
        this.backColor = -1;
        this.ulColor = 0x00868B;
        this.brColor = 0x00868B;
        this.border = 1;
    }

    public Label(int id,int x, int y,int width, int height,  int textColor) {
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        this.id = id;
        this.textColor = textColor;
        this.backColor = -1;
        this.ulColor = 0x00868B;
        this.brColor = 0x00868B;
        this.border = 1;
        this.centered = false;
        this.labelBgEnabled = false;
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
     * @param value 代表颜色的整数型数值
     * 1代表黑色,-1代表白色
     * */
    public void setBorderColor(int value){
            this.brColor=value;
    }

    /**
     * 设置背景的颜色
     * @param value 代表颜色的整数型数值
     * 1代表黑色,-1代表白色
     * */
    public void setBackColor(int value){
            this.backColor=value;
    }

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

    @Override
    public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
        return false;
    }

    @Override
    public boolean mousePressed(int mouseX, int mouseY) {
        return false;
    }

    @Override
    public void mouseDragged(int mouseX, int mouseY) {

    }

    @Override
    public void mouseReleased(int mouseX, int mouseY) {

    }

    @Override
    public void playPressSound(SoundHandler soundHandlerIn) {

    }

    @Override
    public void updateGUI() {

    }

    @Override
    public void KeyInput(char typedChar, int keyCode) {

    }

    @Override
    public void onGuiClosed() {

    }
}
