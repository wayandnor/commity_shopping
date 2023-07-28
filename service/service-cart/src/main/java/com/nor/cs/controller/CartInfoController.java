package com.nor.cs.controller;

import com.nor.cs.activity.client.ActivityFeignClient;
import com.nor.cs.common.auth.AuthContextHolder;
import com.nor.cs.common.result.Result;
import com.nor.cs.model.order.CartInfo;
import com.nor.cs.model.order.OrderConfirmVo;
import com.nor.cs.service.api.CartInfoService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class CartInfoController {

    @Resource
    private CartInfoService cartInfoService;
    
    @Resource
    private ActivityFeignClient activityFeignClient;

    @GetMapping("addToCart/{skuId}/{skuCount}")
    public Result addToCart(@PathVariable Long skuId, @PathVariable Integer skuCount) {
        Long userId = AuthContextHolder.getUserId();
        cartInfoService.addToCart(userId, skuId, skuCount);
        return Result.successWithOutData();
    }

    @DeleteMapping("deleteCart/{skuId}")
    public Result deleteCart(@PathVariable("skuId") Long skuId) {
        Long userId = AuthContextHolder.getUserId();
        cartInfoService.deleteCart(skuId,userId);
        return Result.successWithOutData();
    }

    @DeleteMapping("deleteAllCart")
    public Result deleteAllCart() {
        Long userId = AuthContextHolder.getUserId();
        cartInfoService.deleteAllCart(userId);
        return Result.successWithOutData();
    }

    @DeleteMapping("batchDeleteCart")
    public Result batchDeleteCart(@RequestBody List<Long> skuIdList) {
        Long userId = AuthContextHolder.getUserId();
        cartInfoService.batchDeleteCart(skuIdList,userId);
        return Result.successWithOutData();
    }
    
    
    @GetMapping("cartList")
    public Result<List<CartInfo>> getCartInfo() {
        Long userId = AuthContextHolder.getUserId();
        List<CartInfo> cartInfos = cartInfoService.getCartList(userId);
        return Result.successWithData(cartInfos);
    }

    @GetMapping("activityCartList")
    public Result activityCartList() {
        // 获取用户Id
        Long userId = AuthContextHolder.getUserId();
        List<CartInfo> cartInfoList = cartInfoService.getCartList(userId);

        OrderConfirmVo orderTradeVo = activityFeignClient.findCartActivityAndCoupon(cartInfoList, userId);
        return Result.successWithData(orderTradeVo);
    }
}
