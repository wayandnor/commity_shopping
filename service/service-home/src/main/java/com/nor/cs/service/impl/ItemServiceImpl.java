package com.nor.cs.service.impl;

import com.nor.cs.activity.client.ActivityFeignClient;
import com.nor.cs.client.product.ProductFeignClient;
import com.nor.cs.client.search.SkuFeignClient;
import com.nor.cs.model.product.SkuInfo;
import com.nor.cs.service.api.ItemService;
import com.sun.xml.internal.ws.util.CompletedFuture;
import io.netty.util.concurrent.CompleteFuture;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;

public class ItemServiceImpl implements ItemService {
    @Resource
    private ProductFeignClient productFeignClient;

    @Resource
    private SkuFeignClient skuFeignClient;

    @Resource
    private ActivityFeignClient activityFeignClient;

    @Resource
    private ThreadPoolExecutor threadPoolExecutor;

    @Override
    public Map<String, Object> getProductDeatail(Long skuId, Long userId) {
        Map<String, Object> result = new HashMap<>();
        CompletableFuture<Void> skuInfoCompletableFuture = CompletableFuture.runAsync(() -> {
            SkuInfo skuInfoVo = productFeignClient.getSkuInfoVoById(skuId);
            result.put("skuInfoVo", skuInfoVo);
        },threadPoolExecutor);

        CompletableFuture<Void> activityCompletableFuture = CompletableFuture.runAsync(() -> {
            Map<String, Object> activityMap = activityFeignClient.findActivityAndCoupon(skuId, userId);
            result.putAll(activityMap);
        },threadPoolExecutor);

        CompletableFuture<Void> hotCompletableFuture = CompletableFuture.runAsync(() -> {
            skuFeignClient.incrHotScore(skuId);
        },threadPoolExecutor);

        CompletableFuture.allOf(skuInfoCompletableFuture, activityCompletableFuture, hotCompletableFuture).join();
        return result;
    }
}
