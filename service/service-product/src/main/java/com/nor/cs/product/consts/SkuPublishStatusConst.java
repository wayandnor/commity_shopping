package com.nor.cs.product.consts;

public enum SkuPublishStatusConst {
    ON_SELL(1),
    UNSHELVED(0);
    private final Integer status;

    SkuPublishStatusConst(Integer status) {
        this.status = status;
    }
    
    public Integer getStatus() {
        return status;
    }
}
