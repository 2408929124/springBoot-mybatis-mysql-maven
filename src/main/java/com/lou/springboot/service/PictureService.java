package com.lou.springboot.service;

import com.lou.springboot.entity.Picture;
import com.lou.springboot.utils.PageResult;
import com.lou.springboot.utils.PageUtil;

public interface PictureService {
    /**
     * 查询列表数据
     * 分页
     */
    PageResult getPicturePage(PageUtil pageUtil);
    /**
     * 新增图片对象
     * */
    int save(Picture picture);
    /**
     * @param id
     * 删除
     * */
    int delete(Integer id);
    /**
     * 修改
     * */
    int update(Picture picture);
}
