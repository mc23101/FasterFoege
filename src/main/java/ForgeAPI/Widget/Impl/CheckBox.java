package ForgeAPI.Widget.Impl;

import ForgeAPI.Widget.ex.GuiBaseException;

public class CheckBox extends Button{
    /**
     * CheckBox的文本框
     * */
    protected Label label;

    /**
     *
     * */
    protected int color=0xFFFFFF;

    protected int checkedColor=0x000000;

    protected boolean state=false;

    public CheckBox(String id,int x,int y,int width,int height){
        super(id,x,y, Math.min(width, height), Math.min(width, height),"");
        if(x<0||y<0) throw new GuiBaseException("x坐标或y坐标值小于0");
        if(width<0||height<0) throw new GuiBaseException("宽度width或高度height小于0");
        this.label=new Label("label",x+(Math.min(width, height)),y,width-(Math.min(width, height)),height);
        this.border= (int) (Math.min(width, height)*0.2);
        this.brColor=0xFFFFFF;
        this.enableTexture=false;
        setColor(color);
        label.setEnableLines(false);
        label.addLine("这是一个标签");

    }

    @Override
    public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        boolean f=(this.x<mouseX&&mouseX<this.x+this.width)&&(this.y<mouseY&&mouseY<this.y+this.height);
        System.out.println(mouseX);
        if(f){
            state = !state;
            setColor(state ? checkedColor : color);
        }
        return true;
    }

    @Override
    public void drawGUI(int mouseX, int mouseY, float partialTicks) {
        super.drawGUI(mouseX, mouseY, partialTicks);
        label.drawGUI(mouseX,mouseY,partialTicks);
    }

    public void setColor(int color){
        this.buttonColor=color;
        this.buttonHorveredColor=color;
    }
}
