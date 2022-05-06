package ForgeAPI.Widget.Impl;

import ForgeAPI.Widget.IBaseGUI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public abstract class BaseGui extends Gui implements IBaseGUI {
    protected Minecraft mc=Minecraft.getMinecraft();
}
