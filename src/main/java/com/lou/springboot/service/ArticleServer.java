package com.lou.springboot.service;

import com.lou.springboot.entity.Article;
import com.lou.springboot.utils.PageResult;
import com.lou.springboot.utils.PageUtil;

public interface ArticleServer {
    PageResult getArticlePage(PageUtil pageUtil);
    /**
     * 添加
     * */
    int save(Article article);
    /**
     * 修改
     * */
    int update(Article article);
    /**
     * 获取详情
     * */
    Article queryObject(Integer id);
    /**
     * 删除
     * */
    int delete(Integer id);
}
