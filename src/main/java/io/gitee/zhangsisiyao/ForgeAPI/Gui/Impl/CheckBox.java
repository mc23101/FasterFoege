package io.gitee.zhangsisiyao.ForgeAPI.Gui.Impl;

/* =======================
||类名：CheckBox
||状态：已完成
||作者：mc23
||最后一次修改时间：2022.5.26
==========================*/

import io.gitee.zhangsisiyao.ForgeAPI.Gui.Exception.NullTextureException;
import io.gitee.zhangsisiyao.ForgeAPI.Gui.Exception.NullTexturePositionException;
import io.gitee.zhangsisiyao.ForgeAPI.Gui.Exception.TextureNotFoundException;
import io.gitee.zhangsisiyao.ForgeAPI.Texture.GuiTexturePos2D;

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

    protected GuiTexturePos2D pos;

    protected GuiTexturePos2D checkedPos;

    /**
     * 自定义大小的CheckBox
     * @param x 在屏幕上的横坐标X
     * @param y 在屏幕上的纵坐标Y
     * @param width 宽度
     * @param height 高度
     * */
    public CheckBox(String id,int x,int y,int width,int height){
        super(id,x,y,width,height,"");
    }

    /**
     * {@inheritDoc}
     * */
    @Override
    public void drawGUI(int mouseX, int mouseY, float partialTicks) throws NullTextureException, NullTexturePositionException, TextureNotFoundException {
        if(this.checked){
            this.buttonColor=checkedColor;
            this.buttonHorveredColor=checkedColor;
            this.texturePos=checkedPos;
            this.hoveredTexturePos=checkedPos;
        }else{
            this.buttonColor=color;
            this.buttonHorveredColor=color;
            this.texturePos=pos;
            this.hoveredTexturePos=pos;
        }
        super.drawGUI(mouseX, mouseY, partialTicks);
    }

    /**
     * {@inheritDoc}
     * */
    @Override
    public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if(this.isMouseInRange(mouseX,mouseY)){
            this.checked =!this.checked;
        }
        return false;
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

    @Override
    public void setTexturePos(GuiTexturePos2D texturePos, GuiTexturePos2D hoveredTexturePos) {
        this.pos=texturePos;
        this.checkedPos=hoveredTexturePos;
    }
}
