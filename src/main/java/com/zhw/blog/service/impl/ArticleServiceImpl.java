package com.zhw.blog.service.impl;

import com.zhw.blog.dao.ArticleDao;
import com.zhw.blog.model.Article;
import com.zhw.blog.service.ArticleService;
import com.zhw.blog.util.DateUtils;
import com.zhw.blog.util.ListUtils;
import com.zhw.blog.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by
 */
@Service(value = "articleService")
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleDao articleDao;//这里会报错，但是并不会影响

    @Override
    public int insert(Article article) {
        article.setArticleId(UUIDUtil.getUuidStr());
        article.setCreateTime(DateUtils.getDateTime());
        return articleDao.insert(article);
    }

    @Override
    public List<Article> listData(Article article) {
        return articleDao.listData(article);
    }

    @Override
    public Article getById(String articleId) {
        Article article = new Article();
        article.setArticleId(articleId);
        List<Article> articleList = articleDao.listData(article);
        if (ListUtils.isNotEmpty(articleList)) {
            return articleList.get(0);
        } else {
            return null;
        }
    }

    @Override
    public void update(Article article) {
        article.setUpdateTime(DateUtils.getDateTime());
        articleDao.update(article);
    }
}