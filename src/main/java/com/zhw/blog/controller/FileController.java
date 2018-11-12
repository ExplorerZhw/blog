package com.zhw.blog.controller;

import com.zhw.blog.util.UUIDUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author zhw
 * @Date 2018/11/3 21:12
 * @DESC TODO
 */
@RestController
@RequestMapping(value = "/file")
public class FileController {
    @RequestMapping("/upload")
    public Map<String, String> uploadFile(HttpServletRequest request) {
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
            in = file.getInputStream();
            out = new FileOutputStream(filePath);
            byte buffer[] = new byte[1024];
            int len = 0;
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
            System.out.println(file.getOriginalFilename());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (in != null) {
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
}
