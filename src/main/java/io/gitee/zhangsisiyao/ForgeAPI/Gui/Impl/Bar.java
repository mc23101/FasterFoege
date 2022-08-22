package io.gitee.zhangsisiyao.ForgeAPI.Gui.Impl;


import io.gitee.zhangsisiyao.ForgeAPI.Gui.BaseGui;
import io.gitee.zhangsisiyao.ForgeAPI.Gui.ex.NullTextureException;
import io.gitee.zhangsisiyao.ForgeAPI.Gui.ex.NullTexturePositionException;
import io.gitee.zhangsisiyao.ForgeAPI.Gui.ex.TextureNotFoundException;
import io.gitee.zhangsisiyao.ForgeAPI.Texture.GuiTexturePos2D;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
/* =======================
||类名：Bar
||状态：已完成
||作者：mc23
||最后一次修改时间：2022.5.26
==========================*/
/**
 * Gui控件：进度条
 * */
@SideOnly(Side.CLIENT)
public class Bar extends BaseGui {
    /**
     * 进度条的最小值
     * */
    protected  final float min=0;

    /**
     * 进度条的最大值
     * */
    protected final float max=100;

    /**
     * 进度条的进度
     * */
    protected float curr;

    /**
     * 进度条背景颜色
     * */
    protected int BackColor=0xFFFAFA;

    /**
     * 进度条进度颜色
     * */
    protected int OverlapColor=0x668B8B;

    /**
     * 进度条背景材质位置
     * */
    protected GuiTexturePos2D backTexture;

    /**
     * 进度条进度材质位置
     * */
    protected GuiTexturePos2D overlapTexture;

    /**
     * 自定义大小的进度条控件
     * @param curr 进度条的当前进度
     * @param x 进度条在屏幕上的横坐标X
     * @param y 进度条在屏幕上的纵坐标Y
     * @param width 进度条的宽度
     * @param height 进度条的高度
     * */
    public Bar(String id,float curr, int x,int y,int width, int height) {
        this.y=y;
        this.x=x;
        this.id=id;
        this.curr = curr;
        this.width = width;
        this.height = height;
    }

    /**
     * {@inheritDoc}
     * */
    @Override
    public void drawGUI(int mouseX, int mouseY, float partialTicks) throws NullTextureException, NullTexturePositionException, TextureNotFoundException {
        if(this.visible){
            GlStateManager.scale(1.0,1.0,1.0);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            if(enableTexture){
                drawTexture();
            }else{
                drawColor();
            }
            drawText();
        }
    }

    /**
     * 绘制Gui的文本
     * */
    private void drawText(){
        this.drawCustomSizedCenterString(this.displayName,fontSize,this.x+this.width/2,this.y+(this.height-fontSize)/2,this.textColor);
    }

    /**
     * 绘制启用颜色的Gui
     * */
    private void drawColor(){
        drawRect(x,y,x+width,y+height,0xFF000000+BackColor);
        drawRect(x,y,x+(int) (width*(curr/max)),y+height,0xFF000000+OverlapColor);
    }

    /**
     * 绘制启用材质的Gui
     * */
    private void drawTexture() throws NullTexturePositionException, NullTextureException, TextureNotFoundException {
        if (this.guiTextureLoader != null ) {
            if(this.guiTextureLoader.getGlTextureId() != -1){
                if (this.backTexture != null && this.overlapTexture != null) {
                    guiTextureLoader.bindTexture();
                    this.drawCustomSizedTexture(x, y, width, height, this.backTexture);
                    if (curr != 0) {
                        GuiTexturePos2D currOverlapTexture = new GuiTexturePos2D(
                                overlapTexture.getU(),
                                overlapTexture.getV(),
                                (int) (overlapTexture.getWidth() * (curr / max)),
                                overlapTexture.getHeight(),
                                overlapTexture.getTextureWidth(),
                                overlapTexture.getTextureHeight());
                        this.drawCustomSizedTexture(x, y, (int) (width * (curr / max)), height, currOverlapTexture);
                    }
                }else {
                    throw new NullTexturePositionException();
                }
            }else {
                throw new TextureNotFoundException(this.guiTextureLoader.getResourceLocation());
            }
        }else {
            throw new NullTextureException();
        }
    }

    /**
     * 获取进度条进度
     * @return 返回进度条进度
     * */
    public float getCurr() {
        return curr;
    }

    /**
     * 设置进度条进度
     * @param curr 进度条的当前进度
     * */
    public void setCurr(float curr) {
        this.curr = curr;
    }

    /**
     * 设置进度条背景位置
     * @param backTexture 进度条背景位置
     * @param overlapTexture 进度条进度位置
     * */
    public void setTexturePos(GuiTexturePos2D backTexture, GuiTexturePos2D overlapTexture){
       this.backTexture=backTexture;
        this.overlapTexture=overlapTexture;
    }

    /**
     * {@inheritDoc}
     * */
    @Override
    public void updateGUI() {
        super.updateGUI();
    }

    /**
     * {@inheritDoc}
     * */
    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
    }

    /**
     *  设置绘制背景的RGB颜色
     * @param backColor 背景的RGB颜色
     * */
    public void setBackColor(int backColor) {
        BackColor = backColor;
    }

    /**
     * 设置进度条进度的RGB颜色
     * @param overlapColor 进度条进度的RGB颜色
     * */
    public void setOverlapColor(int overlapColor) {
        OverlapColor = overlapColor;
    }

}
