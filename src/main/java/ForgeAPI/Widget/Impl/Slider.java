package ForgeAPI.Widget.Impl;

import ForgeAPI.Utils.Texture.TexturePos2D;
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
        this.texturePos=new TexturePos2D(0,46,200,20,256,256);
        this.hoveredTexturePos=new TexturePos2D(0,46,200,20,256,256);
    }

    @Override
    public void drawGUI( int mouseX, int mouseY, float partialTicks){
        super.drawGUI(mouseX,mouseY,partialTicks);
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


            int w= (this.width*0.05)<4? 4: (int) (this.width * 0.05);
            TexturePos2D texturePos2D = new TexturePos2D(20, 66, w, this.height, 256, 256);
            this.drawCustomSizedTexture(this.x + (int)(this.sliderPosition * (float)(this.width-w)), this.y, w,this.height ,texturePos2D);
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
