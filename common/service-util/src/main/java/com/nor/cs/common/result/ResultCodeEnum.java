package com.nor.cs.common.result;

import lombok.Getter;

@Getter
public enum ResultCodeEnum {
    SUCCESS(200, "成功"),
    FAILED(201, "失败"),

    REGION_WARE_ALREADY_EXIST(240,"该区域已经开通");

    private ResultCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private int code;
    private String message;
}
