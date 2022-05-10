package ForgeAPI.Widget.Impl;

import ForgeAPI.Utils.Image.ImageLoader;
import ForgeAPI.Utils.Image.ImageUtil;
import ForgeAPI.Widget.BaseGui;

import java.io.IOException;

public class Image extends BaseGui {

    protected int textureId;

        public Image(int id, int x, int y, int width, int height, ImageLoader imageLoader) throws IOException {
        this.id=id;
        this.x=x;
        this.y=y;
        this.width=width;
        this.height=height;
        this.textureId= ImageUtil.loadTexture(imageLoader.getImageBuffer());
    }


    @Override
    public void drawGUI(int mouseX, int mouseY, float partialTicks) {
        ImageUtil.bindTexture(textureId);
        this.drawModalRectWithCustomSizedTexture(x,y,0,0,width,height,width,height);
    }


    @Override
    public void onGuiClosed() {
        ImageUtil.deleteTexture(textureId);
    }
}
