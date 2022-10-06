package io.gitee.zhangsisiyao.FasterForge.Gui;

import io.gitee.zhangsisiyao.FasterForge.Font.FontRender;
import io.gitee.zhangsisiyao.FasterForge.Gui.Exception.NullTextureException;
import io.gitee.zhangsisiyao.FasterForge.Gui.Exception.NullTexturePositionException;
import io.gitee.zhangsisiyao.FasterForge.Gui.Exception.TextureNotFoundException;
import io.gitee.zhangsisiyao.FasterForge.Texture.GuiTextureLoader;
import io.gitee.zhangsisiyao.FasterForge.Texture.GuiTexturePos2D;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
/* =======================
||类名：BaseGui
||状态：已完成
||作者：mc23
||最后一次修改时间：2022.5.26
==========================*/
/**
 * 所有Gui控件的父类，所有Gui都继承此类
 * */
@SuppressWarnings("all")
public abstract class BaseGui extends Gui implements IBaseGUI {
    public static final Logger logger= LogManager.getLogger("FasterForge");

    /**
     * Mc主类
     * */
    protected Minecraft mc=Minecraft.getMinecraft();

    /**
     *  Gui字体信息
     * */
    protected FontRender fontRenderer= FontRender.fontRender;

    /**
     * Gui的唯一id
     * */
    protected String id;

    /**
     * Gui是否可以被看见
     * */
    protected boolean visible = true;

    /**
     * Gui是否为聚焦状态
     * */
    protected boolean hovered;

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
     * Gui字体大小
     * */
    protected int fontSize=9;

    /**
     * Gui的高度
     * */
    protected int height;

    /**
     * Gui的材质TextureLoader
     * */
    protected GuiTextureLoader guiTextureLoader;

    /**
     * 是否启用绘制材质
     * 如果为true则绘制材质图片，如果为false则单纯使用颜色填充gui
     * */
    protected boolean enableTexture=false;

    /**
     * Gui的容器
     * */
    protected Gui frame;

    /**
     * 屏幕的最大宽度
     * */
    protected int maxWidth;

    /**
     * 屏幕的最大高度
     * */
    protected int maxHeight;

    /**
     * Gui文本颜色
     * */
    protected int textColor=16777120;


    /**
     * Gui是否启用，如果为false则按钮不能被操作
     * */
    protected boolean enabled;

    /**
     * 按钮显示的文本
     * */
    protected String displayName;

    /**
     * 绘制自定义材质
     * @param x Gui的横坐标x
     * @param y Gui的纵坐标y
     * @param width Gui的宽度
     * @param height Gui的高度
     * @param texturePos 材质位置(texturePos的width,height尽量与Gui一直，否则材质会变形)
     */
    public void drawCustomSizedTexture(int x, int y, int width, int height, GuiTexturePos2D texturePos)
    {
        float f = 1.0F / texturePos.getTextureWidth();
        float f1 = 1.0F / texturePos.getTextureHeight();
        float u=texturePos.getU();
        float v=texturePos.getV();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos((double)x, (double)(y + height), 0.0D)
                .tex((double)(u * f), (double)((v + (float)texturePos.getHeight()) * f1)).endVertex();

        bufferbuilder.pos((double)(x + width), (double)(y + height), 0.0D)
                .tex((double)((u + (float)texturePos.getWidth()) * f), (double)((v + (float)texturePos.getHeight()) * f1)).endVertex();

        bufferbuilder.pos((double)(x + width), (double)y, 0.0D)
                .tex((double)((u + (float)texturePos.getWidth()) * f), (double)(v * f1)).endVertex();

        bufferbuilder.pos((double)x, (double)y, 0.0D)
                .tex((double)(u * f), (double)(v * f1)).endVertex();
        tessellator.draw();
    }

    /**
     * 绘制自定义大小的居中字符串
     * @param text 字符串
     * @param fontSize 字体大小
     * @param x 横坐标
     * @param y 纵坐标
     * @param color 字体颜色
     * */
    public void drawCustomSizedCenterString(String text,float fontSize,int x,int y,int color){
        if(text!=null){
            this.fontRenderer.drawString(text, this.fontSize,(float)x-this.fontRenderer.getStringWidth(text)/2, (float)y, color);
        }
    }

    /**
     * 绘制自定义大小的字符串
     * @param text 字符串
     * @param fontSize 字体大小
     * @param x 横坐标
     * @param y 纵坐标
     * @param color 字体颜色
     * */
    public void drawCustomSizedString(String text,float fontSize,int x,int y,int color){
        if(text!=null){
            fontRenderer.drawString(text, this.fontSize,(float)x, (float)y, color);
        }
    }


    /**
     * 加载Gui的材质
     * */
    public void setTexture(GuiTextureLoader loader) {
        if(this.guiTextureLoader!=null){
            this.guiTextureLoader.deleteTexture();
        }
        this.guiTextureLoader =loader;
    }

    /**
     * {@inheritDoc}
     * */
    @Override
    public void drawGUI(int mouseX, int mouseY, float partialTicks) throws NullTextureException, NullTexturePositionException,TextureNotFoundException {

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
        if(this.guiTextureLoader !=null){
            this.guiTextureLoader.deleteTexture();
            this.guiTextureLoader.stopGifTread();
        }
    }

    /**
     * {@inheritDoc}
     * */
    @Override
    public Gui getFrame() {
        return frame;
    }

    /**
     * {@inheritDoc}
     * */
    @Override
    public void setFrame(Gui frame) {
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
     * 获取Gui的横坐标x
     * */
    @Override
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
    @Override
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
    @Override
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
    @Override
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

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }


    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    /**
     * 设置Gui上显示的文本
     * @param displayString  显示的文本
     * */
    public void setDisplayString(String displayString) {
        this.displayName = displayString;
    }

    @Override
    public String getId() {
        return  id;
    }

    public boolean isMouseInRange(int mouseX,int mouseY){
        if(mouseX>=this.x&&mouseX<=this.x+this.width&&mouseY>=this.y&&mouseY<=this.y+this.height){
            return true;
        }else {
            return false;
        }
    }
}
