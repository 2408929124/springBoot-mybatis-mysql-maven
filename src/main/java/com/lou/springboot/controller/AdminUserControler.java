package com.lou.springboot.controller;

import com.lou.springboot.common.Result;
import com.lou.springboot.common.ResultGenerator;
import com.lou.springboot.config.annotation.TokenToUser;
import com.lou.springboot.entity.AdminUser;
import com.lou.springboot.service.AdminUserService;
import com.lou.springboot.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/users")
public class AdminUserControler {
    @Autowired
    private AdminUserService adminUserService;
    // 登录
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Result login(AdminUser user) {

        Result result = ResultGenerator.getFailResult("登录失败");

        if (StringUtils.isEmpty(user.getUserName())) {
            result.setMessgae("请填写用户名");
        }
        if (StringUtils.isEmpty(user.getPassword())) {
            result.setMessgae("请填写密码");
        }
        AdminUser loginUser = adminUserService.updateTokenAndLogin(user.getUserName(), user.getPassword());

        if (loginUser != null) {
            result = ResultGenerator.genSuccessResult(loginUser);
        }
        return result;
    }
    // 列表
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result list(@RequestParam Map<String, Object> params) {
        if (StringUtils.isEmpty(params.get("page")) || StringUtils.isEmpty("limit")) {
            return ResultGenerator.getFailResult("参数异常！");
        }
        // 查询列表数据
        PageUtil pageUtil = new PageUtil(params);
        return ResultGenerator.genSuccessResult(adminUserService.getAdminUserPage(pageUtil));
    }
    // 新增
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result save(AdminUser user) {
        if (StringUtils.isEmpty(user.getUserName()) || StringUtils.isEmpty(user.getPassword())) {
            return ResultGenerator.getFailResult("参数异常");
        }
        AdminUser tempUser = adminUserService.selectByUserName(user.getUserName());
        if (tempUser != null) {
            return ResultGenerator.getFailResult("用户已存在");
        }
//        tempUser.setPassword(user.getPassword());
        if (adminUserService.save(user) > 0) {
            return ResultGenerator.genSuccessResult("添加成功");
        } else {
            return ResultGenerator.getFailResult("添加失败");
        }
    }
    // 编辑
    @RequestMapping(value = "/updatePassword", method = RequestMethod.PUT)
    public Result update(AdminUser user) {
        if (StringUtils.isEmpty(user.getPassword())) {
            return ResultGenerator.getFailResult("请输入密码");
        }
        AdminUser tempUser = adminUserService.selectById(user.getId());
        if (tempUser == null) {
            return ResultGenerator.getFailResult("无此用户");
        }
//        tempUser.setPassword(user.getPassword());
        if (adminUserService.updatePassword(user) > 0) {
            return ResultGenerator.genSuccessResult("编辑成功");
        } else {
            return ResultGenerator.getFailResult("编辑失败");
        }
    }
    // 删除
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public Result delete(@RequestBody Integer[] ids, @TokenToUser AdminUser loginUser) {
        if (loginUser == null) {
            return ResultGenerator.getFailResult("未登录");
        }
        if (ids.length < 1) {
            return ResultGenerator.getFailResult("参数异常");
        }
        System.out.println(adminUserService.deleteBatch(ids));
        if (adminUserService.deleteBatch(ids) > 0) {
            return ResultGenerator.genSuccessResult("删除成功");
        } else {
            return ResultGenerator.getFailResult("删除失败");
        }
    }
}




