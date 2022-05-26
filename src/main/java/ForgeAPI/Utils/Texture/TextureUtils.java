package ForgeAPI.Utils.Texture;

import cn.hutool.core.img.gif.GifDecoder;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class TextureUtils {
    /**
     * 判断文件是否为图片文件(GIF,PNG,JPG)
     * @param srcFilePath
     * @return
     */
    public static boolean isGif(File srcFilePath) {
        FileInputStream imgFile = null;
        byte[] b = new byte[10];
        int l = -1;
        try {
            imgFile = new FileInputStream(srcFilePath);
            l = imgFile.read(b);
            imgFile.close();
        } catch (Exception e) {
            return false;
        }

        if (l == 10) {
            byte b0 = b[0];
            byte b1 = b[1];
            byte b2 = b[2];
            byte b3 = b[3];
            byte b6 = b[6];
            byte b7 = b[7];
            byte b8 = b[8];
            byte b9 = b[9];

            if (b0 == (byte) 'G' && b1 == (byte) 'I' && b2 == (byte) 'F') {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public static List<BufferedImage> getGifFrame(File file){
        GifDecoder d = new GifDecoder();
        try {
            d.read(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        int n = d.getFrameCount();
        List<BufferedImage> frames = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            BufferedImage frame = d.getFrame(i);  // frame i
            int t = d.getDelay(i);  // display duration of frame in milliseconds
            // do something with frame ，比如gif打水印，非法图片鉴定等
            d.getDelay(i);
            frames.add(frame);
        }
        return frames;
    }


    public static  List<Integer> getGifTime(File file){
        GifDecoder gif = new GifDecoder();
        try {
            gif.read(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        int n = gif.getFrameCount();
        List<Integer> times = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            int t = gif.getDelay(i);
            times.add(t);
        }
        return times;
    }

}
