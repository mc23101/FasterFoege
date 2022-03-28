package com.example.examplemod.client.gui;

import ForgeAPI.Widget.Label;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiLabel;
import net.minecraft.client.gui.GuiScreen;

public class BackGround extends GuiScreen {
    @Override
    public void drawBackground(int tint) {
        super.drawBackground(tint);
    }

    @Override
    public void initGui() {
        GuiButton button=new GuiButton(5,5,5,"test");
        //this.addButton(button);
        Label label=new Label(1,5,10,50,50,1);
       // label.addLine("aaaadawdawd");
        label.setBackColor(0x191970);
        label.setlabelBgEnabled();
        this.addLabel(label.setCentered());
        super.initGui();
    }
    public void addLabel(GuiLabel Label){
        this.labelList.add(Label);
    }
    /**initGui
     [16:33:27] [Client thread/INFO] [STDOUT]: [com.example.examplemod.client.gui.BackGround:drawScreen:61]: drawScreen
     [16:33:27] [Client thread/INFO] [STDOUT]: [com.example.examplemod.client.gui.BackGround:drawWorldBackground:43]: drawWorldBackground
     [16:33:27] [Client thread/INFO] [STDOUT]: [com.example.examplemod.client.gui.BackGround:drawDefaultBackground:37]: drawDefaultBackground
     */
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        //this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}
