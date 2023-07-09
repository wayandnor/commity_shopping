package com.nor.cs.client.product;


import com.nor.cs.model.product.Category;
import com.nor.cs.model.product.SkuInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(value = "service-product",path = "/api/product/inner")
public interface ProductFeignClient {
    @GetMapping("category/{categoryId}")
    Category getCategoryByCategoryId(@PathVariable Long categoryId);

    @GetMapping("sku/{skuId}")
    SkuInfo getSkuInfoBySkuId(@PathVariable Long skuId);
}
