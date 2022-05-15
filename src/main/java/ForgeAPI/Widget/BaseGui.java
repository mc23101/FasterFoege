package ForgeAPI.Widget;

import ForgeAPI.Utils.Image.ImageLoader;
import ForgeAPI.Utils.Image.ImageUtil;
import ForgeAPI.Widget.Impl.Frame;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

/**
 * 所有Gui控件的父类，所有Gui都继承此类
 * */
@SuppressWarnings("all")
public abstract class BaseGui extends Gui implements IBaseGUI {
    /**
     * Mc主类
     * */
    protected Minecraft mc=Minecraft.getMinecraft();

    /**
     *  Gui字体信息
     * */
    protected FontRenderer fontRenderer=Minecraft.getMinecraft().fontRenderer;

    /**
     * Gui是否可以被看见
     * */
    protected boolean visible = true;

    /**
     * Gui是否为聚焦状态
     * */
    protected boolean hovered;

    /**
     * Gui的id信息
     * */
    protected String id;

    /**
     * Gui的横坐标X
     * */
    protected int x;

    /**
     * Gui的纵坐标Y
     * */
    protected int y;

    /**
     * Gui的宽度
     * */
    protected int width;

    /**
     * Gui的高度
     * */
    protected int height;

    /**
     * Gui的材质id
     * 由ImageUtil.loadTexture()方法获得
     * */
    protected int textureId=-1;

    /**
     *
     * */
    protected int maxTextureWidth;

    /**
     *
     * */
    protected int maxTextureHeight;

    /**
     * 是否启用绘制材质
     * 如果为true则绘制材质图片，如果为false则单纯使用颜色填充gui
     * */
    protected boolean enableTexture=true;

    /**
     * Gui的Frame父类
     * */
    protected Frame frame;

    /**
     * 屏幕的最大宽度
     * */
    protected int maxWidth;

    /**
     * 屏幕的最大高度
     * */
    protected int maxHeight;

    public static void drawCustomSizedTexture(int x, int y,TexturePos2D texturePos)
    {
        int width=texturePos.getWidth();
        int height=texturePos.getHeight();
        float f = 1F/ (float)texturePos.getMaxWidth();
        float f1 = 1F /(float)texturePos.getMaxHeight();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        float u= texturePos.getU();
        float v=texturePos.getV();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        //矩形点左下
        bufferbuilder.pos((double)x+0, (double)(y + height), 0.0D).tex((double)(u * f), (double)((v + (float)height) * f1)).endVertex();
        //矩形点右下
        bufferbuilder.pos((double)(x + width), (double)(y + height), 0.0D).tex((double)((u + (float)width) * f), (double)((v + (float)height) * f1)).endVertex();
        //矩形点右上
        bufferbuilder.pos((double)(x + width), (double)y+0, 0.0D).tex((double)((u + (float)width) * f), (double)(v * f1)).endVertex();
        //矩形点左上
        bufferbuilder.pos((double)x+0, (double)y+0, 0.0D).tex((double)(u * f), (double)(v * f1)).endVertex();
        tessellator.draw();
    }

    public static void drawCustomSizedImage(int x, int y,int width,int height,TexturePos2D texturePos)
    {
        float w=(float)texturePos.getWidth()/width;
        float h=(float)texturePos.getMaxHeight()/height;
        float f = w/ (float)texturePos.getMaxWidth();
        float f1 = h /(float)texturePos.getMaxHeight();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        float u= texturePos.getU();
        float v=texturePos.getV();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        //矩形点左下
        bufferbuilder.pos((double)x+0, (double)(y + height), 0.0D).tex((double)(u * f), (double)((v + (float)height) * f1)).endVertex();
        //矩形点右下
        bufferbuilder.pos((double)(x + width), (double)(y + height), 0.0D).tex((double)((u + (float)width) * f), (double)((v + (float)height) * f1)).endVertex();
        //矩形点右上
        bufferbuilder.pos((double)(x + width), (double)y+0, 0.0D).tex((double)((u + (float)width) * f), (double)(v * f1)).endVertex();
        //矩形点左上
        bufferbuilder.pos((double)x+0, (double)y+0, 0.0D).tex((double)(u * f), (double)(v * f1)).endVertex();
        tessellator.draw();
    }

    /**
     * 加载Gui的材质
     * */
    public void setTexture(ImageLoader loader) {
        ImageUtil.deleteTexture(this.textureId);
        this.textureId= ImageUtil.loadTexture(loader);
    }

    /**
     * {@inheritDoc}
     * */
    @Override
    public void drawGUI(int mouseX, int mouseY, float partialTicks) {

    }

    /**
     * {@inheritDoc}
     * */
    @Override
    public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
        return false;
    }

    /**
     * {@inheritDoc}
     * */
    @Override
    public boolean mousePressed(int mouseX, int mouseY,int mouseButton) {
        return false;
    }

    /**
     * {@inheritDoc}
     * */
    @Override
    public void mouseDragged(int mouseX, int mouseY,int mouseButton) {

    }

    /**
     * {@inheritDoc}
     * */
    @Override
    public void mouseReleased(int mouseX, int mouseY,int mouseButton) {

    }

    /**
     * {@inheritDoc}
     * */
    @Override
    public void playPressSound(SoundHandler soundHandlerIn) {

    }

    /**
     * {@inheritDoc}
     * */
    @Override
    public void updateGUI() {

    }

    /**
     * {@inheritDoc}
     * */
    @Override
    public void KeyInput(char typedChar, int keyCode) {

    }

    /**
     * {@inheritDoc}
     * */
    @Override
    public void setResolution(int width, int height) {
        this.maxHeight =height;
        this.maxWidth =width;
    }

    /**
     * {@inheritDoc}
     * */
    @Override
    public void onGuiClosed() {
        ImageUtil.deleteTexture(this.textureId);
        this.textureId=-1;
    }

    /**
     * {@inheritDoc}
     * */
    public String getGuiID(){
        return  id;
    }

    /**
     * {@inheritDoc}
     * */
    @Override
    public Frame getFrame() {
        return frame;
    }

    /**
     * {@inheritDoc}
     * */
    @Override
    public void setFrame(Frame frame) {
        this.frame = frame;
    }


    /**
     * Gui是否可视
     * */
    public boolean isVisible() {
        return visible;
    }

    /**
     * 设置Gui是否可视
     * @param visible Gui是否可视
     * */
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    /**
     * Gui是否为聚焦状态
     * */
    public boolean isHovered() {
        return hovered;
    }

    /**
     * 设置Gui的聚焦状态
     * @param hovered Gui的聚焦状态
     * */
    public void setHovered(boolean hovered) {
        this.hovered = hovered;
    }

    /**
     * 获取Gui的ID
     * */
    public String getId() {
        return id;
    }

    /**
     * 设置Gui的ID
     * */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取Gui的横坐标x
     * */
    public int getX() {
        return x;
    }

    /**
     * 设置Gui的横坐标x
     * @param x Gui的横坐标
     * */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * 获取Gui的纵坐标y
     * */
    public int getY() {
        return y;
    }

    /**
     * 设置Gui的纵坐标y
     * @param y Gui的纵坐标
     * */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * 获取Gui的宽度
     * */
    public int getWidth() {
        return width;
    }

    /**
     * 设置Gui的宽度
     * @param width Gui的宽度
     * */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * 获取Gui的高度
     * */
    public int getHeight() {
        return height;
    }

    /**
     * 设置Gui的高度
     * @param height Gui的高度
     * */
    public void setHeight(int height) {
        this.height = height;
    }


    /**
     * 是否启用材质
     * @param enableTexture 是否启用
     * true：Gui绘制材质，false：Gui绘制颜色
     * */
    public void setEnableTexture(boolean enableTexture) {
        this.enableTexture = enableTexture;
    }
}
