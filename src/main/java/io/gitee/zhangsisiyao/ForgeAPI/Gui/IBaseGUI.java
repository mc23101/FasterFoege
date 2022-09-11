package io.gitee.zhangsisiyao.ForgeAPI.Gui;

import io.gitee.zhangsisiyao.ForgeAPI.Gui.Exception.NullTextureException;
import io.gitee.zhangsisiyao.ForgeAPI.Gui.Exception.NullTexturePositionException;
import io.gitee.zhangsisiyao.ForgeAPI.Gui.Exception.TextureNotFoundException;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.Gui;
/* =======================
||接口：IBaseGUI
||状态：已完成
||作者：mc23
||最后一次修改时间：2022.5.26
==========================*/
public interface IBaseGUI {
    /**
     *  绘制当前GUI到桌面
     * @param mouseX 鼠标x坐标
     * @param mouseY 鼠标Y坐标
     * @param partialTicks 时间帧率
     * */
    public void drawGUI(int mouseX, int mouseY, float partialTicks) throws NullTextureException, NullTexturePositionException, TextureNotFoundException;

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
     * 获取Gui的父窗口
     * */
    public Gui getFrame();

    /**
     * 设置Gui的父窗口
     * @param frame 父窗口
     * */
    public void setFrame(Gui frame);

    public String getId();

    public int getWidth();

    public int getHeight();

    public int getX();

    public int getY();
}

