package com.nor.cs.search.controller;

import com.nor.cs.common.result.Result;
import com.nor.cs.model.product.SkuInfo;
import com.nor.cs.model.search.SkuEs;
import com.nor.cs.model.vo.search.SkuEsQueryVo;
import com.nor.cs.search.service.api.SkuService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

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
    public Result launchSku(@PathVariable Long skuId) {
        skuService.addSkuInfo(skuId);
        return Result.successWithOutData();
    }

    @PutMapping("inner/pull/{skuId}")
    public Result pullSku(@PathVariable Long skuId) {
        skuService.removeSkuInfo(skuId);
        return Result.successWithOutData();
    }

    @GetMapping("inner/hot-sku-list")
    public List<SkuEs> queryHotSkuList() {
        return skuService.getHotSku();
    }

    @GetMapping("{pageNum}/{pageSize}")
    public Result<List<SkuEs>> querySkuInfo(@PathVariable Integer pageNum,
                                              @PathVariable Integer pageSize,
                                              SkuEsQueryVo skuEsQueryVo) {
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        Page<SkuEs> pageModel = skuService.search(pageable,skuEsQueryVo);
        return Result.successWithData(pageModel.getContent());
    }
}
