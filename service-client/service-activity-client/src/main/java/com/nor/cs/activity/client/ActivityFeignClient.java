package com.nor.cs.activity.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
