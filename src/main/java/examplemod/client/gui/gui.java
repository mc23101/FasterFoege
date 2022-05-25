package examplemod.client.gui;

import ForgeAPI.Widget.Impl.*;
import net.minecraft.util.TabCompleter;

public class gui extends Frame {
    private TabCompleter tabCompleter;
    public TextField inputField;
    @Override
    public void drawBackground(int tint) {
        super.drawBackground(tint);
    }

    @Override
    public void initGui() {
        //Slider slider=new Slider(1,0,0,200,20,"",0,100,44);
        Bar bar=new Bar("bar",10,0,20,300,100);
        Button button=new Button("button",0,40,40,10,"sss");
        Label label=new Label("label",0,0,50,50);
        label.setTextColor(0xADFF2F);
        bar.setEnableTexture(true);
        button.setEnableTexture(true);
        //button.setBorder(1);
//        TextureLoader textureLoader = new TextureLoader(new File(ResourcesUtil.getResourcesPath("assets/mod/textures/gui/test.png")));
//        Image image=new Image("image",0,0,400,200, textureLoader);
//
//        CheckBox checkBox=new CheckBox("checkbox",0,0,20,10);

        label.setEnableLines(false);
        label.addLine("测试");

        //this.addGui(checkBox);
        //this.addGui(image);
        this.addGui(button);
        //this.addGui(bar);
        //this.addGui(label);
    }


}
