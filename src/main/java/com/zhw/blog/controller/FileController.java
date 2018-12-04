package com.zhw.blog.controller;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import com.zhw.blog.util.ImageUtils;
import com.zhw.blog.util.UUIDUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author zhw
 * @Date 2018/11/3 21:12
 * @DESC TODO
 */
@RestController
@RequestMapping(value = "/file")
public class FileController {

    @RequestMapping(value = "/image/upload", method = RequestMethod.POST)
    public Map<String, String> uploadImage(HttpServletRequest request) {
        String relativePath = "/WEB-INF/classes/static/image/upload/";
        String realPath = request.getServletContext().getRealPath(relativePath);
        Map<String, String> resultMap = new HashMap<>();
        String fileName = "";
        String filePath = "";
        InputStream in = null;
        FileOutputStream out = null;
        try {
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            MultipartFile file = multipartRequest.getFile("articleImg");
            fileName = file.getOriginalFilename();
            fileName = fileName.substring(fileName.lastIndexOf("."));
            fileName = UUIDUtil.getUuidStr() + fileName;
            File folder = new File(realPath);
            if (!folder.isDirectory()) {
                folder.mkdirs();
            }
            filePath = realPath + fileName;
            // 保存缩略图
            in = file.getInputStream();
            fileName = ImageUtils.zoomImage(in, realPath, fileName, 800, 600);
            // 保存原图
            in = file.getInputStream();
            out = new FileOutputStream(filePath);
            byte buffer[] = new byte[1024];
            int len = 0;
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        resultMap.put("success", "true");
        resultMap.put("file_path", "/static/image/upload/" + fileName);
        return resultMap;
    }

    @RequestMapping(value = "/image/getBingImgs", method = RequestMethod.GET)
    public String getBingImgs(HttpServletRequest request) {
        String result = "";
        InputStream in = null;
        InputStreamReader isr = null;
        BufferedReader bufr = null;
        try {
            String idx = request.getParameter("idx");
            idx = StringUtils.isNotEmpty(idx) ? idx : "0";
            URL url = new URL("http://cn.bing.com/HPImageArchive.aspx?format=js&n=6&idx=" + idx);
            in = url.openStream();
            isr = new InputStreamReader(in);
            bufr = new BufferedReader(isr);
            String str;
            while ((str = bufr.readLine()) != null) {
                result = str;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufr != null) {
                    bufr.close();
                }
                if (isr != null) {
                    isr.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    @RequestMapping(value = "/image/saveBingImgs", method = RequestMethod.GET)
    public String saveBingImgs(HttpServletRequest request) {
        String result = "";
        String relativePath = "/WEB-INF/classes/static/image/bg/";
        String realPath = request.getServletContext().getRealPath(relativePath);
        String urlStr = request.getParameter("url");
        InputStream in = null;
        OutputStream os = null;
        try {
            String suffix = urlStr.substring(urlStr.lastIndexOf("."));
            String fileName = "";
            URL url = new URL(urlStr);
            File folder = new File(realPath);
            if (folder.isDirectory()) {
                File[] files = folder.listFiles();
                fileName = String.valueOf(files.length + 1) + suffix;
            }
            File file = new File(realPath + fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            InputStream is = url.openStream();
            Image src = ImageIO.read(is);
            float w = src.getWidth(null);
            float h = src.getHeight(null);
            os = new FileOutputStream(file);
            BufferedImage bufferedImage = new BufferedImage((int) w, (int) h, BufferedImage.TYPE_INT_RGB);
            bufferedImage.getGraphics().drawImage(src.getScaledInstance((int) w, (int) h, Image.SCALE_SMOOTH), 0, 0, null);
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(os);
            encoder.encode(bufferedImage);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
