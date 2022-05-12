package ForgeAPI.Widget.Impl;

import ForgeAPI.Utils.Image.ImageUtil;
import ForgeAPI.Widget.BaseGui;
import ForgeAPI.Widget.ex.GuiBaseException;
import ForgeAPI.Widget.ex.ParamErrorException;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class Bar extends BaseGui {
    protected  final float min=0;
    protected final float max=100;
    protected float curr;
    protected boolean CenterString=false;
    protected int BackTextureX=0;
    protected int BackTextureY=0;
    protected int BackColor=0xFFFAFA;
    protected int OverlapColor=0x668B8B;
    protected int OverlapTextureX=0;
    protected int OverlapTextureY=10;
    private ResourceLocation BAR_TEXTURES=new ResourceLocation("textures/gui/bars.png");
    public Bar(int id,float curr, int x,int y,int width, int height) {
        if(curr>100||curr<0) throw new ParamErrorException("进度条进度值大于100或小于0");
        if(x<0||y<0) throw new GuiBaseException("x坐标或y坐标值小于0");
        if(width<0||height<0) throw new GuiBaseException("宽度width或高度height小于0");
        this.textureId= ImageUtil.getResourceLocationTextureId(BAR_TEXTURES);
        this.id=id;
        this.y=y;
        this.x=x;
        this.curr = curr;
        this.width = width;
        this.height = height;
    }

    /**
     * 绘制进度条进度
     * */
    private void renderOverlap()
    {
        if(textureId!=-1&&enableTexture){
            this.drawModalRectWithCustomSizedTexture(x, y, OverlapTextureX, OverlapTextureY, (int) (width*(curr/max)), height, (int) (width*(curr/max)), height);
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
            this.drawModalRectWithCustomSizedTexture(x, y, BackTextureX, BackTextureY, width, height,width, height);
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
     * @param backTextureX 背景起始坐标X
     * @param backTextureY 背景起始坐标Y
     * */
    public void setBackTexture(int backTextureX,int backTextureY){
        this.BackTextureX=backTextureX;
        this.BackTextureY=backTextureY;
    }

    /**
     * 设置进度条进度位置
     * @param overlapTextureX 进度图起始位置X
     * @param overlapTextureY 进度图起始位置Y
     * */
    public  void setOverlapTexture(int overlapTextureX,int overlapTextureY){
        this.OverlapTextureX=overlapTextureX;
        this.OverlapTextureY=overlapTextureY;
    }

    /**
     * {@inheritDoc}
     * */
    @Override
    public void updateGUI() {
        super.updateGUI();
        curr++;
        if(curr==100){
            curr=min;
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
