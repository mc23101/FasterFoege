package ForgeAPI.Widget.Impl;


import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.GuiLabel;

import java.lang.reflect.Field;

/**
 * Label无法修改文本框
 * */
@SuppressWarnings("all")
public class Label extends BaseGui {


    /**
     * 显示标签背景
     * */
    public void setlabelBgEnabled(){
        Class c=GuiLabel.class;
        Field labelBgEnabled = null;
        try {
            labelBgEnabled = c.getDeclaredField("labelBgEnabled");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        labelBgEnabled.setAccessible(true);
        try {
            labelBgEnabled.set(this,true);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void setBorderColor(int x){
        try{
            int a=0xFF000000+x;
            Class c=GuiLabel.class;
            Field BackColor = c.getDeclaredField("ulColor");
            BackColor.setAccessible(true);
            BackColor.set(this,a);
            BackColor=c.getDeclaredField("brColor");
            BackColor.setAccessible(true);
            BackColor.set(this,a);
        }catch(NoSuchFieldException e){
            e.printStackTrace();
        }catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    public void setBackColor(int x){
        try{
            int a=0xFF000000+x;
            Class c=GuiLabel.class;
            Field BackColor = c.getDeclaredField("backColor");
            BackColor.setAccessible(true);
            BackColor.set(this,a);
        }catch(NoSuchFieldException e){
            e.printStackTrace();
        }catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void drawGUI(int mouseX, int mouseY, float partialTicks) {

    }

    @Override
    public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
        return false;
    }

    @Override
    public boolean mousePressed(int mouseX, int mouseY) {
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
