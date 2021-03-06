package com.zhw.blog.util;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import org.apache.commons.lang3.StringUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * 图片工具类
 */
public class ImageUtils {

    public static byte[] addText(File imageFile, String text) {
        try {
            BufferedImage baseImage = ImageIO.read(imageFile);
            int xPos = baseImage.getMinX();
            int yPos = baseImage.getMinY();
            int width = baseImage.getWidth(null);
            int height = baseImage.getHeight(null);
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = image.createGraphics();
            //将图片背景设置为透明
            image = g.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);
            g.dispose();

            g = image.createGraphics();
            g.drawImage(baseImage, 0, 0, width, height, null);
            g.setFont(new Font("宋体", Font.BOLD, 13));
            int strWidth = g.getFontMetrics().stringWidth(text);
            int strHeight = g.getFontMetrics().getHeight();
            System.out.println("width=" + width);
            System.out.println("strWidth=" + strWidth);
            System.out.println("strHeight=" + strHeight);
            g.setColor(Color.WHITE);
            g.drawString(text, (width - strWidth) / 2, 6 + strHeight / 2);
//            g.drawString(text,width-10,height-5);
            g.dispose();
            ByteArrayOutputStream imageOut = new ByteArrayOutputStream();
            ImageIO.write(image, "PNG", imageOut);
            return imageOut.toByteArray();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 图片转base64
     */
    public static String imageToBase64(String imagePath) {
        InputStream inputStream = null;
        byte[] data = null;
        try {
            inputStream = new FileInputStream(imagePath);
            data = new byte[inputStream.available()];
            inputStream.read(data);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
        // 加密
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);
    }

    /**
     * BASE转图片
     *
     * @param baseStr   base64字符串
     * @param imagePath 生成的图片
     * @return
     */
    public static boolean base64ToImage(String baseStr, String imagePath) {
        if (baseStr == null) {
            return false;
        }
        BASE64Decoder decoder = new BASE64Decoder();
        OutputStream out = null;
        try {
            // 解密
            byte[] b = decoder.decodeBuffer(baseStr);
            // 处理数据
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            out = new FileOutputStream(imagePath);
            out.write(b);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /*
     * 获取base64字段中的图片类型
     */
    public static String getImageTypeFromBase64(String base64) {
        String str = base64.substring(0, base64.indexOf(";base64,"));
        str = str.substring(str.indexOf("/") + 1);
        return str;
    }

    /*
     * 获取base64字符串（没有描述文件类型的前缀）
     */
    public static String getImageBase64String(String base64) {
        if (StringUtils.isNotEmpty(base64) && base64.contains(";base64,")) {
            return base64.substring(base64.indexOf(";base64,") + 8);
        } else {
            return base64;
        }
    }

    /*
     * 获取base64字符串（有描述文件类型的前缀）
     * @param base64
     * @param suffix 文件后缀
     */
    public static String getImageBase64String(String base64, String suffix) {
        if (StringUtils.isNotEmpty(base64) && StringUtils.isNotEmpty(suffix) && !base64.contains(";base64,")) {
            return "data:image/" + suffix + ";base64," + base64;
        } else {
            return base64;
        }
    }

    /**
     * 等比缩放图片
     *
     * @param imgSrc
     * @param fileName
     * @param width
     * @param height
     * @return
     * @throws IOException
     */
    public static String zoomImage(InputStream is,String imgSrc, String fileName, float width, float height) throws IOException {
        OutputStream os = null;
        try {
            String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
            fileName = fileName.substring(0, fileName.lastIndexOf("."));
            Image src = ImageIO.read(is);
            float w = src.getWidth(null);
            float h = src.getHeight(null);
            if (width > 0 && height > 0 && w > 0 && h > 0) {
                // 初始宽高均大于设定时进行缩放
                if (width < w && height < h) {
                    float percW = width / w;
                    float percH = height / h;
                    float trmp = percW < percH ? percW : percH;
                    float prec = (float) (Math.round(trmp * 100)) / 100;
                    fileName = prec >= 1 ? fileName : fileName + "_" + prec + "." + suffix;
                    os = new FileOutputStream(imgSrc + "/" + fileName);
                    w = w * prec;
                    h = h * prec;
                    BufferedImage bufferedImage = new BufferedImage((int) w, (int) h, BufferedImage.TYPE_INT_RGB);
                    bufferedImage.getGraphics().drawImage(src.getScaledInstance((int) w, (int) h, Image.SCALE_SMOOTH), 0, 0, null);
                    JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(os);
                    encoder.encode(bufferedImage);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                is.close();
            }
            if (os != null) {
                os.close();
            }
        }
        return fileName;
    }
}
