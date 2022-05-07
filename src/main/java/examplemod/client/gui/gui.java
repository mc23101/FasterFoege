package examplemod.client.gui;

import ForgeAPI.Widget.Impl.*;
import net.minecraft.util.TabCompleter;

public class gui extends BackGround {
    private TabCompleter tabCompleter;
    public TextField inputField;
    @Override
    public void drawBackground(int tint) {
        super.drawBackground(tint);
    }

    @Override
    public void initGui() {
        Bar bar=new Bar(40,50,10,182,5);
        this.addGui(bar);
        Button button=new Button(1,23,32,"的阿瓦达");
        this.addGui(button);
        Label  label=new Label(1,90,90,50,50,1);
        label.addLine("中文");
        this.addGui(label);
    }


}
