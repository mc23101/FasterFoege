package examplemod.client.gui;

import ForgeAPI.Widget.Impl.BackGround;
import ForgeAPI.Widget.Impl.Image;
import ForgeAPI.Widget.Impl.TextField;
import net.minecraft.util.TabCompleter;

import java.io.IOException;

public class gui extends BackGround {
    private TabCompleter tabCompleter;
    public TextField inputField;
    @Override
    public void drawBackground(int tint) {
        super.drawBackground(tint);
    }

    @Override
    public void initGui() {
        try {
            Image image=new Image(10,10,200,200,"https://img2022.cnblogs.com/blog/2488947/202203/2488947-20220323193058289-703233879.png");
            this.addGui(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
