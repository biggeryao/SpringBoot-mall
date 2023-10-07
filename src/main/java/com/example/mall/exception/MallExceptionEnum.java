package com.example.mall.exception;

public enum MallExceptionEnum {
    NEED_USER_NAME(10001, "用户名不能为空"),
    NEED_PASSWORD(10002, "密码不能为空"),

    PASSWORD_TOO_SHORT(10003, "密码长度不能少于八位"),

    NAME_EXISTED(10004, "不允许重名"),
    INSERT_FAIL(10004, "插入失败，请重试"),
    SYSTEM_ERROR(20000,"系统异常");

    Integer code;

    String msg;

    MallExceptionEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
