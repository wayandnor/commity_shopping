package com.nor.cs.common.result;

import lombok.Getter;

@Getter
public enum ResultCodeEnum {
    SUCCESS(200, "成功"),
    FAILED(201, "失败"),

    REGION_WARE_ALREADY_EXIST(240, "该区域已经开通"),

    UPDATE_PRODUCT_PUBLISH_STATUS_FAIL(301, "更新上架状态失败"),
    FETCH_ACCESSTOKEN_FAILD(218, "获取accessToken失败"),
    UPDATE_PRODUCT_CHECK_STATUS_FAIL(302, "更新审核状态失败"),
    DATA_ERROR(204, "数据异常"),
    URL_ENCODE_ERROR( 216, "URL编码失败"),
    ILLEGAL_REQUEST(205, "非法请求"),
    ORDER_STOCK_FALL(204, "订单库存锁定失败"),
    SKU_LIMIT_ERROR(230, "购买个数不能大于限购个数");

    private ResultCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private int code;
    private String message;
}
