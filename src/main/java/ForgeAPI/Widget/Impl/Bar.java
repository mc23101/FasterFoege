package ForgeAPI.Widget.Impl;

import ForgeAPI.Utils.ResourcesUtil;
import ForgeAPI.Utils.Texture.TextureLoader;
import ForgeAPI.Widget.BaseGui;
import ForgeAPI.Widget.TexturePos2D;
import ForgeAPI.Widget.ex.GuiBaseException;
import ForgeAPI.Widget.ex.ParamErrorException;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.File;
/* =======================
||类名：Bar
||状态：已完成
||作者：mc23
||最后一次修改时间：2022.5.15
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
    protected TexturePos2D backTexture;

    /**
     * 进度条进度材质位置
     * */
    protected TexturePos2D overlapTexture;

    /**
     * 自定义大小的进度条控件
     * @param id Gui的ID(可填写任意值，但不建议与其他Gui的值相同)
     * @param curr 进度条的当前进度
     * @param x 进度条在屏幕上的横坐标X
     * @param y 进度条在屏幕上的纵坐标Y
     * @param width 进度条的宽度
     * @param height 进度条的高度
     * */
    public Bar(String id,float curr, int x,int y,int width, int height) {
        if(curr>100||curr<0) throw new ParamErrorException("进度条进度值大于100或小于0");
        if(x<0||y<0) throw new GuiBaseException("x坐标或y坐标值小于0");
        if(width<0||height<0) throw new GuiBaseException("宽度width或高度height小于0");
        this.id=id;
        this.y=y;
        this.x=x;
        this.curr = curr;
        this.width = width;
        this.height = height;
        this.textureLoader = new TextureLoader(new File(ResourcesUtil.getResourcesPath("assets/texture/Weight/Bar/bars.png")));
        this.backTexture=new TexturePos2D(0,0,182,5,256,256);
        this.overlapTexture=new TexturePos2D(0,5,182,5,256,256);
    }


    /**
     * {@inheritDoc}
     * */
    @Override
    public void drawGUI(int mouseX, int mouseY, float partialTicks) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        if(enableTexture&&textureLoader!=null){
            textureLoader.bindTexture();
            if(textureLoader.getGlTextureId()!=-1){
                drawTexture();
            }
        }else{
            drawColor();
        }
    }

    /**
     * 绘制仅颜色的Gui
     * */
    private void drawColor(){
        drawRect(x,y,x+width,y+height,0xFF000000+BackColor);
        drawRect(x,y,x+(int) (width*(curr/max)),y+height,0xFF000000+OverlapColor);
    }

    /**
     * 绘制启用材质的Gui
     * */
    private void drawTexture(){
        this.drawCustomSizedTexture(x, y,width,height,this.backTexture);
        if(curr!=0){
            TexturePos2D currOverlapTexture=new TexturePos2D(
                    overlapTexture.getU(),
                    overlapTexture.getV(),
                    (int) (overlapTexture.getWidth()*(curr/max)),
                    overlapTexture.getHeight(),
                    overlapTexture.getMaxWidth(),
                    overlapTexture.getMaxWidth());
            this.drawCustomSizedTexture(x,y, (int) (width*(curr/max)),height,currOverlapTexture);
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
     * */
    public void setBackTexture(TexturePos2D backTexture){
       this.backTexture=backTexture;
    }

    /**
     * 设置进度条进度位置
     * @param overlapTexture 进度条进度位置
     * */
    public  void setOverlapTexture(TexturePos2D overlapTexture){
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
