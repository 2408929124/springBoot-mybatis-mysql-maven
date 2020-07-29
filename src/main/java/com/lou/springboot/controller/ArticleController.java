package com.lou.springboot.controller;

import com.lou.springboot.common.Result;
import com.lou.springboot.common.ResultGenerator;
import com.lou.springboot.entity.Article;
import com.lou.springboot.service.ArticleServer;
import com.lou.springboot.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/articles")
public class ArticleController {
    @Autowired
    private ArticleServer articleServer;
    /**
     * 列表
     * */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result list(@RequestParam Map<String, Object> params) {
        if (StringUtils.isEmpty(params.get("page")) || StringUtils.isEmpty(params.get("limit"))) {
            return ResultGenerator.getFailResult("参数异常！");
        }
        PageUtil pageUtil = new PageUtil(params);
        return ResultGenerator.genSuccessResult(articleServer.getArticlePage(pageUtil));
    }
    // 新增
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result save(Article article) {
        if (article.getAddName() == null) {
            return ResultGenerator.getFailResult("参数异常");
        }
        if (articleServer.save(article) > 0) {
            return ResultGenerator.genSuccessResult("添加成功");
        } else {
            return ResultGenerator.getFailResult("添加失败");
        }
    }
    // 修改
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public Result update(Article article) {
        if (articleServer.update(article) > 0) {
            return ResultGenerator.genSuccessResult("编辑成功");
        } else {
            return ResultGenerator.getFailResult("编辑失败");
        }
    }
    // 详情
    @RequestMapping(value = "/info/{id}", method = RequestMethod.GET)
    public Result info(@PathVariable("id") Integer id) {
        Article article = articleServer.queryObject(id);
        return ResultGenerator.genSuccessResult(article);
    }
    // 删除
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public Result delete(Integer id) {
        if (articleServer.delete(id) > 0) {
            return ResultGenerator.genSuccessResult("删除成功");
        } else {
            return ResultGenerator.getFailResult("删除失败");
        }
    }
}


