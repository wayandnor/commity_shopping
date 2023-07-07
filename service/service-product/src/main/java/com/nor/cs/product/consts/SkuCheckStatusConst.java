package com.nor.cs.product.consts;

public enum SkuCheckStatusConst {
    CHECKED(1),
    UNCHECKED(0);
    private final Integer checkStatus;
    SkuCheckStatusConst(Integer checkStatus) {
        this.checkStatus = checkStatus;
    }
    
    public Integer getCheckStatus(){
        return checkStatus;
    }
}
