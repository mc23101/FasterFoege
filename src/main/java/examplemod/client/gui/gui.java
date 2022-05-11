package examplemod.client.gui;

import ForgeAPI.Utils.Image.ImageLoader;
import ForgeAPI.Widget.Impl.Frame;
import ForgeAPI.Widget.Impl.Image;
import ForgeAPI.Widget.Impl.TextField;
import net.minecraft.util.TabCompleter;

import java.io.File;
import java.io.IOException;

public class gui extends Frame {
    private TabCompleter tabCompleter;
    public TextField inputField;
    @Override
    public void drawBackground(int tint) {
        super.drawBackground(tint);
    }

    @Override
    public void initGui() {
        try {
            String url=this.getClass().getResource("/assets/mod/Weight/Button/test.png").getPath();
            File file = new File(url);
           // System.out.println(svgLoader.getDocument());
            //BufferedImage rasterize = SvgUtil.renderToImage(svgLoader.getDocument(),2000,2000);
            ImageLoader imageLoader = new ImageLoader(file);
            Image image=new Image(1,0,0,200,200,imageLoader);
            this.addGui(image);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
