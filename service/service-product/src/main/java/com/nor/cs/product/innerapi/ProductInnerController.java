package com.nor.cs.product.innerapi;


import com.nor.cs.model.product.Category;
import com.nor.cs.model.product.SkuInfo;
import com.nor.cs.product.service.api.CategoryService;
import com.nor.cs.product.service.api.SkuInfoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/product/inner")
public class ProductInnerController {
    @Resource
    private CategoryService categoryService;
    
    @Resource
    private SkuInfoService skuInfoService;
    @GetMapping("category/{categoryId}")
    public Category getCategoryByCategoryId(@PathVariable Long categoryId) {
        Category category = categoryService.getById(categoryId);
        return category;
    }
    
    @GetMapping("sku/{skuId}")
    public SkuInfo getSkuInfoBySkuId(@PathVariable Long skuId) {
        SkuInfo skuinfo = skuInfoService.getById(skuId);
        return skuinfo;
    }

}
