package com.stu212306144.helloserver.controller;

import com.stu212306144.helloserver.entity.SysUser;
import com.stu212306144.helloserver.entity.UserInfo;
import com.stu212306144.helloserver.service.UserService;
import com.stu212306144.helloserver.util.Result;
import com.stu212306144.helloserver.vo.UserDetailVO;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    @Resource
    private UserService userService;

    // 新增：用户注册接口（实验要求放行）
    @PostMapping
    public Result<String> register(@RequestBody SysUser user) {
        // 这里写简单的返回逻辑即可，实验用不用写数据库操作都可以
        return Result.success("注册成功！用户名：" + user.getUsername());
    }

    // 新增：用户登录接口（实验要求放行）
    @PostMapping("/login")
    public Result<String> login(@RequestBody SysUser user) {
        // 这里写简单的返回逻辑即可，实验用不用写校验都可以
        return Result.success("登录成功！欢迎：" + user.getUsername());
    }

    // 你原来的用户详情接口（实验要求拦截）
    @GetMapping("/{id}/detail")
    public Result<UserDetailVO> getDetail(@PathVariable Long id) {
        return userService.getUserDetail(id);
    }

    // 你原来的更新接口（实验要求拦截）
    @PutMapping("/{id}/detail")
    public Result<String> update(@PathVariable Long id, @RequestBody UserInfo info) {
        info.setUserId(id);
        return userService.updateUserInfo(info);
    }

    // 你原来的删除接口（实验要求拦截）
    @DeleteMapping("/{id}")
    public Result<String> delete(@PathVariable Long id) {
        return userService.deleteUser(id);
    }
}