package ForgeAPI.Widget.Impl;

import ForgeAPI.Widget.BaseGui;
import ForgeAPI.Widget.ex.GuiBaseException;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SuppressWarnings("all")
@SideOnly(Side.CLIENT)
public class Button extends BaseGui
{
    protected  ResourceLocation BUTTON_TEXTURES = new ResourceLocation("textures/gui/widgets.png");
    public String displayString;
    public boolean enabled;
    public int packedFGColour; //FML
    protected int textureX=0;
    protected int textureY=66;
    protected int hoveredTextureX=0;
    protected int getHoveredTextureY=86;


    public Button(int buttonId, int x, int y, String buttonText)
    {
        this(buttonId, x, y, 200, 20, buttonText);
    }

    public Button(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText)
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
            mc.getTextureManager().bindTexture(BUTTON_TEXTURES);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
            int i = this.getHoverState();
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            if(i==1){
                this.drawModalRectWithCustomSizedTexture(this.x, this.y, textureX, textureY, this.width , this.height,this.width,this.height);
            }else if(i==2){
                this.drawModalRectWithCustomSizedTexture(this.x, this.y, hoveredTextureX, getHoveredTextureY, this.width , this.height, this.width,this.height);
            }
            this.mouseDragged(mouseX, mouseY,0);
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
     * @param mouseOver
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
}


