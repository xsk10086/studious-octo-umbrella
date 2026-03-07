package com.stu212306144.helloserver;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // 标记为一个控制器，返回的内容直接作为响应体
public class HelloController {

    // 定义一个 GET 接口，路径为 /hello
    @GetMapping("/hello")
    public String sayHello() {
        return "Hello, this is my first Spring Boot API!";
    }
}