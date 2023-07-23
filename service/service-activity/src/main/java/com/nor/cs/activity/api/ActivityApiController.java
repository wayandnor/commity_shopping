package com.nor.cs.activity.api;

import com.nor.cs.activity.service.api.ActivityInfoService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
