package ForgeAPI.Widget;

import net.minecraft.client.gui.GuiButton;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class Button extends GuiButton
{

    public Button(int buttonId, int x, int y, String buttonText) {
        super(buttonId, x, y, buttonText);
    }

    public Button(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
        super(buttonId, x, y, widthIn, heightIn, buttonText);
    }
}


