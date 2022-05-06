package ForgeAPI.Widget;

import net.minecraft.client.audio.SoundHandler;

public interface IBaseGUI {

    public void drawGUI(int mouseX, int mouseY, float partialTicks);

    public boolean mouseClicked(int mouseX, int mouseY, int mouseButton);

    public boolean mousePressed(int mouseX, int mouseY);

    public void mouseDragged( int mouseX, int mouseY);

    public void mouseReleased(int mouseX, int mouseY);

    public void playPressSound(SoundHandler soundHandlerIn);

    public void updateGUI();

    public void KeyInput(char typedChar, int keyCode);
}

