package ForgeAPI.Widget;

import ForgeAPI.Widget.Impl.Frame;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

@SuppressWarnings("all")
public abstract class BaseGui extends Gui implements IBaseGUI {
    protected Minecraft mc=Minecraft.getMinecraft();
    protected FontRenderer fontRenderer=Minecraft.getMinecraft().fontRenderer;
    protected boolean visible = true;
    protected boolean hovered;
    protected int id;
    protected int x;
    protected int y;
    protected int width;
    protected int height;

    protected Frame frame;

    protected int maxWidth;
    protected int maxHeight;

    public static void drawCustomSizedTexture(int x, int y, float u, float v, int width, int height, float textureWidth, float textureHeight)
    {
        float f = 1F / textureWidth;
        float f1 = 1F / textureHeight;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos((double)x, (double)(y + height), 0.0D).tex((double)(u * f), (double)((v + (float)height) * f1)).endVertex();
        bufferbuilder.pos((double)(x + width), (double)(y + height), 0.0D).tex((double)((u + (float)width) * f), (double)((v + (float)height) * f1)).endVertex();
        bufferbuilder.pos((double)(x + width), (double)y, 0.0D).tex((double)((u + (float)width) * f), (double)(v * f1)).endVertex();
        bufferbuilder.pos((double)x, (double)y, 0.0D).tex((double)(u * f), (double)(v * f1)).endVertex();
        tessellator.draw();
    }


    /**
     * {@inheritDoc}
     * */
    @Override
    public void drawGUI(int mouseX, int mouseY, float partialTicks) {

    }

    /**
     * {@inheritDoc}
     * */
    @Override
    public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
        return false;
    }

    /**
     * {@inheritDoc}
     * */
    @Override
    public boolean mousePressed(int mouseX, int mouseY) {
        return false;
    }

    /**
     * {@inheritDoc}
     * */
    @Override
    public void mouseDragged(int mouseX, int mouseY) {

    }

    /**
     * {@inheritDoc}
     * */
    @Override
    public void mouseReleased(int mouseX, int mouseY) {

    }

    /**
     * {@inheritDoc}
     * */
    @Override
    public void playPressSound(SoundHandler soundHandlerIn) {

    }

    /**
     * {@inheritDoc}
     * */
    @Override
    public void updateGUI() {

    }

    /**
     * {@inheritDoc}
     * */
    @Override
    public void KeyInput(char typedChar, int keyCode) {

    }

    /**
     * {@inheritDoc}
     * */
    @Override
    public void setResolution(int width, int height) {
        this.maxHeight =height;
        this.maxWidth =width;
    }

    /**
     * {@inheritDoc}
     * */
    @Override
    public void onGuiClosed() {

    }

    /**
     * {@inheritDoc}
     * */
    public int getGuiID(){
        return  id;
    }

    /**
     * {@inheritDoc}
     * */
    @Override
    public Frame getFrame() {
        return frame;
    }

    /**
     * {@inheritDoc}
     * */
    @Override
    public void setFrame(Frame frame) {
        this.frame = frame;
    }
}
