package com.example.examplemod.client.gui;

import ForgeAPI.Widget.BackGround;
import ForgeAPI.Widget.TextField;
import net.minecraft.util.TabCompleter;
import org.lwjgl.input.Keyboard;

public class gui extends BackGround {
    private TabCompleter tabCompleter;
    public TextField inputField;
    @Override
    public void drawBackground(int tint) {
        super.drawBackground(tint);
    }

    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        //Slider slider=new Slider(0,100,50,200,30,"1",0,100,0.1F);
        //this.addButton(slider);
        inputField = new TextField(1,  10, 10, 25, 40);
        inputField.setMaxStringLength(256);
        inputField.setFocused(true);
        inputField.setCanLoseFocus(true);
        this.addtestField(inputField);
        System.out.println("initGui");
        super.initGui();
    }


}
