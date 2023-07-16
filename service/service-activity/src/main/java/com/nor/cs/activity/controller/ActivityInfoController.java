package com.nor.cs.activity.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nor.cs.activity.service.api.ActivityInfoService;
import com.nor.cs.common.result.Result;
import com.nor.cs.model.activity.ActivityInfo;
import com.nor.cs.model.product.SkuInfo;
import com.nor.cs.model.vo.activity.ActivityRuleVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 活动表 前端控制器
 * </p>
 *
 * @author north
 * @since 2023-07-13
 */
@RestController
@RequestMapping("/admin/activity/activityInfo")
//@CrossOrigin
public class ActivityInfoController {
    @Resource
    private ActivityInfoService activityInfoService;
    @PostMapping
    public Result createActivity(@RequestBody ActivityInfo activityInfo) {
        activityInfoService.save(activityInfo);
        return Result.successWithOutData();
    }
    
    @GetMapping("{pageNum}/{pageSize}")
    public Result<IPage<ActivityInfo>> getActivityPage(@PathVariable Long pageNum,
                                                             @PathVariable Long pageSize){
        Page<ActivityInfo> pageParam = new Page<>(pageNum,pageSize);
        IPage<ActivityInfo> activityInfos = activityInfoService.queryActivityPage(pageParam);
        return Result.successWithData(activityInfos);
    }
    
    @GetMapping("{id}")
    public Result<ActivityInfo> queryActivityById(@PathVariable Long id) {
        ActivityInfo activityInfo = activityInfoService.getById(id);
        activityInfo.setActivityTypeString(activityInfo.getActivityType().getComment());
        return Result.successWithData(activityInfo);
    }
    
    @PutMapping
    public Result updateActivity(@RequestBody ActivityInfo activityInfo) {
        activityInfoService.updateById(activityInfo);
        return Result.successWithOutData();
    }
    
    @DeleteMapping("{id}")
    public Result removeById(@PathVariable Long id) {
        activityInfoService.removeById(id);
        return Result.successWithOutData();
    }
    
    @DeleteMapping("batchRemove")
    public Result batchRemove(@RequestBody List<Long> ids) {
        activityInfoService.removeByIds(ids);
        return  Result.successWithOutData();
    }
    
    @GetMapping("rules/{activityId}")
    public Result<Map<String,Object>> getActivityRules(@PathVariable Long activityId) {
        Map<String, Object> rules = activityInfoService.queryActivityRules(activityId);
        return Result.successWithData(rules);
    }
    
    @PostMapping("rules")
    public Result saveActivityRules(@RequestBody ActivityRuleVo activityRuleVo) {
        activityInfoService.saveActivityRules(activityRuleVo);
        return Result.successWithOutData();
    }
    
    @GetMapping("findSkuInfoByKeyword/{keyword}")
    public Result<List<SkuInfo>> findSkuInfoByKeyword(@PathVariable String keyword) {
        List<SkuInfo> skuInfos = activityInfoService.findSkuInfoByKeyword(keyword);
        return Result.successWithData(skuInfos);
    }
}

