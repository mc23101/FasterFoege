package ForgeAPI.Widget;

import net.minecraft.client.audio.SoundHandler;

public interface IBaseGUI {
    /**
     * 接口名称:drawGUI
     * 作用: 绘制当前GUI到桌面
     * @param mouseX 鼠标x坐标
     * @param mouseY 鼠标Y坐标
     * @param partialTicks 时间帧率
     * */
    public void drawGUI(int mouseX, int mouseY, float partialTicks);

    /**
     * 接口名称:mouseClicked
     * 作用: 鼠标点击事件
     * @param mouseX 鼠标x坐标
     * @param mouseY 鼠标Y坐标
     * @param mouseButton 点击类型
     * */
    public boolean mouseClicked(int mouseX, int mouseY, int mouseButton);

    /**
     * 接口名称:mousePressed
     * 作用: 鼠标按下事件
     * @param mouseX 鼠标x坐标
     * @param mouseY 鼠标Y坐标
     * */
    public boolean mousePressed(int mouseX, int mouseY);

    /**
     * 接口名称:mouseDragged
     * 作用: 鼠标拖动事件
     * @param mouseX 鼠标x坐标
     * @param mouseY 鼠标Y坐标
     * */
    public void mouseDragged( int mouseX, int mouseY);

    /**
     * 接口名称:mouseReleased
     * 作用: 鼠标释放事件
     * @param mouseX 鼠标x坐标
     * @param mouseY 鼠标Y坐标
     * */
    public void mouseReleased(int mouseX, int mouseY);

    /**
     * 接口名称:mouseReleased
     * 作用: 鼠标释放事件
     * @param soundHandlerIn 声音类
     * */
    public void playPressSound(SoundHandler soundHandlerIn);

    /**
     * 接口名称:updateGUI
     * 作用: 更新GUI
     * */
    public void updateGUI();

    /**
     * 接口名称:KeyInput
     * 作用: 键盘输入
     * @param typedChar 按键名称
     * @param keyCode 按键ASCII码
     * */
    public void KeyInput(char typedChar, int keyCode);
}

