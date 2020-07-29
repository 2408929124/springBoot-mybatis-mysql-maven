package com.lou.springboot.dao;

import com.lou.springboot.entity.Picture;

import java.util.List;
import java.util.Map;

public interface PictureDao {
    /**
     * 返回数据列表
     * */
    List<Picture> findPictures(Map<String, Object> map);
    /**
     * 查询总数
     * */
    int getTotalPictures(Map<String, Object> map);
    /**
     * 上传图片
     * */
    int insertPicture(Picture picture);
    /**
     * 删除图片
     * */
    int deletePicture(Integer id);
    /**
     * 修改图片
     * */
    int updPicture(Picture picture);

}
