package ForgeAPI.Widget.Impl;

import net.minecraft.client.audio.SoundHandler;
import net.minecraft.util.ResourceLocation;

public class Bar extends BaseGui {
    public float min;
    public float max;
    public float curr;
    public int width;
    public int height;
    public boolean visible;
    private ResourceLocation BAR_TEXTURES=new ResourceLocation("textures/gui/bars.png");
    public Bar(float min, float max, float curr, int width, int height) {
        this.min = min;
        this.max = max;
        this.curr = curr;
        this.width = width;
        this.height = height;
    }

    public void drawBar(){

    }


    @Override
    public void drawGUI(int mouseX, int mouseY, float partialTicks) {

    }

    @Override
    public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
        return false;
    }

    @Override
    public boolean mousePressed( int mouseX, int mouseY) {
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
}
