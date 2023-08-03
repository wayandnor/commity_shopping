package com.nor.cs.activity.api;

import com.nor.cs.activity.service.api.ActivityInfoService;
import com.nor.cs.model.order.CartInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @version 1.0
 * @author: Nor Way
 * @description:
 * @date 2023/7/22 14:17
 */
@RestController
@RequestMapping("/api/activity")
public class ActivityApiController {
    @Resource
    private ActivityInfoService activityInfoService;
    
    @PostMapping("inner/activity")
    public Map<Long, List<String>> getActivityList(@RequestBody List<Long> skuIdList){
        return activityInfoService.getActitvity(skuIdList);
    }

    @GetMapping("inner/findActivityAndCoupon/{skuId}/{userId}")
    public Map<String,Object> findActivityAndCoupon(@PathVariable Long skuId,
                                                    @PathVariable Long userId) {
        return activityInfoService.findActivityAndCoupon(skuId,userId);
    }

    @GetMapping("inner/findCartActivityAndCoupon/{userId}")
    public OrderConfirmVo findCartActivityAndCoupon(@RequestBody List<CartInfo> cartInfoList,
                                             @PathVariable Long userId){
        return activityInfoService.findCartActivityAndCoupon(cartInfoList,userId);
    }
}
