package ForgeAPI.Widget;


import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiLabel;

import java.lang.reflect.Field;

/**
 * Label无法修改文本框
 * */
@SuppressWarnings("all")
public class Label extends GuiLabel {
    public Label( int id, int x, int y, int height, int width, int textColor) {
        super(Minecraft.getMinecraft().fontRenderer, id, x,y,height, width, textColor);
    }


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
}
