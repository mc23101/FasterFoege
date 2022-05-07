package ForgeAPI.Widget.Impl;

import ForgeAPI.Widget.IBaseGUI;
import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

import java.io.IOException;
import java.util.List;

public abstract class BackGround extends GuiScreen {
    public Minecraft mc=Minecraft.getMinecraft();
    protected List<IBaseGUI> Guis= Lists.<IBaseGUI>newArrayList();
    public BackGround(){

    }

    @Override
    public void setWorldAndResolution(Minecraft mc, int width, int height) {
        super.setWorldAndResolution(mc, width, height);
        for(IBaseGUI gui: Guis){
            gui.setResolution(width,height);
        }
    }

    public <T extends IBaseGUI> T addGui(T Gui) {
        Guis.add(Gui);
        return Gui;
    }

    @Deprecated
    @Override
    protected <T extends GuiButton> T addButton(T buttonIn) {
        return null;
    }

    /**
     * 屏幕绘制事件
     * 绘制BackGround到屏幕
     * @param mouseX 鼠标的X轴位置
     * @param mouseY 鼠标的Y轴位置
     * @param partialTicks 未知参数
     * */
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        for(IBaseGUI gui : Guis){
            gui.drawGUI(mouseX,mouseY,partialTicks);
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }


    /**
     * 屏幕更新事件
     * 更新屏幕，打开时会一直调用updateScreen
     * 因此此函数大部分用来更新数据
     * */
    @Override
    public void updateScreen() {
        for(IBaseGUI gui:Guis){
            gui.updateGUI();
        }
        super.updateScreen();
    }

    /**
     * 键盘输入事件
     * 键盘输入时，会调用此方法
     * @param typedChar 输入的字符
     * @param keyCode 按键的编码(ASCII码)
     * */
    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        for(IBaseGUI gui:Guis) {
            gui.KeyInput(typedChar,keyCode);
        }
        super.keyTyped(typedChar,keyCode);
    }


    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
       super.mouseClicked(mouseX, mouseY, mouseButton);
       for(IBaseGUI gui: Guis){
           gui.mouseClicked(mouseX, mouseY,mouseButton);
       }
    }

    @Override
    public abstract void initGui();
}
