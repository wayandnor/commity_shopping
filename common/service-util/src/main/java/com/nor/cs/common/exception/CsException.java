package com.nor.cs.common.exception;

import com.nor.cs.common.result.ResultCodeEnum;
import lombok.Data;

@Data
public class CsException extends RuntimeException {
    private int code;

    public CsException(String message, int code) {
        super(message);
        this.code = code;
    }

    public CsException(ResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum.getMessage());
        this.code = resultCodeEnum.getCode();
    }

    @Override
    public String toString() {
        return "CsException{" +
                "code=" + code +
                "message" + this.getMessage() +
                '}';
    }
}
