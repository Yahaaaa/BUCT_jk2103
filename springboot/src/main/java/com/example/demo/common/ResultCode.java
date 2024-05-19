package com.example.demo.common;

public enum ResultCode {
    SUCCESS("0", "成功"),
    ERROR("-1", "系统异常"),
    USER_EXIST_ERROR("201", "用户名已被使用"),
    USER_ACCOUNT_ERROR("301", "账号或密码错误"),
    USER_FORMAT_ERROR("401","注册格式错误"),
    REMARK_ERROR("501","你已经被禁止评论"),
    REMARK_NULL_ERROR("502","评论为空"),
    REMARK_ILLEGAL_ERROR("503","评论含有违禁词，禁止评论")
            ;

    public String code;
    public String msg;

    ResultCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
