package com.nor.cs.product.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nor.cs.common.result.Result;
import com.nor.cs.model.product.SkuInfo;
import com.nor.cs.model.vo.product.SkuInfoQueryVo;
import com.nor.cs.model.vo.product.SkuInfoVo;
import com.nor.cs.product.service.SkuInfoService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * sku信息 前端控制器
 * </p>
 *
 * @author north
 * @since 2023-06-28
 */
@RestController
@RequestMapping("/admin/product/sku-info")
public class SkuInfoController {
    @Resource
    private SkuInfoService skuInfoService;
    
    @GetMapping("{pageNum}/{pageSize}")
    public Result<IPage<SkuInfo>> getSkuInfoPage(@PathVariable Integer pageNum,
                                                 @PathVariable Integer pageSize,
                                                 SkuInfoQueryVo skuInfoQueryVo) {
        Page<SkuInfo> pageParam = new Page<>(pageNum,pageSize);
        IPage<SkuInfo> page = skuInfoService.listByPageAndFilter(pageParam, skuInfoQueryVo);
        return Result.successWithData(page);
    }
    
    @PostMapping
    public Result saveSkuInfo(@RequestBody SkuInfoVo skuInfoVo) {
        skuInfoService.saveDetailSkuInfo(skuInfoVo);
        return null;
    }
}

