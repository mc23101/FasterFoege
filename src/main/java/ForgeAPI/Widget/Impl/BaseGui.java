package ForgeAPI.Widget.Impl;

import ForgeAPI.Widget.IBaseGUI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;

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

    protected int maxWidth;
    protected int maxHeight;

    @Override
    public void setResolution(int width, int height) {
        this.maxHeight =height;
        this.maxWidth =width;
    }

    public int getGuiID(){
        return  id;
    }
}
