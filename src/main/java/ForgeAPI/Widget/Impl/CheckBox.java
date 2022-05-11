package ForgeAPI.Widget.Impl;

import ForgeAPI.Widget.BaseGui;
import ForgeAPI.Widget.ex.GuiBaseException;

public class CheckBox extends BaseGui {
    protected Button CheckButton;
    protected Label Label;
    public CheckBox(int id,int x,int y,int width){
        if(x<0||y<0) throw new GuiBaseException("x坐标或y坐标值小于0");
        if(width<0||height<0) throw new GuiBaseException("宽度width或高度height小于0");
    }
    public CheckBox(int id,int x,int y,int width,int height){
        if(x<0||y<0) throw new GuiBaseException("x坐标或y坐标值小于0");
        if(width<0||height<0) throw new GuiBaseException("宽度width或高度height小于0");
    }

}
