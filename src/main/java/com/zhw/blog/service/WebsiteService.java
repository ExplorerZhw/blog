package com.zhw.blog.service;

import com.zhw.blog.model.Website;

import java.util.List;
import java.util.Map;

public interface WebsiteService {
    int addWebsite(Website website);

    List<Website> findAllWebsites();

    Map<String, List<Website>> findAllClassifyWebsites();

    Website getOne(String websiteId);

    void update(Website website);
}
