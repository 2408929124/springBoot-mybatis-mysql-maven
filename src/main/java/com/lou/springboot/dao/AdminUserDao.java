package com.lou.springboot.dao;

import com.lou.springboot.entity.AdminUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface AdminUserDao {
    // 登录
    AdminUser getAdminUserByUserNameAndPassword(@Param("userName") String userName, @Param("passwordMD5") String passwordMD5);

    // 更新用户tocken
    int updateUserToken(@Param("userId") Long id, @Param("newToken") String newToken);
    /**
     * 根据参数查询用户列表
     *
     * @param param
     * @return
     */
    List<AdminUser> findAdminUsers(Map param);

    /**
     * 查询用户总数
     *
     * @param param
     * @return
     */
    int getTotalAdminUser(Map param);
    /**
     * 根据用户名查询记录
     * */
    AdminUser getAdminUserByUserName(String userName);
    /**
     * 新增用户
     * */
    int addUser(AdminUser user);
    /**
     * 根据id查询用户
     * */
    AdminUser getAdminUserById(Long id);
    /**
     * 修改密码
     * */
    int updateUserPassword(@Param("userId") long userId, @Param("newPassword") String newPassword);
    /**
     * 批量删除
     * @param ids
     * */
    int deleteBatch(Object[] ids);
    /**
     * 根据userToken获取用户记录
     * */
    AdminUser getAdminUserByToken(String userToken);
}
