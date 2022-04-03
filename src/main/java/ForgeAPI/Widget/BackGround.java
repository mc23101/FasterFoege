package ForgeAPI.Widget;

import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiLabel;
import net.minecraft.client.gui.GuiScreen;

import java.io.IOException;
import java.util.List;

public class BackGround extends GuiScreen {
    public Minecraft mc=Minecraft.getMinecraft();
    protected List<Slider> SliderList= Lists.<Slider>newArrayList();
    protected List<TextField> TextFieldList=Lists.<TextField>newArrayList();
    protected List<TextField> testFieldList=Lists.<TextField>newArrayList();
    public BackGround(){
    }

    @Override
    public <T extends GuiButton> T addButton(T buttonIn) {
        return super.addButton(buttonIn);
    }
    public <T extends GuiLabel> void addLabel(T Label){
        this.labelList.add(Label);
    }
    public <T extends Slider> void addSlider(T slider){
        this.SliderList.add(slider);
    }
    public <T extends TextField> void addTextField(T textField){
        this.TextFieldList.add(textField);
    }
    public <T extends TextField> void addtestField(T textField){
        this.testFieldList.add(textField);
    }
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        for(int i=0;i<SliderList.size();++i){
            SliderList.get(i).drawButton(this.mc,mouseX,mouseY,partialTicks);
        }
        for(int i=0;i<TextFieldList.size();++i){
            TextFieldList.get(i).drawTextBox();
        }
        for(int i=0;i<testFieldList.size();++i){
            testFieldList.get(i).drawTextBox();
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void updateScreen() {
        for(int i=0;i<TextFieldList.size();++i){
            TextField textField = TextFieldList.get(i);
            textField.updateCursorCounter();
        }
        for(int i=0;i<testFieldList.size();++i){
            TextField textField = testFieldList.get(i);
            textField.updateCursorCounter();
        }
        super.updateScreen();
    }
    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        for(int i=0;i<TextFieldList.size();++i){
            TextField inputField = TextFieldList.get(i);
            if(inputField.isFocused()){
                if (keyCode == 1)
                {
                    this.mc.displayGuiScreen((GuiScreen)null);
                }
                else if (keyCode != 28 && keyCode != 156)
                {
                    inputField.textboxKeyTyped(typedChar, keyCode);
                }
            }
        }
        for(int i=0;i<testFieldList.size();++i){
            TextField inputField = testFieldList.get(i);
            if(inputField.isFocused()){
                if (keyCode == 1)
                {
                    this.mc.displayGuiScreen((GuiScreen)null);
                }
                else if (keyCode != 28 && keyCode != 156)
                {
                    inputField.textboxKeyTyped(typedChar, keyCode);
                }
            }
        }

    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        for(int i=0;i<TextFieldList.size();++i){
            TextField inputField = TextFieldList.get(i);
            inputField.mouseClicked(mouseX,mouseY,mouseButton);
        }
        for(int i=0;i<testFieldList.size();++i){
            TextField inputField = testFieldList.get(i);
            inputField.mouseClicked(mouseX,mouseY,mouseButton);
        }
    }

    @Override
    public void initGui() {
        super.initGui();
    }
}
