package com.nor.cs.client.product;


import com.nor.cs.model.product.Category;
import com.nor.cs.model.product.SkuInfo;
import com.nor.cs.model.vo.product.SkuInfoVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(value = "service-product", path = "/api/product/inner")
public interface ProductFeignClient {
    @GetMapping("category/{categoryId}")
    Category getCategoryByCategoryId(@PathVariable Long categoryId);

    @GetMapping("sku/{skuId}")
    SkuInfo getSkuInfoBySkuId(@PathVariable Long skuId);

    @PostMapping("skuInfoList")
    List<SkuInfo> querySkuInfoList(@RequestBody List<Long> skuIdList);

    @GetMapping("{keyword}")
    List<SkuInfo> querySkuInfoByKeyword(@PathVariable String keyword);

    @PostMapping("categoryList")
    List<Category> queryCategoryList(@RequestBody List<Long> skuIdList);

    @GetMapping("category/all")
    List<Category> queryAllCategory();

    @GetMapping("new-exclusive/list")
    List<SkuInfo> queryNewExclusiveSkuInfoList();

    @GetMapping("skuVo/{skuId}")
    SkuInfoVo getSkuInfoVoById(@PathVariable Long skuId);
}
