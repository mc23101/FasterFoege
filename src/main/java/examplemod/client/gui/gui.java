package examplemod.client.gui;

import ForgeAPI.Utils.Image.ImageLoader;
import ForgeAPI.Utils.ResourcesUtil;
import ForgeAPI.Widget.Impl.*;
import net.minecraft.util.TabCompleter;

import java.io.File;

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
        Bar bar=new Bar("bar",10,0,20,182,5);
        Button button=new Button("button",0,40,200,20,"sss");
        Label label=new Label("label",0,150,50,50);
        bar.setEnableTexture(true);
        button.setEnableTexture(true);
        ImageLoader imageLoader = new ImageLoader(new File(ResourcesUtil.getResourcesPath("assets/mod/textures/Weight/Button/test.png")));
        Image image=new Image("image",0,0,400,200,imageLoader);

        this.addGui(image);
        //this.addGui(button);
        //this.addGui(bar);
        //this.addGui(label);
    }


}
