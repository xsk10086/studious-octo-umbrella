package com.stu212306144.helloserver.service;

import com.stu212306144.helloserver.entity.UserInfo;
import com.stu212306144.helloserver.util.Result;
import com.stu212306144.helloserver.vo.UserDetailVO;

public interface UserService {
    Result<UserDetailVO> getUserDetail(Long userId);
    Result<String> updateUserInfo(UserInfo userInfo);
    Result<String> deleteUser(Long userId);
}