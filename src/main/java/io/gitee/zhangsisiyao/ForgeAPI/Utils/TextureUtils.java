package io.gitee.zhangsisiyao.ForgeAPI.Utils;

import cn.hutool.core.img.gif.GifDecoder;
import org.apache.commons.io.IOUtils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class TextureUtils {

    /**
     * 判断文件是否为图片文件(GIF,PNG,JPG)
     * @param inputStream 图片输入流
     * @return 是否为Gif图片
     */
    public static boolean isGif(InputStream inputStream) {
        byte[] b = new byte[10];
        int l = -1;
        try {
            l = inputStream.read(b);
            IOUtils.closeQuietly(inputStream);
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

    public static ByteArrayOutputStream cloneInputStream(InputStream input) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = input.read(buffer)) > -1) {
                baos.write(buffer, 0, len);
            }
            baos.flush();
            return baos;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 获取Gif图片的所有图片帧
     * @param stream Gif图片的文件
     * @return gif的所有图片帧
     * */
    public static List<BufferedImage> getGifFrame(InputStream stream){
        GifDecoder d = new GifDecoder();
        d.read(stream);
        try {
            stream.reset();
        } catch (IOException e) {
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

    /**
     * 获取Gif图片的帧与帧之间的时间间隔
     * @param stream Gif图片的文件
     * @return 帧与帧之间的时间间隔
     * */
    public static  List<Integer> getGifTime(InputStream stream){
        GifDecoder gif = new GifDecoder();
        gif.read(stream);
        try {
            stream.reset();
        } catch (IOException e) {
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
