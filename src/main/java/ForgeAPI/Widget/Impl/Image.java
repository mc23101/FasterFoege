package ForgeAPI.Widget.Impl;

import ForgeAPI.Utils.Image.ImageLoader;
import net.minecraft.client.audio.SoundHandler;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Image extends BaseGui{

    protected int textureId;

    public Image(int id,int x,int y,int width,int height,String url) throws IOException {
        this.id=id;
        this.x=x;
        this.y=y;
        this.width=width;
        this.height=height;
        this.textureId= ImageLoader.loadTexture(url);
    }

    public Image(int id, int x, int y, int width, int height, File file) throws IOException {
        this.id=id;
        this.x=x;
        this.y=y;
        this.width=width;
        this.height=height;
        this.textureId= ImageLoader.loadTexture(file);
    }

    public Image(int id, int x, int y, int width, int height, BufferedImage image) throws IOException {
        this.id=id;
        this.x=x;
        this.y=y;
        this.width=width;
        this.height=height;
        this.textureId= ImageLoader.loadTexture(image);
    }


    @Override
    public void drawGUI(int mouseX, int mouseY, float partialTicks) {
        ImageLoader.bindTexture(textureId);
        this.drawTexturedModalRect(x,y,0,0,width,height);
    }

    @Override
    public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
        return false;
    }

    @Override
    public boolean mousePressed(int mouseX, int mouseY) {
        return false;
    }

    @Override
    public void mouseDragged(int mouseX, int mouseY) {

    }

    @Override
    public void mouseReleased(int mouseX, int mouseY) {

    }

    @Override
    public void playPressSound(SoundHandler soundHandlerIn) {

    }

    @Override
    public void updateGUI() {

    }

    @Override
    public void KeyInput(char typedChar, int keyCode) {

    }

    @Override
    public void onGuiClosed() {
        ImageLoader.deleteTexture(textureId);
    }
}
