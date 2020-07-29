package com.lou.springboot.service.impl;

import com.lou.springboot.dao.AdminUserDao;
import com.lou.springboot.entity.AdminUser;
import com.lou.springboot.service.AdminUserService;
import com.lou.springboot.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminUserServiceImpl implements AdminUserService {
    @Autowired
    private AdminUserDao adminUserDao;
    @Override
    public AdminUser updateTokenAndLogin(String userName, String password) {
        AdminUser adminUser = adminUserDao.getAdminUserByUserNameAndPassword(userName, MD5Util.MD5Encode(password, "UTF-8"));
        if (adminUser != null) {
            //登录后即执行修改token的操作
            String token = getNewToken(System.currentTimeMillis() + "", adminUser.getId());
            if (adminUserDao.updateUserToken(adminUser.getId(), token) > 0) {
                //返回数据时带上token
                adminUser.setUserToken(token);
                return adminUser;
            }
        }
        return null;
    }
    private String getNewToken(String sessionId, Long userId) {
        String src = sessionId + userId + NumberUtil.genRandomNum(4);
        return SystemUtil.genToken(src);
    }

    @Override
    public PageResult getAdminUserPage(PageUtil pageUtil) {
        System.out.println("111111" + pageUtil);
        // 当前页面中的数据列表
        List<AdminUser> users = adminUserDao.findAdminUsers(pageUtil);
        // 根据总条数 用于计算分页数据
        int total = adminUserDao.getTotalAdminUser(pageUtil);
        PageResult pageResult = new PageResult(users, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }

    // 根据用户名查记录
    @Override
    public AdminUser selectByUserName(String userName) {
        return adminUserDao.getAdminUserByUserName(userName);
    }
    // 新增用户记录
    @Override
    public int save(AdminUser user) {
        // 密码加密
        user.setPassword(MD5Util.MD5Encode(user.getPassword(), "UTF-8"));
        return adminUserDao.addUser(user);
    }
    // 根据id查询用户
    public AdminUser selectById(Long id) {
        return adminUserDao.getAdminUserById(id);
    }
    // 修改密码
    @Override
    public int updatePassword(AdminUser user) {
        return adminUserDao.updateUserPassword(user.getId(), MD5Util.MD5Encode(user.getPassword(), "UTF-8"));
    }
    // 删除
    @Override
    public int deleteBatch(Integer[] ids) {
        return adminUserDao.deleteBatch(ids);
    }
    // 获取用户记录
    @Override
    public AdminUser getAdminUserByToken(String userToken) {
        return adminUserDao.getAdminUserByToken(userToken);
    }

}



