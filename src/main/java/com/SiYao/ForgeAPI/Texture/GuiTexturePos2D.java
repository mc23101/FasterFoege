package com.SiYao.ForgeAPI.Texture;
/* =======================
||类名：TexturePos2D
||状态：已完成
||作者：mc23
||最后一次修改时间：2022.5.26
==========================*/
/**
 * 平面材质位置类，用来表示材质的位置
 * */
public class GuiTexturePos2D {
    /**
     * 材质UV坐标(横坐标)
     * */
    private float U;

    /**
     * 材质UV坐标(纵坐标)
     * */
    private float V;

    /**
     * 材质的宽度
     * */
    private int width;

    /**
     * 材质的高度
     * */
    private int height;

    /**
     * 材质图片的宽度
     * */
    private float maxWidth;

    /**
     * 材质图片的高度
     * */
    private float maxHeight;

    /**
     * @param u 材质UV坐标(横坐标)
     * @param v 材质UV坐标(纵坐标)
     * @param width 材质的宽度
     * @param height 材质的高度
     * @param maxWidth 材质图片的宽度
     * @param maxHeight 材质图片的高度
     * */
    public GuiTexturePos2D(float u, float v, int width, int height, float maxWidth, float maxHeight) {
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
