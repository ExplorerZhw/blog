package com.zhw.blog.service;

import com.zhw.blog.model.Article;

import java.util.List;

public interface ArticleService {
    int insert(Article article);

    List<Article> listData(Article article);

    Article getById(String articleId);

    void update(Article article);
}
