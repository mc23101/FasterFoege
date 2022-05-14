package ForgeAPI.Widget;

public class TexturePos2D {
    private float U;
    private float V;
    private int width;
    private int height;
    private float maxWidth;
    private float maxHeight;

    public TexturePos2D(float u, float v, int width, int height, float maxWidth, float maxHeight) {
        U = u;
        V = v;
        this.width = width;
        this.height = height;
        this.maxWidth = maxWidth;
        this.maxHeight = maxHeight;
    }

    public float getU() {
        return U;
    }

    public void setU(float u) {
        U = u;
    }

    public float getV() {
        return V;
    }

    public void setV(float v) {
        V = v;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public float getMaxWidth() {
        return maxWidth;
    }

    public void setMaxWidth(float maxWidth) {
        this.maxWidth = maxWidth;
    }

    public float getMaxHeight() {
        return maxHeight;
    }

    public void setMaxHeight(float maxHeight) {
        this.maxHeight = maxHeight;
    }
}
