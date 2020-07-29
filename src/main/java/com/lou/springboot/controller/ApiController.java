package com.lou.springboot.controller;

import com.lou.springboot.common.Result;
import com.lou.springboot.common.ResultGenerator;
import com.lou.springboot.dao.UserDao;
import com.lou.springboot.entity.User;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api")

public class ApiController {
    @Resource
    UserDao userDao;
    // 查询一条记录
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public Object getOne(HttpServletRequest req) {

        String id = req.getParameter("id");

        if (id == null) {
            return ResultGenerator.getFailResult("缺少必要参数");
        }
        List user = userDao.getUserById(id);
        if (user == null) {
            return ResultGenerator.getFailResult("未查询到数据");
        }
        return ResultGenerator.genSuccessResult(user);
    }
    // 查询所有记录
    @RequestMapping(value = "/usersAll", method = RequestMethod.GET)
    public Object queryAll(){
        List users = userDao.findAllUsers();
        return ResultGenerator.genSuccessResult(users);
    }
    //新增一条记录
    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public Result insert(User user) {
        // 参数验证
        if (StringUtils.isEmpty(user.getName()) || StringUtils.isEmpty(user.getPassword())) {
            return ResultGenerator.getFailResult("缺少参数");
        }
        return ResultGenerator.genSuccessResult(userDao.insertUser(user) > 0);
    }
    // 修改一条记录
    @RequestMapping(value = "/users", method = RequestMethod.PUT)
    public Result<Boolean> update(User tempUser) {
        //参数验证
        if (tempUser.getId() == null || tempUser.getId() < 1 || StringUtils.isEmpty(tempUser.getName()) || StringUtils.isEmpty(tempUser.getPassword())) {
            return ResultGenerator.getFailResult("缺少参数");
        }
        //实体验证，不存在则不继续修改操作
        List user = userDao.getUserById(tempUser.getId().toString());
        if (user == null) {
            return ResultGenerator.getFailResult("不存在此数据");
        }
        return ResultGenerator.genSuccessResult(userDao.updUser(tempUser) > 0);
    }
    // 删除一条记录
    @RequestMapping(value = "/users", method = RequestMethod.DELETE)
    public Result deleteUser(User user) {
        if (user.getId() == null || user.getId() < 1) {
            return ResultGenerator.getFailResult("请输入有效id");
        }
        return ResultGenerator.genSuccessResult(userDao.delUser(user.getId()));
    }
}
