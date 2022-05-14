package ForgeAPI.Widget.Impl;

import ForgeAPI.Utils.Image.ImageLoader;
import ForgeAPI.Utils.Image.ImageUtil;
import ForgeAPI.Utils.ResourcesUtil;
import ForgeAPI.Widget.BaseGui;
import ForgeAPI.Widget.TexturePos2D;
import ForgeAPI.Widget.ex.GuiBaseException;
import ForgeAPI.Widget.ex.ParamErrorException;
import net.minecraft.client.renderer.GlStateManager;

import java.io.File;

public class Bar extends BaseGui {
    protected  final float min=0;
    protected final float max=100;
    protected float curr;
    protected boolean CenterString=false;
    protected int BackColor=0xFFFAFA;
    protected int OverlapColor=0x668B8B;
    protected TexturePos2D backTexture;
    protected TexturePos2D overlapTexture;


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
        ImageLoader imageLoader = new ImageLoader(new File(ResourcesUtil.getResourcesPath("assets/mod/textures/Weight/Bar/bars.png")));
        this.backTexture=new TexturePos2D(0,0,182,5,256,256);
        this.overlapTexture=new TexturePos2D(0,10,182,5,256,256);
        this.textureId=ImageUtil.loadTexture(imageLoader);
    }

    /**
     * 绘制进度条进度
     * */
    private void renderOverlap()
    {
        if(textureId!=-1&&enableTexture){
            overlapTexture.setWidth((int) (width*(curr/max)));
            this.drawCustomSizedTexture(x,y,overlapTexture);
        }else{
            drawRect(x,y,x+(int) (width*(curr/max)),y+height,0xFF000000+OverlapColor);
        }

    }


    /**
     * {@inheritDoc}
     * */
    @Override
    public void drawGUI(int mouseX, int mouseY, float partialTicks) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        if(textureId!=-1&&enableTexture){
            ImageUtil.bindTexture(textureId);
            this.drawCustomSizedTexture(x, y,this.backTexture);
        }else{
            drawRect(x,y,x+width,y+height,0xFF000000+BackColor);
        }
        this.renderOverlap();
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
     * */
    public void setCurr(float curr) {
        this.curr = curr;
    }

    /**
     * 设置进度条背景位置
     * */
    public void setBackTexture(TexturePos2D backTexture){
       this.backTexture=backTexture;
    }

    /**
     * 设置进度条进度位置
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
        curr++;
        if(curr==100){
            curr=1;
        }
    }

    /**
     * {@inheritDoc}
     * */
    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
    }
}
