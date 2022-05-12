package ForgeAPI.Widget;

import ForgeAPI.Widget.Impl.Frame;
import net.minecraft.client.audio.SoundHandler;

public interface IBaseGUI {
    /**
     *  绘制当前GUI到桌面
     * @param mouseX 鼠标x坐标
     * @param mouseY 鼠标Y坐标
     * @param partialTicks 时间帧率
     * */
    public void drawGUI(int mouseX, int mouseY, float partialTicks);

    /**
     * 鼠标点击事件
     * @param mouseX 鼠标x坐标
     * @param mouseY 鼠标Y坐标
     * @param mouseButton 按键类型  0：鼠标左键，1：鼠标右键，3鼠标侧键下，4鼠标侧键上
     * */
    public boolean mouseClicked(int mouseX, int mouseY, int mouseButton);

    /**
     * 鼠标按下事件
     * @param mouseX 鼠标x坐标
     * @param mouseY 鼠标Y坐标
     * @param mouseButton 按键类型 0：鼠标左键，1：鼠标右键，3鼠标侧键下，4鼠标侧键上
     * */
    public boolean mousePressed(int mouseX, int mouseY,int mouseButton);

    /**
     * 鼠标拖动事件
     * @param mouseX 鼠标x坐标
     * @param mouseY 鼠标Y坐标
     * @param mouseButton 按键类型 0：鼠标左键，1：鼠标右键，3鼠标侧键下，4鼠标侧键上
     * */
    public void mouseDragged( int mouseX, int mouseY,int mouseButton);

    /**
     * 鼠标释放事件
     * @param mouseX 鼠标x坐标
     * @param mouseY 鼠标Y坐标
     * @param mouseButton 按键类型 0：鼠标左键，1：鼠标右键，3鼠标侧键下，4鼠标侧键上
     * */
    public void mouseReleased(int mouseX, int mouseY,int mouseButton );

    /**
     * 播放声音
     * @param soundHandlerIn 声音类
     * */
    public void playPressSound(SoundHandler soundHandlerIn);

    /**
     * 更新GUI事件
     * */
    public void updateGUI();

    /**
     * 键盘输入事件
     * @param typedChar 按键名称
     * @param keyCode 按键ASCII码
     * */
    public void KeyInput(char typedChar, int keyCode);

    /**
     * 设置窗口分辨率
     * @param width 宽
     * @param height 高
     * */
    public void setResolution(int width, int height);


    /**
     * Gui关闭事件
     * */
    public void onGuiClosed();


    /**
     * 获取Gui的id
     * */
    public int getGuiID();


    /**
     * 获取Gui的父窗口
     * */
    public Frame getFrame();

    /**
     * 设置Gui的父窗口
     * @param frame 父窗口
     * */
    public void setFrame(Frame frame);


}

