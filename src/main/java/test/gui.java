package test;

import com.SiYao.ForgeAPI.Gui.Impl.*;
import com.SiYao.ForgeAPI.Texture.GuiTextureLoader;
import com.SiYao.ForgeAPI.Utils.ResourcesUtil;
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
        Slider slider=new Slider("slider",0,0,200,20,"",0,100,44);
        Bar bar=new Bar("bar",10,0,20,300,100);
        Button button=new Button("button",0,40,40,10,"sss");
        Label label=new Label("label",0,0,50,50,9);
        label.setTextColor(0xADFF2F);
        label.setBackEnabled(true);
        label.setEnableTexture(false);
        bar.setEnableTexture(true);
        button.setEnableTexture(true);
        //button.setBorder(1);
        GuiTextureLoader guiTextureLoader = new GuiTextureLoader(new File(ResourcesUtil.getResourcesPath("assets/texture/gifDebug.gif")));
        Image image=new Image("image",0,0,400,200, guiTextureLoader);
//
        CheckBox checkBox=new CheckBox("checkbox",0,0,20,20);
        checkBox.setEnableTexture(true);

        label.setEnableLines(false);
        label.addLine("测试");

        this.addGui(slider);
        //this.addGui(checkBox);
        //this.addGui(image);
        //this.addGui(button);
        //this.addGui(bar);
        //this.addGui(label);
    }


}
