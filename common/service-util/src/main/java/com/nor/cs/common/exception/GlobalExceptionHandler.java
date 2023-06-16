package com.nor.cs.common.exception;

import com.nor.cs.common.result.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result handleGlobalException(Exception e) {
        e.printStackTrace();
        return Result.fail();
    }

    @ExceptionHandler(CsException.class)
    @ResponseBody
    public Result handleCsException(CsException e) {
        e.printStackTrace();
        return Result.build(null, e.getCode(), e.getMessage());
    }
}
