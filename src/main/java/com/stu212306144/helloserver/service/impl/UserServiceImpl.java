package com.stu212306144.helloserver.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.stu212306144.helloserver.entity.SysUser;
import com.stu212306144.helloserver.entity.UserInfo;
import com.stu212306144.helloserver.mapper.SysUserMapper;
import com.stu212306144.helloserver.mapper.UserInfoMapper;
import com.stu212306144.helloserver.security.JwtUtil;
import com.stu212306144.helloserver.service.UserService;
import com.stu212306144.helloserver.util.Result;
import com.stu212306144.helloserver.util.ResultCode;
import com.stu212306144.helloserver.vo.UserDetailVO;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {
    private static final String CACHE_KEY_PREFIX = "user:detail:";

    @Resource
    private UserInfoMapper userInfoMapper;
    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private StringRedisTemplate redisTemplate;
    @Resource
    private JwtUtil jwtUtil; // 注入JWT工具类

    // 登录方法（核心：生成JWT）
    @Override
    public Result<String> login(SysUser user) {
        // 1. 查询用户
        SysUser existUser = sysUserMapper.selectOne(
                new LambdaQueryWrapper<SysUser>()
                        .eq(SysUser::getUsername, user.getUsername())
        );

        if (existUser == null) {
            return Result.error(ResultCode.USER_NOT_EXIST);
        }

        // 2. 简单密码校验（实验用，生产加密）
        if (!user.getPassword().equals(existUser.getPassword())) {
            return Result.error(ResultCode.USER_NOT_EXIST);
        }

        // 3. 生成JWT并返回
        String token = jwtUtil.generateToken(existUser.getUsername());
        return Result.success(token);
    }

    // ↓↓↓ 下面是你原来的代码，不用改，保留原样 ↓↓↓
    @Override
    public Result<UserDetailVO> getUserDetail(Long userId) {
        String key = CACHE_KEY_PREFIX + userId;
        String json = redisTemplate.opsForValue().get(key);
        if (json != null && !json.isBlank()) {
            try {
                UserDetailVO vo = JSONUtil.toBean(json, UserDetailVO.class);
                return Result.success(vo);
            } catch (Exception e) {
                redisTemplate.delete(key);
            }
        }
        UserDetailVO detail = userInfoMapper.getUserDetail(userId);
        if (detail == null) return Result.error(ResultCode.USER_NOT_EXIST);
        redisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(detail), 10, TimeUnit.MINUTES);
        return Result.success(detail);
    }

    @Override
    @Transactional
    public Result<String> updateUserInfo(UserInfo userInfo) {
        if (userInfo == null || userInfo.getUserId() == null)
            return Result.error(ResultCode.USER_NOT_EXIST);
        userInfoMapper.updateById(userInfo);
        redisTemplate.delete(CACHE_KEY_PREFIX + userInfo.getUserId());
        return Result.success("更新成功");
    }

    @Override
    @Transactional
    public Result<String> deleteUser(Long userId) {
        sysUserMapper.deleteById(userId);
        userInfoMapper.delete(new LambdaQueryWrapper<UserInfo>().eq(UserInfo::getUserId, userId));
        redisTemplate.delete(CACHE_KEY_PREFIX + userId);
        return Result.success("删除成功");
    }
}