package com.example.mall.exception;

public class MallException extends RuntimeException {
    private final Integer code;
    private final String message;

    public MallException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }


    public MallException(MallExceptionEnum mallExceptionEnum) {
        this(mallExceptionEnum.getCode(), mallExceptionEnum.getMsg());
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
