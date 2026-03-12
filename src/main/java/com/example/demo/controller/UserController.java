package com.example.demo.controller; // 注意改成你自己的包名

import com.example.demo.entity.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    // 查询用户
    @GetMapping("/{id}")
    public String getUser(@PathVariable("id") Long id) {
        return "查询成功,正在返回ID为" + id + "的用户信息";
    }

    // 新增用户
    @PostMapping
    public String createUser(@RequestBody User user) {
        return "新增成功,接收到用户:" + user.getName() + ",年龄:" + user.getAge();
    }

    // 更新用户
    @PutMapping("/{id}")
    public String updateUser(@PathVariable("id") Long id, @RequestBody User user) {
        return "更新成功,ID" + id + "的用户已修改为:" + user.getName();
    }

    // 删除用户
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        return "删除成功,已移除ID为" + id + "的用户";
    }
}