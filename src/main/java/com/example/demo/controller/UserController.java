package com.example.demo.controller;

import com.example.demo.common.Result;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户接口控制器
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    /**
     * 查询单个用户（放行接口）
     * @param id 用户ID
     * @return 统一响应结果
     */
    @GetMapping("/{id}")
    public Result<String> getUser(@PathVariable Long id) {
        String data = "查询成功，用户ID：" + id;
        return Result.success(data);
    }

    /**
     * 删除单个用户（需要Token验证的接口）
     * @param id 用户ID
     * @return 统一响应结果
     */
    @DeleteMapping("/{id}")
    public Result<String> deleteUser(@PathVariable Long id) {
        String data = "删除成功，用户ID：" + id;
        return Result.success(data);
    }
}