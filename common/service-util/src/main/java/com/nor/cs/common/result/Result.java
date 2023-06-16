package com.nor.cs.common.result;

import lombok.Data;

@Data
public class Result<T> {
    private Integer code;
    private String message;
    private T data;

    private Result() {

    }

    public static <T> Result<T> build(T data, ResultCodeEnum resultCodeEnum) {
        Result<T> result = new Result<>();
        result.setCode(resultCodeEnum.getCode());
        result.setMessage(resultCodeEnum.getMessage());
        if (data != null) {
            result.setData(data);
        }
        return result;
    }

    public static<T> Result<T> build(T data, Integer code, String message) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMessage(message);
        if (data != null) {
            result.setData(data);
        }
        return result;
    }

    public static <T> Result<T> successWithData(T data) {
        return build(data, ResultCodeEnum.SUCCESS);
    }

    public static <T> Result<T> successWithOutData() {
        return build(null, ResultCodeEnum.SUCCESS);
    }

    public static <T> Result<T> fail() {
        return build(null, ResultCodeEnum.FAILED);
    }


    
}
