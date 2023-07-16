package com.nor.cs.activity.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nor.cs.activity.mapper.CouponRangeMapper;
import com.nor.cs.activity.service.api.CouponInfoService;
import com.nor.cs.client.product.ProductFeignClient;
import com.nor.cs.common.result.Result;
import com.nor.cs.enums.CouponRangeType;
import com.nor.cs.model.activity.CouponInfo;
import com.nor.cs.model.vo.activity.CouponRuleVo;
import io.swagger.models.auth.In;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 优惠券信息 前端控制器
 * </p>
 *
 * @author north
 * @since 2023-07-13
 */
@RestController
@RequestMapping("/admin/activity/couponInfo")
//@CrossOrigin
public class CouponInfoController {
    @Resource
    private CouponInfoService couponInfoService;
    
    

    @GetMapping("{pageNum}/{pageSize}")
    public Result<IPage<CouponInfo>> queryCouponInfoByPage(
            @PathVariable Integer pageNum,
            @PathVariable Integer pageSize
    ) {
        Page<CouponInfo> pageParam = new Page<>(pageNum,pageSize);
        IPage<CouponInfo> couponInfoPage = couponInfoService.queryCouponInfoByPage(pageParam);
        return Result.successWithData(couponInfoPage);
    }
    
    @PostMapping
    public Result saveCouponInfo(@RequestBody CouponInfo couponInfo)  {
        couponInfoService.save(couponInfo);
        return Result.successWithOutData();
    }
    
    @PutMapping
    public Result updateCouponInfo(@RequestBody CouponInfo couponInfo) {
        couponInfoService.updateById(couponInfo);
        return Result.successWithOutData();
    }
    
    @DeleteMapping("{id}")
    public Result deleteCouponInfoById(@PathVariable Long id) {
        couponInfoService.deleteCouponById(id);
        return Result.successWithOutData();
    }
    
    @DeleteMapping("batchRemove")
    public Result batchRemoveCouponInfo(@RequestBody List<Long> ids) {
        couponInfoService.batchRemoveByIds(ids);
        return Result.successWithOutData();
    }
    
    @GetMapping("{id}")
    public Result<CouponInfo> queryCouponInfoById(@PathVariable Long id) {
        CouponInfo couponInfo = couponInfoService.queryCouponInfoById(id);
        return Result.successWithData(couponInfo);
    }
    
    @GetMapping("/findCouponByKeyword/{keyword}")
    public Result<List<CouponInfo>> queryCouponByKeyword(@PathVariable String keyword) {
        List<CouponInfo> couponInfos = couponInfoService.queryCouponByKeyword(keyword);
        return Result.successWithData(couponInfos);
    }
    
    @GetMapping("rules/{id}")
    public Result<Map<String, Object>> queryCouponRulesByCouponId(@PathVariable Long id) {
        Map<String, Object> result =  couponInfoService.queryCouponRules(id);
        return Result.successWithData(result);
    }
    
    @PostMapping("rules")
    public Result saveCouponRules(@RequestBody CouponRuleVo couponRuleVo) {
        couponInfoService.saveRules(couponRuleVo);
        return Result.successWithOutData();
    }
}

