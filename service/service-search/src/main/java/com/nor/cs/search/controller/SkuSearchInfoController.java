package com.nor.cs.search.controller;

import com.nor.cs.common.result.Result;
import com.nor.cs.search.service.api.SkuService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @version 1.0
 * @author: Nor Way
 * @description:
 * @date 2023/7/8 17:34
 */
@RestController
@RequestMapping("api/sku-search-info")
public class SkuSearchInfoController {
    @Resource
    private SkuService skuService;
    
    @PutMapping("inner/launch/{skuId}")
    public Result launchSku(@PathVariable Long skuId){
        skuService.addSkuInfo(skuId);
        return Result.successWithOutData();
    }

    @PutMapping("inner/pull/{skuId}")
    public Result pullSku(@PathVariable Long skuId){
        skuService.removeSkuInfo(skuId);
        return Result.successWithOutData();
    }
}
