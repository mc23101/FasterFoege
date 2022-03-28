package ForgeAPI.Widget;

import net.minecraft.client.gui.GuiLabel;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

public class BackGround extends GuiScreen {
    private ResourceLocation resource;
    private int left;
    private int top;
    public BackGround(int width,int height,int left,int top,String Path){
        this.width=width;
        this.height=height;
        this.left=left;
        this.top=top;
        resource=new ResourceLocation(Path);
    }

    public void addLabel(GuiLabel Label){
        this.labelList.add(Label);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void initGui() {
        super.initGui();
        this.mc.getTextureManager().bindTexture(resource);
        this.drawTexturedModalRect(width,height,0,0,left,top);
    }
}
