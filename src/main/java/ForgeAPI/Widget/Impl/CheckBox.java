package ForgeAPI.Widget.Impl;

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
        super(id,x,y,width,height,"");
    }

}
