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

    protected int MaxWidth;
    protected int MaxHeight;

    @Override
    public void setResolution(int width, int height) {
        this.MaxHeight=height;
        this.MaxWidth=width;
    }
}
