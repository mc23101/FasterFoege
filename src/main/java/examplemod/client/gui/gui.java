package examplemod.client.gui;

import ForgeAPI.Widget.Impl.Bar;
import ForgeAPI.Widget.Impl.Button;
import ForgeAPI.Widget.Impl.Frame;
import ForgeAPI.Widget.Impl.TextField;
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
        Bar bar=new Bar(10,10,0,20,182,1);
        Button button=new Button(3,0,40,"sss");
        bar.setEnableTexture(false);
        button.setEnableTexture(false);
        this.addGui(button);
        this.addGui(bar);
    }


}
