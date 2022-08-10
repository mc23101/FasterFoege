package io.gitee.zhangsisiyao.ForgeAPI.Gui.Impl;

import io.gitee.zhangsisiyao.ForgeAPI.Texture.GuiTextureLoader;
import io.gitee.zhangsisiyao.ForgeAPI.Texture.GuiTexturePos2D;
import net.minecraft.util.ResourceLocation;
/* =======================
||类名：CheckBox
||状态：已完成
||作者：mc23
||最后一次修改时间：2022.5.26
==========================*/
/**
 * Gui控件：选择框
 * */
public class CheckBox extends Button{

    /**
     * CheckBox未选中的RGB颜色
     * */
    protected int color=0xFFFFFF;

    /**
     * CheckBox选中的RGB颜色
     * */
    protected int checkedColor=0x000000;

    /**
     * CheckBox的选中状态
     * */
    protected boolean checked =false;

    /**
     * 自定义大小的CheckBox
     * @param x 在屏幕上的横坐标X
     * @param y 在屏幕上的纵坐标Y
     * @param width 宽度
     * @param height 高度
     * */
    public CheckBox(String id,int x,int y,int width,int height){
        super(id,x,y,width,height,"");
        this.guiTextureLoader =new GuiTextureLoader(new ResourceLocation("textures/gui/widgets.png"));
    }

    /**
     * {@inheritDoc}
     * */
    @Override
    public void drawGUI(int mouseX, int mouseY, float partialTicks) {
        this.texturePos= (!this.checked)?
                new GuiTexturePos2D(0,0,20,20,64,64):
                new GuiTexturePos2D(0,20,20,20,64,64);
        this.hoveredTexturePos=(!this.checked)?
                new GuiTexturePos2D(20,0,20,20,64,64):
                new GuiTexturePos2D(20,20,20,20,64,64);
        super.drawGUI(mouseX, mouseY, partialTicks);
    }

    /**
     * {@inheritDoc}
     * */
    @Override
    public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
        this.checked =!this.checked;
        return true;
    }

    /**
     * 获取未选中时，CheckBox的RGB颜色
     * @return
     * */
    public int getColor() {
        return color;
    }

    /**
     * 设置未选中时，CheckBox的RGB颜色
     * @param color RGB颜色
     * */
    public void setColor(int color) {
        this.color = color;
    }

    /**
     * 获取选中时，CheckBox的RGB颜色
     * @return
     * */
    public int getCheckedColor() {
        return checkedColor;
    }

    /**
     * 设置选中时，CheckBox的RGB颜色
     * @param checkedColor RGB颜色
     * */
    public void setCheckedColor(int checkedColor) {
        this.checkedColor = checkedColor;
    }

    /**
     * CheckBox是否被选中
     * @return
     * */
    public boolean isChecked() {
        return checked;
    }

    /**
     * 设置CheckBox的选中状态
     * @param checked CheckBox的选中状态
     * */
    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
