package examplemod.client.gui;

import ForgeAPI.Utils.Image.ImageLoader;
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

            ImageLoader imageLoader = new ImageLoader("https://cdn3-banquan.ituchong.com/weili/l/253783080657223739.jpeg");
            imageLoader.setImageSize(100,100);
            Image image=new Image(1,0,0,100,100,imageLoader);
            this.addGui(image);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
