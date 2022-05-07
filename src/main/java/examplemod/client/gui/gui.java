package examplemod.client.gui;

import ForgeAPI.Widget.Impl.BackGround;
import ForgeAPI.Widget.Impl.TextField;
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

    }


}
