package ForgeAPI.Widget.Impl;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

@SuppressWarnings("all")
public class Slider extends Button {
    protected  ResourceLocation SLIDER_TEXTURES = new ResourceLocation("textures/gui/widgets.png");
    private float sliderPosition = 1.0F;
    public boolean isMouseDown;
    private final String name;
    private final float min;
    private final float max;
    protected String displayString;

    public Slider( String idIn, int x, int y, int width,int height,String nameIn, float minIn, float maxIn, float defaultValue) {
        super(idIn, x, y, width, height, "");
        this.name = nameIn;
        this.min = minIn;
        this.max = maxIn;
        this.sliderPosition = (defaultValue - minIn) / (maxIn - minIn);
    }

    @Override
    public void drawGUI( int mouseX, int mouseY, float partialTicks){
        long time=System.currentTimeMillis();
        FontRenderer fontrenderer = mc.fontRenderer;
        mc.getTextureManager().bindTexture(SLIDER_TEXTURES);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
        int i = this.getHoverState(this.hovered);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        //绘画滑条材质
        this.drawTexturedModalRect(this.x, this.y, 0, 46 + i * 20, this.width / 2, this.height);
        this.drawTexturedModalRect(this.x + this.width / 2, this.y, 200 - this.width / 2, 46 + i * 20, this.width / 2, this.height);
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


    public float getSliderValue()
    {
        return this.min + (this.max - this.min) * this.sliderPosition;
    }

    public void setSliderValue(float value, boolean notifyResponder)
    {
        this.sliderPosition = (value - this.min) / (this.max - this.min);
        this.displayString = this.getDisplayString();

    }

    public float getSliderPosition()
    {
        return this.sliderPosition;
    }

    private String getDisplayString()
    {
        return displayString;
    }

    protected int getHoverState(boolean mouseOver)
    {
        return 0;
    }


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
            this.drawTexturedModalRect(this.x + (int)(this.sliderPosition * (float)(this.width - 8)), this.y, 0, 100, 4, this.height);
            this.drawTexturedModalRect(this.x + (int)(this.sliderPosition * (float)(this.width - 8)) + 4, this.y, 196, 100, 4, this.height);
        }
    }

    public void setSliderPosition(float position)
    {
        this.sliderPosition = position;
        this.displayString = this.getDisplayString();
    }



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

    @Override
    public void mouseReleased(int mouseX, int mouseY,int mouseButton)
    {
        this.isMouseDown = false;
    }


}
