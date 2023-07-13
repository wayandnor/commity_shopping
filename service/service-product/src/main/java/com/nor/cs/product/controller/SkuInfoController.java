package com.nor.cs.product.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nor.cs.common.result.Result;
import com.nor.cs.common.result.ResultCodeEnum;
import com.nor.cs.model.product.SkuInfo;
import com.nor.cs.model.vo.product.SkuInfoQueryVo;
import com.nor.cs.model.vo.product.SkuInfoVo;
import com.nor.cs.product.service.api.SkuInfoService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

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
@CrossOrigin
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
        return Result.successWithOutData();
    }
    
    @GetMapping("{id}")
    public Result<SkuInfoVo> getSkuInfoById(@PathVariable Long id) {
        SkuInfoVo skuInfoVo = skuInfoService.getSkuInfoVoById(id);
        return Result.successWithData(skuInfoVo);
    }
    
    @PutMapping
    public Result updateSkuInfo(@RequestBody SkuInfoVo skuInfoVo) {
        skuInfoService.updateSkuInfo(skuInfoVo);
        return Result.successWithOutData();
    }
    
    @DeleteMapping("{id}")
    public Result removeById(@PathVariable Long id) {
        skuInfoService.removeById(id);
        return Result.successWithOutData();
    }
    
    @DeleteMapping("batchRemove")
    public Result batchRemove(@RequestBody List<Long> ids) {
        skuInfoService.removeByIds(ids);
        return Result.successWithOutData();
    }
    
    @PutMapping("publish/{id}/{status}")
    public Result updateSkuPublishStatus(@PathVariable Long id,
                                  @PathVariable Integer status) {
        boolean updateResult = skuInfoService.updateSkuPublishStatus(id,status);
        return updateResult? Result.successWithOutData(): Result.build(null, ResultCodeEnum.UPDATE_PRODUCT_PUBLISH_STATUS_FAIL);
    }

    @PutMapping("check/{id}/{status}")
    public Result updateSkuCheckStatus(@PathVariable Long id,
                                  @PathVariable Integer status) {
        skuInfoService.updateCheckSkuStatus(id,status);
        return Result.successWithOutData();
    }

    @PutMapping("isNewPerson/{id}/{status}")
    public Result updateNewMemberExclusive(@PathVariable Long id,
                                           @PathVariable Integer status) {
        skuInfoService.updateSkuNewExclusive(id,status);
        return Result.successWithOutData();
    }
}

