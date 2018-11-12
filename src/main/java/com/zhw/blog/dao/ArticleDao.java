package com.zhw.blog.dao;

import com.zhw.blog.model.Article;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleDao {
    int insert(Article article);

    List<Article> listData(Article article);

    Article getById(String articleId);

    void update(Article article);
}
