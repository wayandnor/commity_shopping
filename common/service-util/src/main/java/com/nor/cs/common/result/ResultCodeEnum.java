package com.nor.cs.common.result;

import lombok.Getter;

@Getter
public enum ResultCodeEnum {
    SUCCESS(200, "成功"),
    FAILED(201, "失败"),

    REGION_WARE_ALREADY_EXIST(240,"该区域已经开通"),
    
    UPDATE_PRODUCT_PUBLISH_STATUS_FAIL(301, "更新上架状态失败"),
    UPDATE_PRODUCT_CHECK_STATUS_FAIL(302, "更新审核状态失败");

    private ResultCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private int code;
    private String message;
}
