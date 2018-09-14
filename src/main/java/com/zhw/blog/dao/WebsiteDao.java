package com.zhw.blog.dao;

import com.zhw.blog.model.Website;

import java.util.List;

public interface WebsiteDao {
    int insert(Website website);

    List<Website> selectWebsites();

    Website getByWebsiteId(String websiteId);
}
