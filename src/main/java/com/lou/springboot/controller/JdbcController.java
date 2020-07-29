package com.lou.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class JdbcController {
    // 注入jdbcTemplate
    @Autowired
    JdbcTemplate jdbcTemplate;

    // 查询tb_user 表中的所有记录
    @GetMapping("/user/queryAll")
    public List<Map<String, Object>> queryAll(){
        List<Map<String, Object>> list = jdbcTemplate.queryForList("select * from tb_user");
        return list;
    }

    // 向tb_user表中增加一条记录
    @PostMapping("/user/insert")
    public Object insert(String name, String password) {
        if (StringUtils.isEmpty(name) || StringUtils.isEmpty(password)) {
            return false;
        }
        jdbcTemplate.execute("insert into tb_user(`name`,`password`) value  (\"" + name + "\",\"" + password + "\")");
        return true;
    }
}
