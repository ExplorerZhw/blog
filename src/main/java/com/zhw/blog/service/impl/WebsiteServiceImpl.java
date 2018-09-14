package com.zhw.blog.service.impl;

import com.zhw.blog.dao.WebsiteDao;
import com.zhw.blog.model.Website;
import com.zhw.blog.service.WebsiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by
 */
@Service(value = "websiteService")
public class WebsiteServiceImpl implements WebsiteService {

    @Autowired
    private WebsiteDao websiteDao;//这里会报错，但是并不会影响

    @Override
    public int addWebsite(Website website) {
        return websiteDao.insert(website);
    }

    /*
     *
     */
    @Override
    public List<Website> findAllWebsites() {
        return websiteDao.selectWebsites();
    }

    @Override
    public Map<String, List<Website>> findAllClassifyWebsites() {
        List<Website> websiteList = websiteDao.selectWebsites();
        Map<String, List<Website>> map = websiteList.stream().collect(Collectors.groupingBy(Website::getLabelId));
        return map;
    }

    @Override
    public Website getOne(String wsid) {
        return  websiteDao.getByWebsiteId(wsid);
    }
}