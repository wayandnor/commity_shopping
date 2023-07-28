package com.nor.cs.service.api;

import com.nor.cs.model.order.CartInfo;

import java.util.List;

public interface CartInfoService {
    void addToCart(Long userId, Long skuId, Integer skuCount);

    void deleteCart(Long skuId, Long userId);

    void deleteAllCart(Long userId);

    void batchDeleteCart(List<Long> skuIdList, Long userId);

    List<CartInfo> getCartList(Long userId);
}
