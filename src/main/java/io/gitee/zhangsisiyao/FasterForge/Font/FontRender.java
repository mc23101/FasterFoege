package io.gitee.zhangsisiyao.FasterForge.Font;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Random;

public class FontRender {

    private static final Logger logger= LogManager.getLogger("ForgeFrame");

    private static final ResourceLocation[] UNICODE_PAGE_LOCATIONS = new ResourceLocation[256];
    protected final int[] charWidth = new int[256];
    public final int FONT_HEIGHT = 9;
    public Random fontRandom = new Random();
    protected final byte[] glyphWidth = new byte[65536];
    private final int[] colorCode = new int[32];
    protected final ResourceLocation locationFontTexture;
    private final TextureManager renderEngine;
    protected float posX;
    protected float posY;
    private boolean unicodeFlag;
    private float red;
    private float blue;
    private float green;
    private float alpha;
    private int textColor;
    /**
     * 粗体
     * */
    private boolean boldStyle=false;
   /**
    * 斜体
    * */
    private boolean italicStyle=true;


    private int fontSize=9;

    public static FontRender fontRender=new FontRender(new ResourceLocation("textures/font/ascii.png"),true);

    public FontRender(ResourceLocation location,boolean unicode) {
        GameSettings gameSettings=Minecraft.getMinecraft().gameSettings;
        this.locationFontTexture=location;
        this.renderEngine= Minecraft.getMinecraft().renderEngine;
        this.unicodeFlag = unicode;
        bindTexture(this.locationFontTexture);
        for (int i = 0; i < 32; ++i)
        {
            int j = (i >> 3 & 1) * 85;
            int k = (i >> 2 & 1) * 170 + j;
            int l = (i >> 1 & 1) * 170 + j;
            int i1 = (i >> 0 & 1) * 170 + j;

            if (i == 6)
            {
                k += 85;
            }

            if (gameSettings.anaglyph)
            {
                int j1 = (k * 30 + l * 59 + i1 * 11) / 100;
                int k1 = (k * 30 + l * 70) / 100;
                int l1 = (k * 30 + i1 * 70) / 100;
                k = j1;
                l = k1;
                i1 = l1;
            }

            if (i >= 16)
            {
                k /= 4;
                l /= 4;
                i1 /= 4;
            }

            this.colorCode[i] = (k & 255) << 16 | (l & 255) << 8 | i1 & 255;
        }

        this.readGlyphSizes();
    }

    private void readGlyphSizes()
    {
        IResource iresource = null;

        try
        {
            iresource = getResource(new ResourceLocation("font/glyph_sizes.bin"));
            iresource.getInputStream().read(this.glyphWidth);
        }
        catch (IOException ioexception)
        {
            throw new RuntimeException(ioexception);
        }
        finally
        {
            IOUtils.closeQuietly(iresource);
        }
    }

    private float renderChar(char ch, boolean italic)
    {
        if (ch == 160) return 4.0F; // forge: display nbsp as space. MC-2595
        if (ch == ' ')
        {
            return 4.0F;
        }
        else
        {
            int i = "\u00c0\u00c1\u00c2\u00c8\u00ca\u00cb\u00cd\u00d3\u00d4\u00d5\u00da\u00df\u00e3\u00f5\u011f\u0130\u0131\u0152\u0153\u015e\u015f\u0174\u0175\u017e\u0207\u0000\u0000\u0000\u0000\u0000\u0000\u0000 !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u0000\u00c7\u00fc\u00e9\u00e2\u00e4\u00e0\u00e5\u00e7\u00ea\u00eb\u00e8\u00ef\u00ee\u00ec\u00c4\u00c5\u00c9\u00e6\u00c6\u00f4\u00f6\u00f2\u00fb\u00f9\u00ff\u00d6\u00dc\u00f8\u00a3\u00d8\u00d7\u0192\u00e1\u00ed\u00f3\u00fa\u00f1\u00d1\u00aa\u00ba\u00bf\u00ae\u00ac\u00bd\u00bc\u00a1\u00ab\u00bb\u2591\u2592\u2593\u2502\u2524\u2561\u2562\u2556\u2555\u2563\u2551\u2557\u255d\u255c\u255b\u2510\u2514\u2534\u252c\u251c\u2500\u253c\u255e\u255f\u255a\u2554\u2569\u2566\u2560\u2550\u256c\u2567\u2568\u2564\u2565\u2559\u2558\u2552\u2553\u256b\u256a\u2518\u250c\u2588\u2584\u258c\u2590\u2580\u03b1\u03b2\u0393\u03c0\u03a3\u03c3\u03bc\u03c4\u03a6\u0398\u03a9\u03b4\u221e\u2205\u2208\u2229\u2261\u00b1\u2265\u2264\u2320\u2321\u00f7\u2248\u00b0\u2219\u00b7\u221a\u207f\u00b2\u25a0\u0000".indexOf(ch);
            return i != -1  ? this.renderDefaultChar(i, italic) : this.renderUnicodeChar(ch, italic);
        }
    }

    protected float renderDefaultChar(int ch, boolean italic)
    {
        int i = this.glyphWidth[ch] & 255;

        if (i == 0)
        {
            return 0.0F;
        }
        else
        {
            int j = ch / 256;
            this.loadGlyphTexture(j);
            int k = i >>> 4;
            int l = i & 15;
            float f = (float)k;
            float f1 = (float)(l + 1);
            float f2 = (float)(ch % 16 * 16) + f;
            float f3 = (float)((ch & 255) / 16 * 16);
            float f4 = f1 - f - 0.02F;
            float f5 = italic ? 1.0F : 0.0F;
            GlStateManager.glBegin(5);
            GlStateManager.glTexCoord2f(f2 / 256.0F, f3 / 256.0F);
            GlStateManager.glVertex3f(this.posX + f5, this.posY, 0.0F);
            GlStateManager.glTexCoord2f(f2 / 256.0F, (f3 + 15.98F) / 256.0F);
            GlStateManager.glVertex3f(this.posX - f5, this.posY + fontSize, 0.0F);
            GlStateManager.glTexCoord2f((f2 + f4) / 256.0F, f3 / 256.0F);
            GlStateManager.glVertex3f(this.posX + fontSize/2+ f5, this.posY, 0.0F);
            GlStateManager.glTexCoord2f((f2 + f4) / 256.0F, (f3 + 15.98F) / 256.0F);
            GlStateManager.glVertex3f(this.posX + fontSize/2 - f5, this.posY + fontSize, 0.0F);
            GlStateManager.glEnd();
            return fontSize/2+1;
        }
    }

    private ResourceLocation getUnicodePageLocation(int page)
    {
        if (UNICODE_PAGE_LOCATIONS[page] == null)
        {
            UNICODE_PAGE_LOCATIONS[page] = new ResourceLocation(String.format("textures/font/unicode_page_%02x.png", page));
        }

        return UNICODE_PAGE_LOCATIONS[page];
    }

    private void loadGlyphTexture(int page)
    {
        bindTexture(this.getUnicodePageLocation(page));
    }

    protected float renderUnicodeChar(char ch, boolean italic)
    {
        int i = this.glyphWidth[ch] & 255;

        if (i == 0)
        {
            return 0.0F;
        }
        else
        {
            int j = ch / 256;
            this.loadGlyphTexture(j);
            int k = i >>> 4;
            int l = i & 15;
            float f = (float)k;
            float f1 = (float)(l + 1);
            float f2 = (float)(ch % 16 * 16) + f;
            float f3 = (float)((ch & 255) / 16 * 16);
            float f4 = f1 - f - 0.02F;
            float f5 = italic ? 1.0F : 0.0F;
            GlStateManager.glBegin(5);
            GlStateManager.glTexCoord2f(f2 / 256.0F, f3 / 256.0F);
            GlStateManager.glVertex3f(this.posX + f5, this.posY, 0.0F);
            GlStateManager.glTexCoord2f(f2 / 256.0F, (f3 + 15.98F) / 256.0F);
            GlStateManager.glVertex3f(this.posX - f5, this.posY + fontSize, 0.0F);
            GlStateManager.glTexCoord2f((f2 + f4) / 256.0F, f3 / 256.0F);
            GlStateManager.glVertex3f(this.posX + fontSize+ f5, this.posY, 0.0F);
            GlStateManager.glTexCoord2f((f2 + f4) / 256.0F, (f3 + 15.98F) / 256.0F);
            GlStateManager.glVertex3f(this.posX + fontSize - f5, this.posY + fontSize, 0.0F);
            GlStateManager.glEnd();
            return fontSize;
        }
    }

    public int drawString(String text,int fontSize, float x, float y, int color)
    {
        this.fontSize=fontSize;
        enableAlpha();
        return this.renderString(text, x, y, color, false);
    }


    /**
     * 绘制字符串
     * */
    private void renderStringAtPos(String text)
    {
        for (int i = 0; i < text.length(); ++i)
        {
            char c0 = text.charAt(i);
            float f = this.renderChar(c0, this.italicStyle);
            float f1 = (float) ((0.5*fontSize)/9.0);
            if (this.boldStyle)
            {
                this.posX += f1;
                this.renderChar(c0, this.italicStyle);
                this.posX -= f1;
                f+=fontSize*0.1;
            }
            this.posX += f;
        }

    }

    private int renderString(String text, float x, float y, int color, boolean dropShadow)
    {
        if (text == null)
        {
            return 0;
        }
        else
        {

            if ((color & -67108864) == 0)
            {
                color |= -16777216;
            }

            if (dropShadow)
            {
                color = (color & 16579836) >> 2 | color & -16777216;
            }

            this.red = (float)(color >> 16 & 255) / 255.0F;
            this.blue = (float)(color >> 8 & 255) / 255.0F;
            this.green = (float)(color & 255) / 255.0F;
            this.alpha = (float)(color >> 24 & 255) / 255.0F;
            setColor(this.red, this.blue, this.green, this.alpha);
            this.posX = x;
            this.posY = y;
            this.renderStringAtPos(text);
            return (int)this.posX;
        }
    }


    protected void setColor(float r, float g, float b, float a)
    {
        GlStateManager.color(r,g,b,a);
    }

    protected void enableAlpha()
    {
        GlStateManager.enableAlpha();
    }

    protected void bindTexture(ResourceLocation location)
    {
        renderEngine.bindTexture(location);
    }

    protected IResource getResource(ResourceLocation location) throws IOException
    {
        return Minecraft.getMinecraft().getResourceManager().getResource(location);
    }

    public int getStringWidth(String text){
        int sum=0;
        for(int i=0;i<text.length();i++){
            int x = "\u00c0\u00c1\u00c2\u00c8\u00ca\u00cb\u00cd\u00d3\u00d4\u00d5\u00da\u00df\u00e3\u00f5\u011f\u0130\u0131\u0152\u0153\u015e\u015f\u0174\u0175\u017e\u0207\u0000\u0000\u0000\u0000\u0000\u0000\u0000 !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u0000\u00c7\u00fc\u00e9\u00e2\u00e4\u00e0\u00e5\u00e7\u00ea\u00eb\u00e8\u00ef\u00ee\u00ec\u00c4\u00c5\u00c9\u00e6\u00c6\u00f4\u00f6\u00f2\u00fb\u00f9\u00ff\u00d6\u00dc\u00f8\u00a3\u00d8\u00d7\u0192\u00e1\u00ed\u00f3\u00fa\u00f1\u00d1\u00aa\u00ba\u00bf\u00ae\u00ac\u00bd\u00bc\u00a1\u00ab\u00bb\u2591\u2592\u2593\u2502\u2524\u2561\u2562\u2556\u2555\u2563\u2551\u2557\u255d\u255c\u255b\u2510\u2514\u2534\u252c\u251c\u2500\u253c\u255e\u255f\u255a\u2554\u2569\u2566\u2560\u2550\u256c\u2567\u2568\u2564\u2565\u2559\u2558\u2552\u2553\u256b\u256a\u2518\u250c\u2588\u2584\u258c\u2590\u2580\u03b1\u03b2\u0393\u03c0\u03a3\u03c3\u03bc\u03c4\u03a6\u0398\u03a9\u03b4\u221e\u2205\u2208\u2229\u2261\u00b1\u2265\u2264\u2320\u2321\u00f7\u2248\u00b0\u2219\u00b7\u221a\u207f\u00b2\u25a0\u0000".indexOf(text.charAt(i));
            if(x!=-1){
                sum+=fontSize/2;
            }else {
                sum+=fontSize;
            }
        }
        return sum;
    }
}
