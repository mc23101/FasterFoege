package ForgeAPI;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

import javax.annotation.Nullable;

public class MinecraftClient {
    /**
     *在客户端中打开Gui
     *
     * @param guiScreen 继承GuiScreen的类
     * */
    public static void DisplayGui(@Nullable GuiScreen guiScreen){
        Minecraft.getMinecraft().displayGuiScreen(guiScreen);
    }
}
