package ForgeAPI.Widget.Impl;

import ForgeAPI.Utils.ResourcesUtil;
import ForgeAPI.Utils.Texture.TextureLoader;
import ForgeAPI.Utils.Texture.TexturePos2D;

import java.io.File;
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
     * CheckBox的文本框
     * */
    protected Label label;

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
    protected boolean state=false;

    /**
     * 自定义大小的CheckBox
     * @param id Gui的ID(可填写任意值，但不建议与其他Gui的值相同)
     * @param x 在屏幕上的横坐标X
     * @param y 在屏幕上的纵坐标Y
     * @param width 宽度
     * @param height 高度
     * */
    public CheckBox(String id,int x,int y,int width,int height){
        super(id,x,y,width,height,"");
        this.textureLoader=new TextureLoader(new File(ResourcesUtil.getResourcesPath("assets/texture/Weight/CheckBox/checkbox.png")));
    }

    /**
     * {@inheritDoc}
     * */
    @Override
    public void drawGUI(int mouseX, int mouseY, float partialTicks) {
        this.texturePos= (!this.state)?
                new TexturePos2D(0,0,20,20,64,64):
                new TexturePos2D(0,20,20,20,64,64);
        this.hoveredTexturePos=(!this.state)?
                new TexturePos2D(20,0,20,20,64,64):
                new TexturePos2D(20,20,20,20,64,64);
        super.drawGUI(mouseX, mouseY, partialTicks);
    }

    /**
     * {@inheritDoc}
     * */
    @Override
    public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
        this.state=!this.state;
        return true;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getCheckedColor() {
        return checkedColor;
    }

    public void setCheckedColor(int checkedColor) {
        this.checkedColor = checkedColor;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }
}
