package com.lou.springboot.controller;

import com.lou.springboot.common.Result;
import com.lou.springboot.common.ResultGenerator;
import com.lou.springboot.entity.Picture;
import com.lou.springboot.service.PictureService;
import com.lou.springboot.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/pictures")
public class PictureController {
    @Autowired
    private PictureService pictureService;
    /**
     * 分页列表
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result list(@RequestParam Map<String, Object> params) {
        if (StringUtils.isEmpty(params.get("page")) || StringUtils.isEmpty(params.get("limit"))) {
            return ResultGenerator.getFailResult("参数异常");
        }
        // 查询列表参数
        PageUtil pageUtil = new PageUtil(params);
        return ResultGenerator.genSuccessResult(pictureService.getPicturePage(pageUtil));
    }
    /**
     * 上传
     * @param  path
     * @param  remark
     * */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result save(Picture picture) {
        if (StringUtils.isEmpty(picture.getPath()) || StringUtils.isEmpty(picture.getRemark())) {
            return ResultGenerator.getFailResult("参数异常");
        }
        if (pictureService.save(picture) > 0) {
            return ResultGenerator.genSuccessResult("新增成功");
        } else {
            return ResultGenerator.getFailResult("新增失败");
        }
    }
    /**
     * 删除图片
     * */
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public Result delete(HttpServletRequest picture) {
        Integer id = Integer.parseInt(picture.getParameter("id"));
        if (StringUtils.isEmpty(id)) {
            return ResultGenerator.getFailResult("参数异常");
        }
        if (pictureService.delete(id) > 0) {
            return ResultGenerator.genSuccessResult("删除成功");
        } else {
            return ResultGenerator.getFailResult("删除失败");
        }
    }
    /**
     * 修改图片
     * */
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public Result update(Picture picture) {
        if (picture.getId() == null || StringUtils.isEmpty(picture.getPath())) {
            return ResultGenerator.getFailResult("参数异常");
        }
        if (pictureService.update(picture) > 0) {
            return ResultGenerator.genSuccessResult("编辑成功");
        } else {
            return ResultGenerator.getFailResult("编辑失败");
        }
    }

}









