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
        Bar bar=new Bar("bar",10,0,20,182,1);
        Button button=new Button("button",0,40,"sss");
        Label label=new Label("label",0,150,50,50,-1);
        bar.setEnableTexture(false);
        button.setEnableTexture(false);
        this.addGui(button);
        this.addGui(bar);
        this.addGui(label);
    }


}
