package com.stu212306144.helloserver.util;

// 加Lombok自动生成getter，不用自己写
import lombok.Getter;

@Getter
public enum ResultCode {
    SUCCESS(200, "成功"),
    USER_NOT_EXIST(404, "用户不存在");

    // 枚举字段必须是final的
    private final Integer code;
    private final String msg;

    ResultCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}