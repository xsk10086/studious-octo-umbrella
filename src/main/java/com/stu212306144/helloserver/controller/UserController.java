package com.stu212306144.helloserver.controller;

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

    @GetMapping("/{id}/detail")
    public Result<UserDetailVO> getDetail(@PathVariable Long id) {
        return userService.getUserDetail(id);
    }

    @PutMapping("/{id}/detail")
    public Result<String> update(@PathVariable Long id, @RequestBody UserInfo info) {
        info.setUserId(id);
        return userService.updateUserInfo(info);
    }

    @DeleteMapping("/{id}")
    public Result<String> delete(@PathVariable Long id) {
        return userService.deleteUser(id);
    }
}