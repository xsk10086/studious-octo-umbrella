package com.stu212306144.helloserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 开启跨域支持（前后端分离必须开）
                .cors(Customizer.withDefaults())
                // 关闭CSRF防护（前后端分离必须关，不然POST请求会被拦截）
                .csrf(csrf -> csrf.disable())
                // 无状态会话，不使用Session（避免依赖浏览器Cookie）
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                // 核心：配置接口权限
                .authorizeHttpRequests(auth -> auth
                        // 放行：注册接口（POST /api/users）和登录接口（POST /api/users/login）
                        .requestMatchers(HttpMethod.POST, "/users", "/users/login").permitAll()
                        // 其他所有接口 → 必须登录认证才能访问
                        .anyRequest().authenticated()
                )
                // 关闭Spring Security默认的登录页面
                .formLogin(form -> form.disable())
                // 关闭HTTP Basic认证弹窗
                .httpBasic(basic -> basic.disable());

        return http.build();
    }
}