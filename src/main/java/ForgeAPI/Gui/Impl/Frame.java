package ForgeAPI.Gui.Impl;

import ForgeAPI.Gui.IBaseGUI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.opengl.GL11;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
/* =======================
||类名：Frame
||状态：已完成
||作者：mc23
||最后一次修改时间：2022.5.15
==========================*/
/**
 * 作为父窗体加载其他Gui控件
 * */
public abstract class Frame extends GuiScreen {

    /**
     * mc主类
     * */
    public Minecraft mc=Minecraft.getMinecraft();

    /**
     * Gui容器，用于存放所有Gui
     * */
    protected Map<String,IBaseGUI> Guis= new HashMap();

    /**
     * Gui的主窗口，用于响应Gui控件的事件
     * */
    public Frame(){
    }

    /**
     * 设置窗口分辨率事件
     * @param mc Minecraft类
     * @param width mc主窗口宽度
     * @param height mc主窗口高度
     * */
    @Override
    public void setWorldAndResolution(Minecraft mc, int width, int height) {
        super.setWorldAndResolution(mc, width, height);
        for(IBaseGUI gui: Guis.values()){
            gui.setResolution(width,height);
        }
    }

    /**
     * Gui关闭事件
     * 当Gui关闭时调用此事件
     * */
    @Override
    public void onGuiClosed() {
        for(IBaseGUI gui:Guis.values()){
            gui.onGuiClosed();
        }
    }

    /**
     * 在窗口中添加Gui
     * @param Gui 要添加的Gui
     * */
    public <T extends IBaseGUI> T addGui(T Gui) {
        Guis.put(Gui.getGuiID(),Gui);
        Gui.setFrame(this);
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
        GL11.glColor4f(1, 1, 1, 1);
        for(IBaseGUI gui:Guis.values()){
            gui.drawGUI(mouseX,mouseY,partialTicks);
        }
    }

    /**
     * 屏幕更新事件
     * 更新屏幕，打开时会一直调用updateScreen
     * 因此此函数大部分用来更新数据
     * */
    @Override
    public void updateScreen() {
        for(IBaseGUI gui:Guis.values()){
            gui.updateGUI();
        }
    }

    /**
     * 键盘输入事件
     * 键盘输入时，会调用此方法
     * @param typedChar 输入的字符
     * @param keyCode 按键的编码(ASCII码)
     * */
    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        for(IBaseGUI gui:Guis.values()) {
            gui.KeyInput(typedChar,keyCode);
        }
        super.keyTyped(typedChar,keyCode);
    }

    /**
     * 鼠标点击移动事件
     * 鼠标按住，并移动鼠标会调用此方法
     * @param mouseX 鼠标的X坐标
     * @param mouseY 鼠标的Y坐标
     * @param clickedMouseButton 鼠标按键类型  0：鼠标左键，1：鼠标右键，3鼠标侧键下，4鼠标侧键上
     * @param timeSinceLastClick 上一次鼠标点击到这一次鼠标点击的时间间隔
     * */
    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
        for(IBaseGUI gui:Guis.values()){
            gui.mouseDragged(mouseX,mouseY,clickedMouseButton);
        }
    }

    /**
     * 鼠标点击事件
     * 鼠标点击会调用此方法
     * @param mouseX 鼠标的X坐标
     * @param mouseY 鼠标的Y坐标
     * @param mouseButton 鼠标按键类型  0：鼠标左键，1：鼠标右键，3鼠标侧键下，4鼠标侧键上
     * */
    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
       for(IBaseGUI gui:Guis.values()){
           gui.mousePressed(mouseX,mouseY,mouseButton);
           gui.mouseClicked(mouseX, mouseY,mouseButton);
       }
    }

    /**
     * 鼠标释放事件
     * @param mouseX 鼠标的X坐标
     * @param mouseY 鼠标的Y坐标
     * @param mouseButton 鼠标按键类型  0：鼠标左键，1：鼠标右键，3鼠标侧键下，4鼠标侧键上
     * */
    @Override
    protected void mouseReleased(int mouseX, int mouseY,int mouseButton) {
        for (IBaseGUI gui:Guis.values()){
            gui.mouseReleased(mouseX,mouseY,mouseButton);
        }
    }

    /**
     * 窗口初始化事件
     * 如果需要添加Gui，则在此方法中添加
     * */
    @Override
    public abstract void initGui();



}
