package com.lou.springboot.service;

import com.lou.springboot.entity.AdminUser;
import com.lou.springboot.utils.PageResult;
import com.lou.springboot.utils.PageUtil;

public interface AdminUserService {
    /**
     * 登录接口
     * */
    AdminUser updateTokenAndLogin(String userName, String password);
    /**
     * 用户信息查询(分页)
     * */
    PageResult getAdminUserPage(PageUtil pageUtil);
    /**
     * 根据用户名查看有无此车辆
     * */
    AdminUser selectByUserName(String userName);
    /**
     * 新增用户记录
     * */
    int save(AdminUser user);
    /**
     * 根据id查询用户
     * */
    AdminUser selectById(Long id);
    /**
     * 修改密码
     * */
    int updatePassword(AdminUser user);
    /**
     * 删除
     * */
    int deleteBatch(Integer[] ids);
    /**
     * 根据userToken获取用户记录
     * */
    AdminUser getAdminUserByToken(String userToken);

}









