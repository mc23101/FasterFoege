package ForgeAPI.Gui.Impl;

import ForgeAPI.Gui.BaseGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.math.MathHelper;

import java.util.HashMap;
import java.util.Map;

public class TextField extends BaseGui {
    private boolean enabledCenter=false;
    private boolean enabledLines=true;
    private String text = "";
    private Map<Integer,String> textlist=new HashMap<Integer,String>();
    private int maxStringLength = 32;
    private int cursorPositionY=0;
    private int cursorPositionX=0;
    private int cursorPositionLine=0;
    private int ClickedLine;
    private int cursorCounter;
    private int line=1;
    private int MaxLine=0;
    private int VisibleLineBegin;
    private int VisibleLineEnd;
    private boolean enableBackgroundDrawing = true;
    private boolean canLoseFocus = true;
    private boolean isFocused;
    private boolean isEnabled = true;
    private int lineScrollOffset=1;
    private int selectionEnd;
    private int TextEndPositionX=0;
    private int TextEndPositionY=0;
    private int TextEndLine=1;
    private int enabledColor = 14737632;
    private int disabledColor = 7368816;
    private int cursorStringPosition;


    public TextField(String componentId, int x, int y, int par5Width, int par6Height)
    {
        this.id = componentId;
        this.fontRenderer = Minecraft.getMinecraft().fontRenderer;
        this.x = x;
        this.y = y;
        this.width = par5Width;
        this.height = par6Height;
        this.MaxLine=this.height/this.fontRenderer.FONT_HEIGHT;
    }



    public void updateCursorCounter()
    {
        ++this.cursorCounter;
    }

    public void setText(String textIn)
    {
            if (textIn.length() > this.maxStringLength)
            {
                this.text = textIn.substring(0, this.maxStringLength);
            }
            else
            {
                this.text = textIn;
            }

            this.setCursorPositionEnd();
    }

    public String getText()
    {
        return this.text;
    }

    public String getSelectedText()
    {
        int i = this.cursorPositionX < this.selectionEnd ? this.cursorPositionX : this.selectionEnd;
        int j = this.cursorPositionX < this.selectionEnd ? this.selectionEnd : this.cursorPositionX;
        return this.text.substring(i, j);
    }

    public void writeText(String textToWrite)
    {
            String inputString = ChatAllowedCharacters.filterAllowedCharacters(textToWrite);
            if(this.cursorStringPosition==this.text.length()){
                this.text=this.text.substring(0,this.cursorStringPosition)+inputString;
            }else{
                this.text=this.text.substring(0,this.cursorStringPosition)+inputString+this.text.substring(this.cursorStringPosition);
            }
            this.cursorStringPosition=this.cursorStringPosition+inputString.length();
            createLines();
    }

    private void createLines(){
        String s=text;
        textlist.clear();
        line=1;
        if(s.length()==0){
            this.TextEndPositionX=0;
        }
        while (s.length()!=0){
            String lineString=this.fontRenderer.trimStringToWidth(s,this.width-this.lineScrollOffset);
            if(lineString.equals(s)){
                textlist.put(line,lineString);
                this.TextEndPositionX=this.fontRenderer.getStringWidth(lineString);
                this.TextEndLine=line;
                s="";
            }else{
                textlist.put(line,lineString);
                this.TextEndPositionX=0;
                line=line+1;
                s=s.substring(lineString.length());
            }
        }
    }
    public int getClickedLine(int MouseX,int MouseY){
        int LineHeight=this.fontRenderer.FONT_HEIGHT;
        for(int i=1;i<=MaxLine;i++){
            if((MouseY<=this.y+i*LineHeight&&MouseY>this.y+(i-1)*LineHeight)&&MouseX>this.x&&MouseX<this.x+this.width-this.lineScrollOffset){
                return i+this.VisibleLineBegin-1;
            }
        }
        return 0;
    }
    public void getClickCursorPosition(int mouseX,int mouseY){
        int X=mouseX-this.x;
        if(textlist.containsKey(this.ClickedLine)){
            String s=textlist.get(this.ClickedLine);
            int sum=0;
            for(int i=1;i<this.ClickedLine                                                                                                                                                                                  ;i++){
                sum=sum+this.textlist.get(i).length();
            }

            if(s.length()!=0){
                char[] chars=s.toCharArray();
                for(int i=0;i<chars.length;i++){
                    String SubString=s.substring(0,i);
                    int StringWidth=this.fontRenderer.getStringWidth(SubString);
                    int StringWidth1=StringWidth+this.fontRenderer.getCharWidth(chars[i])/2;
                    int StringWidth2=StringWidth+this.fontRenderer.getCharWidth(chars[i]);
                    if(X>StringWidth1&&X<=StringWidth2){
                        this.cursorStringPosition=sum+i+1;
                    }else if(X<=StringWidth1&&X>StringWidth){
                        this.cursorStringPosition=sum+i;
                    }else if(X>this.fontRenderer.getStringWidth(s)&X<=this.width-this.lineScrollOffset){
                        this.cursorStringPosition=sum+s.length();
                    }
                }
            }
        }

    }
    public void loadCursorPosition(){
        int sum=0;
        String s="";
        for(int i=1;i<=textlist.size();i++){
            sum=sum+this.textlist.get(i).length();
            if(sum>=this.cursorStringPosition){
                sum=sum-this.textlist.get(i).length();
                s=this.textlist.get(i-1);
                this.cursorPositionLine=i;
                break;
            }
        }
        String ss=this.text.substring(sum,this.cursorStringPosition);
        this.cursorPositionX=this.fontRenderer.getStringWidth(ss);
        this.cursorPositionY=(this.cursorPositionLine-this.VisibleLineBegin)*this.fontRenderer.FONT_HEIGHT;
    }
    public void drawTextToScreen(){
            int color = this.isEnabled ? this.enabledColor : this.disabledColor;
            //光标的位置
            int j = this.cursorPositionX - this.lineScrollOffset;
            //字符串结束位置
            int k = this.selectionEnd - this.lineScrollOffset;
            if(textlist.size()<=MaxLine){
                VisibleLineBegin=1;
                VisibleLineEnd=MaxLine;
            }else{
                VisibleLineBegin=textlist.size()-MaxLine+1;
                VisibleLineEnd=textlist.size();
            }
            for (int x = VisibleLineBegin, c=1;x<=VisibleLineEnd; x++,c++) {
                if(textlist.containsKey(x)){
                    TextEndPositionY=(c-1)*this.fontRenderer.FONT_HEIGHT;
                    String s = this.textlist.get(x);
                    //判断光标位置是否在范围内
                    boolean flag = (j >= 0 && j <= s.length());
                    //this.enableBackgroundDrawing ? this.y + this.fontRenderer.FONT_HEIGHT * (c-1) : this.y;
                    //开始时绘画文本
                    if (!s.isEmpty()) {
                        this.fontRenderer.drawStringWithShadow(s, (float)this.x,  (float)this.y+this.fontRenderer.FONT_HEIGHT*(c-1), color);
                    }
                }
            }
    }

    public void drawCursor(){
            if(this.isFocused()){
                int color = this.isEnabled ? this.enabledColor : this.disabledColor;
                boolean flag1 = this.isFocused && this.cursorCounter / 6 % 2 == 0 ;
                if (flag1)
                {
                    boolean flag2=cursorPositionLine!=0;
                    if(flag2&&cursorPositionLine>=VisibleLineBegin&&cursorPositionLine<=VisibleLineEnd){
                        Gui.drawRect(this.x + this.cursorPositionX, this.y + this.cursorPositionY - 1, this.x + this.cursorPositionX + 1, this.y + this.cursorPositionY + 1 + this.fontRenderer.FONT_HEIGHT, -3092272);
                    }
                    else if(!flag2&&TextEndLine>=VisibleLineBegin&&TextEndLine<=VisibleLineEnd){
                        Gui.drawRect(this.x + this.TextEndPositionX, this.y + this.TextEndPositionY - 1, this.x + this.TextEndPositionX + 1, this.y + this.TextEndPositionY + 1 + this.fontRenderer.FONT_HEIGHT, -3092272);
                    }
                }
            }

    }



    public void deleteWords(int num)
    {

    }

    public void deleteFromCursor(int num)
    {
        if(this.cursorStringPosition>=1){
            this.text=this.text.substring(0,this.cursorStringPosition-1)+this.text.substring(this.cursorStringPosition);
            this.cursorStringPosition=this.cursorStringPosition-1;
        }
        createLines();
    }

    public String getId()
    {
        return this.id;
    }

    public int getNthWordFromCursor(int numWords)
    {
        return this.getNthWordFromPos(numWords, this.getCursorPosition());
    }

    public int getNthWordFromPos(int n, int pos)
    {
        return this.getNthWordFromPosWS(n, pos, true);
    }

    public int getNthWordFromPosWS(int n, int pos, boolean skipWs)
    {
        int i = pos;
        boolean flag = n < 0;
        int j = Math.abs(n);

        for (int k = 0; k < j; ++k)
        {
            if (!flag)
            {
                int l = this.text.length();
                i = this.text.indexOf(32, i);

                if (i == -1)
                {
                    i = l;
                }
                else
                {
                    while (skipWs && i < l && this.text.charAt(i) == ' ')
                    {
                        ++i;
                    }
                }
            }
            else
            {
                while (skipWs && i > 0 && this.text.charAt(i - 1) == ' ')
                {
                    --i;
                }

                while (i > 0 && this.text.charAt(i - 1) != ' ')
                {
                    --i;
                }
            }
        }

        return i;
    }

    public void moveCursorBy(int num)
    {
        this.setCursorPosition(this.selectionEnd + num);
    }

    public void setCursorPosition(int pos)
    {
        this.cursorPositionX = pos;
        int i = this.text.length();
        this.cursorPositionX = MathHelper.clamp(this.cursorPositionX, 0, i);
        this.setSelectionPos(this.cursorPositionX);
    }

    public void setCursorPositionZero()
    {
        this.setCursorPosition(0);
    }

    public void setCursorPositionEnd()
    {
        this.setCursorPosition(this.text.length());
    }

    public boolean textboxKeyTyped(char typedChar, int keyCode)
    {
        if (!this.isFocused)
        {
            return false;
        }
        else if (GuiScreen.isKeyComboCtrlA(keyCode))
        {
            this.setCursorPositionEnd();
            this.setSelectionPos(0);
            return true;
        }
        else if (GuiScreen.isKeyComboCtrlC(keyCode))
        {
            GuiScreen.setClipboardString(this.getSelectedText());
            return true;
        }
        else if (GuiScreen.isKeyComboCtrlV(keyCode))
        {
            if (this.isEnabled)
            {
                this.writeText(GuiScreen.getClipboardString());
            }

            return true;
        }
        else if (GuiScreen.isKeyComboCtrlX(keyCode))
        {
            GuiScreen.setClipboardString(this.getSelectedText());

            if (this.isEnabled)
            {
                this.writeText("");
            }

            return true;
        }
        else
        {
            switch (keyCode)
            {
                case 14:

                    if (GuiScreen.isCtrlKeyDown())
                    {
                        if (this.isEnabled)
                        {
                            this.deleteWords(-1);
                        }
                    }
                    else if (this.isEnabled)
                    {
                        this.deleteFromCursor(-1);
                    }

                    return true;
                case 199:

                    if (GuiScreen.isShiftKeyDown())
                    {
                        this.setSelectionPos(0);
                    }
                    else
                    {
                        this.setCursorPositionZero();
                    }

                    return true;
                case 203:

                    if (GuiScreen.isShiftKeyDown())
                    {
                        if (GuiScreen.isCtrlKeyDown())
                        {
                            this.setSelectionPos(this.getNthWordFromPos(-1, this.getSelectionEnd()));
                        }
                        else
                        {
                            this.setSelectionPos(this.getSelectionEnd() - 1);
                        }
                    }
                    else if (GuiScreen.isCtrlKeyDown())
                    {
                        this.setCursorPosition(this.getNthWordFromCursor(-1));
                    }
                    else
                    {
                        this.moveCursorBy(-1);
                    }

                    return true;
                case 205:

                    if (GuiScreen.isShiftKeyDown())
                    {
                        if (GuiScreen.isCtrlKeyDown())
                        {
                            this.setSelectionPos(this.getNthWordFromPos(1, this.getSelectionEnd()));
                        }
                        else
                        {
                            this.setSelectionPos(this.getSelectionEnd() + 1);
                        }
                    }
                    else if (GuiScreen.isCtrlKeyDown())
                    {
                        this.setCursorPosition(this.getNthWordFromCursor(1));
                    }
                    else
                    {
                        this.moveCursorBy(1);
                    }

                    return true;
                case 207:

                    if (GuiScreen.isShiftKeyDown())
                    {
                        this.setSelectionPos(this.text.length());
                    }
                    else
                    {
                        this.setCursorPositionEnd();
                    }

                    return true;
                case 211:

                    if (GuiScreen.isCtrlKeyDown())
                    {
                        if (this.isEnabled)
                        {
                            this.deleteWords(1);
                        }
                    }
                    else if (this.isEnabled)
                    {
                        this.deleteFromCursor(1);
                    }

                    return true;
                default:

                    if (ChatAllowedCharacters.isAllowedCharacter(typedChar))
                    {
                        if (this.isEnabled)
                        {
                            this.writeText(Character.toString(typedChar));
                        }

                        return true;
                    }
                    else
                    {
                        return false;
                    }
            }
        }
    }

    @Override
    public void drawGUI(int mouseX, int mouseY, float partialTicks) {
        if (this.getVisible()) {
            if (this.getEnableBackgroundDrawing()) {
                drawRect(this.x - 1, this.y - 1, this.x + this.width + 1, this.y + this.height + 1, -6250336);
                drawRect(this.x, this.y, this.x + this.width, this.y + this.height, -16777216);
            }
            loadCursorPosition();
            drawTextToScreen();
            drawCursor();
        }
    }

    public boolean mouseClicked(int mouseX, int mouseY, int mouseButton)
    {

        ClickedLine=getClickedLine(mouseX,mouseY);
        getClickCursorPosition(mouseX,mouseY);
        boolean flag = mouseX >= this.x && mouseX < this.x + this.width && mouseY >= this.y && mouseY < this.y + this.height;
        if (this.canLoseFocus)
        {
            this.setFocused(flag);
        }
        return  true;
    }

    private void drawSelectionBox(int startX, int startY, int endX, int endY)
    {
        if (startX < endX)
        {
            int i = startX;
            startX = endX;
            endX = i;
        }

        if (startY < endY)
        {
            int j = startY;
            startY = endY;
            endY = j;
        }

        if (endX > this.x + this.width)
        {
            endX = this.x + this.width;
        }

        if (startX > this.x + this.width)
        {
            startX = this.x + this.width;
        }

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        GlStateManager.color(0.0F, 0.0F, 255.0F, 255.0F);
        GlStateManager.disableTexture2D();
        GlStateManager.enableColorLogic();
        GlStateManager.colorLogicOp(GlStateManager.LogicOp.OR_REVERSE);
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION);
        bufferbuilder.pos((double)startX, (double)endY, 0.0D).endVertex();
        bufferbuilder.pos((double)endX, (double)endY, 0.0D).endVertex();
        bufferbuilder.pos((double)endX, (double)startY, 0.0D).endVertex();
        bufferbuilder.pos((double)startX, (double)startY, 0.0D).endVertex();
        tessellator.draw();
        GlStateManager.disableColorLogic();
        GlStateManager.enableTexture2D();
    }

    public void setMaxStringLength(int length)
    {
        this.maxStringLength = length;

        if (this.text.length() > length)
        {
            this.text = this.text.substring(0, length);
        }
    }

    public int getMaxStringLength()
    {
        return this.maxStringLength;
    }

    public int getCursorPosition()
    {
        return this.cursorPositionX;
    }

    public boolean getEnableBackgroundDrawing()
    {
        return this.enableBackgroundDrawing;
    }

    public void setEnableBackgroundDrawing(boolean enableBackgroundDrawingIn)
    {
        this.enableBackgroundDrawing = enableBackgroundDrawingIn;
    }

    public void setTextColor(int color)
    {
        this.enabledColor = color;
    }

    public void setDisabledTextColour(int color)
    {
        this.disabledColor = color;
    }

    public void setFocused(boolean isFocusedIn)
    {
        if (isFocusedIn && !this.isFocused)
        {
            this.cursorCounter = 0;
        }

        this.isFocused = isFocusedIn;

        if (Minecraft.getMinecraft().currentScreen != null)
        {
            Minecraft.getMinecraft().currentScreen.setFocused(isFocusedIn);
        }
    }

    public boolean isFocused()
    {
        return this.isFocused;
    }

    public void setEnabled(boolean enabled)
    {
        this.isEnabled = enabled;
    }

    public int getSelectionEnd()
    {
        return this.selectionEnd;
    }

    public int getWidth()
    {
        return this.getEnableBackgroundDrawing() ? this.width - 8 : this.width;
    }

    public void setSelectionPos(int position)
    {
        int i = this.text.length();

        if (position > i)
        {
            position = i;
        }

        if (position < 0)
        {
            position = 0;
        }

        this.selectionEnd = position;

        if (this.fontRenderer != null)
        {
            if (this.lineScrollOffset > i)
            {
                this.lineScrollOffset = i;
            }

            int j = this.getWidth();
            String s = this.fontRenderer.trimStringToWidth(this.text.substring(this.lineScrollOffset), j);
            int k = s.length() + this.lineScrollOffset;

            if (position == this.lineScrollOffset)
            {
                this.lineScrollOffset -= this.fontRenderer.trimStringToWidth(this.text, j, true).length();
            }

            if (position > k)
            {
                this.lineScrollOffset += position - k;
            }
            else if (position <= this.lineScrollOffset)
            {
                this.lineScrollOffset -= this.lineScrollOffset - position;
            }

            this.lineScrollOffset = MathHelper.clamp(this.lineScrollOffset, 0, i);
        }
    }

    public void setCanLoseFocus(boolean canLoseFocusIn)
    {
        this.canLoseFocus = canLoseFocusIn;
    }

    public boolean getVisible()
    {
        return this.visible;
    }

    public void setVisible(boolean isVisible)
    {
        this.visible = isVisible;
    }
}
