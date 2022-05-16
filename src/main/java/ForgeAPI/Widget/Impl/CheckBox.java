package ForgeAPI.Widget.Impl;

import ForgeAPI.Widget.ex.GuiBaseException;

public class CheckBox extends Button{
    /**
     * CheckBox的文本框
     * */
    protected Label label;
    public CheckBox(String id,int x,int y,int width,int height){
        super(id,x,y, Math.max(width, height), Math.max(width, height),"");
        if(x<0||y<0) throw new GuiBaseException("x坐标或y坐标值小于0");
        if(width<0||height<0) throw new GuiBaseException("宽度width或高度height小于0");
        this.id=id;
        this.x=x;
        this.y=y;
        this.width=width;
        this.height=height;
        this.label=new Label("label",x+(Math.max(width, height)),y,width-(Math.max(width, height)),height);
    }

}
