package com.github.zhangsiyao.FasterForge.ForgeBoot.Manager;

import net.minecraft.client.gui.GuiScreen;

import javax.annotation.Nullable;

public class GuiManager {
    /**
     *在客户端中打开Gui
     * @param guiScreen 继承GuiScreen的类
     * */
    public static void DisplayGui(@Nullable GuiScreen guiScreen){
        net.minecraft.client.Minecraft.getMinecraft().displayGuiScreen(guiScreen);
    }
}
