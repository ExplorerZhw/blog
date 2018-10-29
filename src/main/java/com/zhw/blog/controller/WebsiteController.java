package com.zhw.blog.controller;

import com.zhw.blog.model.Website;
import com.zhw.blog.service.WebsiteService;
import com.zhw.blog.util.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/16.
 */
@RestController
@RequestMapping(value = "/website")
public class WebsiteController {

    @Autowired
    private WebsiteService websiteService;

    @RequestMapping("/add")
    public int addUser(Website website) {
        return websiteService.addWebsite(website);
    }

    @RequestMapping("/all")
    public Object findAllUser() {
        return websiteService.findAllWebsites();
    }

    @RequestMapping("/classifyData")
    public Map<String, List<Website>> findClassifyData() {
        return websiteService.findAllClassifyWebsites();
    }

    @RequestMapping("/getOne")
    public Website getOne(HttpServletRequest request, HttpServletResponse response) {
        String websiteId = request.getParameter("websiteId");
        Website website = websiteService.getOne(websiteId);
        return website;
    }

    @RequestMapping("/update")
    public void update(HttpServletRequest request, HttpServletResponse response) {
        Website website = BeanUtil.parseJsonToJavaBean(request, "json", Website.class);
        websiteService.update(website);
    }
}

