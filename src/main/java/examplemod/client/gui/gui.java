package examplemod.client.gui;

import ForgeAPI.Widget.Impl.Frame;
import ForgeAPI.Widget.Impl.Slider;
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
        Slider slider=new Slider(1,0,0,200,20,"",0,100,44);
        this.addGui(slider);


    }


}
