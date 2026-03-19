package com.example.demo.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String method = request.getMethod();
        String uri = request.getRequestURI();

        // 放行：POST /api/users（注册接口）
        boolean isCreateUser = "POST".equalsIgnoreCase(method) && "/api/users".equals(uri);
        // 放行：GET /api/users/*（查询用户接口）
        boolean isGetUser = "GET".equalsIgnoreCase(method) && uri.startsWith("/api/users/");

        if (isCreateUser || isGetUser) {
            return true;
        }

        // 校验Token（无Token则拦截）
        String token = request.getHeader("Authorization");
        if (token == null || token.isEmpty()) {
            response.setContentType("application/json;charset=UTF-8");
            String json = "{\"code\":401,\"msg\":\"非法操作：敏感动作需要登录凭证\"}";
            response.getWriter().write(json);
            return false;
        }
        return true;
    }
}