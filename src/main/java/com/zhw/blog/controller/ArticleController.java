package com.zhw.blog.controller;

import com.zhw.blog.model.Article;
import com.zhw.blog.service.ArticleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(value = "/manage/Article")
public class ArticleController {

    @Autowired
    ArticleService articleService;

    @RequestMapping("/listData")
    public List<Article> listData(Article article) {
        return articleService.listData(article);
    }

    @RequestMapping("/save")
    public void save(Article article) {
        if (StringUtils.isNotEmpty(article.getArticleId())) {
            articleService.update(article);
        } else {
            articleService.insert(article);
        }
    }

//    @RequestMapping("/deleteById")
//    public void deleteById(HttpServletRequest request) {
//        String articleId = request.getParameter("articleId");
//        articleService.deleteById(articleId);
//    }

    @RequestMapping("/getOne")
    public Article getOne(HttpServletRequest request) {
        String articleId = request.getParameter("articleId");
        if (StringUtils.isEmpty(articleId)) {
            return new Article();
        } else {
            return articleService.getById(articleId);
        }
    }
}
