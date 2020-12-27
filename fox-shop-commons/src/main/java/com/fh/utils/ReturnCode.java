package com.fh.utils;

public enum ReturnCode {
    SUCCESS(200, "操作成功"),
    ERROR(500, "操作失败"),
    USERNAME_PASSWORD_NULL(5001, "用户名密码不能为空"),
    USERNAME_NOEXIST(5002, "用户名不存在"),
    PASSWORD_ERROR(5003, "密码错误"),
    SYSTEM_ERROR(5004, "系统错误"),
    LEAVE_TIME_ERROR(5005, "请选择正确的请假时间数据"),
    LOGIN_DISABLED(6666, "登录失效重新登录"),
    LOGIN_ERROR(7777, "用户名或者密码错误"),
    NO_PERMISSION(403, "没有权限访问该方法"),
    ;

    private final Integer code;
    private final String msg;

    ReturnCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
