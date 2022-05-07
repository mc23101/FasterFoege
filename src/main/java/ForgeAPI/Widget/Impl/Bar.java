package ForgeAPI.Widget.Impl;

import ForgeAPI.Widget.BaseGui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class Bar extends BaseGui {
    protected  final float min=0;
    protected final float max=100;
    protected float curr;
    protected boolean CenterString=false;
    protected int BackTextureX=0;
    protected int BackTextureY=0;
    protected int OverlapTextureX=0;
    protected int OverlapTextureY=5;
    private ResourceLocation BAR_TEXTURES=new ResourceLocation("textures/gui/bars.png");
    public Bar(int id,float curr, int x,int y,int width, int height) {
        this.id=id;
        this.y=y;
        this.x=x;
        this.curr = curr;
        this.width = width;
        this.height = height;
    }


    private void renderOverlap()
    {
        this.drawTexturedModalRect(x, y, OverlapTextureX, OverlapTextureY, (int) (width*(curr/max)), height);
    }



    @Override
    public void drawGUI(int mouseX, int mouseY, float partialTicks) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(BAR_TEXTURES);
        this.drawTexturedModalRect(x, y, BackTextureX, BackTextureY, width, height);
        this.renderOverlap();
    }


    public float getCurr() {
        return curr;
    }

    public void setCurr(float curr) {
        this.curr = curr;
    }

    public void setBackTexturePos(int backTextureX,int backTextureY){
        this.BackTextureX=backTextureX;
        this.BackTextureY=backTextureY;
    }

    public  void setOverlapTexture(int overlapTextureX,int overlapTextureY){
        this.OverlapTextureX=overlapTextureX;
        this.OverlapTextureY=overlapTextureY;
    }

    public void setTexture(ResourceLocation BAR_TEXTURES) {
        this.BAR_TEXTURES = BAR_TEXTURES;
    }
}
