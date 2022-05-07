package ForgeAPI.Widget.Impl;

import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SuppressWarnings("all")
@SideOnly(Side.CLIENT)
public  class Button extends BaseGui
{
    protected  ResourceLocation BUTTON_TEXTURES = new ResourceLocation("textures/gui/widgets.png");
    public String displayString;
    public boolean enabled;
    public int packedFGColour; //FML

    public Button(int buttonId, int x, int y, String buttonText)
    {
        this(buttonId, x, y, 200, 20, buttonText);
    }

    public Button(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText)
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
    }

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
            this.drawTexturedModalRect(this.x, this.y, 0, 46 + i * 20, this.width / 2, this.height);
            this.drawTexturedModalRect(this.x + this.width / 2, this.y, 200 - this.width / 2, 46 + i * 20, this.width / 2, this.height);
            this.mouseDragged(mouseX, mouseY);
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


    /**
     * 获取鼠标聚焦状态
     * @param mouseOver
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

    public boolean isMouseOvered()
    {
        return this.hovered;
    }

    public void drawButtonForegroundLayer(int mouseX, int mouseY)
    {
    }


    public int getButtonWidth()
    {
        return this.width;
    }

    public void setWidth(int width)
    {
        this.width = width;
    }

    @Override
    public void playPressSound(SoundHandler soundHandlerIn)
    {
        soundHandlerIn.playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
    }

    @Override
    public void updateGUI() {

    }

    @Override
    public void KeyInput(char typedChar, int keyCode) {

    }
}


