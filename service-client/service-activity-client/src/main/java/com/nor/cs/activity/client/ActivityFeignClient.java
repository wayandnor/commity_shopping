package com.nor.cs.activity.client;

import com.nor.cs.model.order.CartInfo;
import com.nor.cs.model.order.CartInfoVo;
import com.nor.cs.model.order.OrderConfirmVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @version 1.0
 * @author: Nor Way
 * @description:
 * @date 2023/7/22 14:39
 */
@FeignClient("service-activity")
@RequestMapping("/api/activity")
public interface ActivityFeignClient {
    @PostMapping("inner/activity")
    Map<Long, List<String>> getActivityList(@RequestBody List<Long> skuIdList);

    @GetMapping("inner/findActivityAndCoupon/{skuId}/{userId}")
    Map<String,Object> findActivityAndCoupon(@PathVariable Long skuId,
                                                    @PathVariable Long userId);
    
    @GetMapping("inner/findCartActivityAndCoupon/{userId}")
    OrderConfirmVo findCartActivityAndCoupon(@RequestBody List<CartInfo> cartInfoVoList,
                                                    @PathVariable Long userId);
}
