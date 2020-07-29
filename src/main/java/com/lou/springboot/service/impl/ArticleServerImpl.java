package com.lou.springboot.service.impl;

import com.lou.springboot.dao.ArticleDao;
import com.lou.springboot.entity.Article;
import com.lou.springboot.service.ArticleServer;
import com.lou.springboot.utils.PageResult;
import com.lou.springboot.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("ArticleServer")
public class ArticleServerImpl implements ArticleServer {
    @Autowired
    private ArticleDao articleDao;

    @Override
    public PageResult getArticlePage(PageUtil pageUtil) {
        List<Article> articleList = articleDao.findArticles(pageUtil);
        int total = articleDao.getTotalArticles(pageUtil);
        PageResult pageResult = new PageResult(articleList, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }

    @Override
    public int save(Article article) {
        return articleDao.insertArticle(article);
    }

    @Override
    public int update(Article article) {
        return articleDao.updArticle(article);
    }

    @Override
    public Article queryObject(Integer id) {
        Article article = articleDao.getArticleById(id);
        if (article != null) {
            return article;
        }
        return null;
    }
    @Override
    public int delete(Integer id) {
        return articleDao.delArticle(id);
    }

}
