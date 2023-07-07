package com.nor.cs.product.consts;

import io.swagger.models.auth.In;

public enum SkuNewExclusiveConst {
    NEW_EXCLUSIVE(1),
    NOT_NEW_EXCLUSIVE(0);
    private final Integer exclusive;
    SkuNewExclusiveConst(Integer exclusive) {
        this.exclusive = exclusive;
    }
    Integer getExclusive() {
        return this.exclusive;
    }
}
