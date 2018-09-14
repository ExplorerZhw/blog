package com.zhw.blog.controller;

import com.zhw.blog.model.Website;
import com.zhw.blog.service.WebsiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public Website getOne(@RequestBody Map<String, Object> reqMap) {
        String wsid = reqMap.get("wsid").toString();
        Website website = websiteService.getOne(wsid);
        return website;
    }
}

