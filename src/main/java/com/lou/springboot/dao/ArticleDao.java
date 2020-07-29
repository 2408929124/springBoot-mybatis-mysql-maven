package com.lou.springboot.dao;

import com.lou.springboot.entity.Article;

import java.util.List;
import java.util.Map;

public interface ArticleDao {
    /**
     * 返回相应的数据集合
     * */
    List<Article> findArticles(Map<String, Object> map);
    /**
     * 查询总数
     * */
    int getTotalArticles(Map<String, Object> map);
    /**
     * 添加
     * */
    int insertArticle(Article article);
    /**
     * 修改
     * */
    int updArticle(Article article);
    /**
     * 详情
     * */
    Article getArticleById(Integer id);
    /**
     * 删除
     * */
    int delArticle(Integer id);
}
