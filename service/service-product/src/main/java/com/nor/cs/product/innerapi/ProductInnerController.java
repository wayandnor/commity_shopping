package com.nor.cs.product.innerapi;


import com.nor.cs.model.product.Category;
import com.nor.cs.model.product.SkuInfo;
import com.nor.cs.product.service.api.CategoryService;
import com.nor.cs.product.service.api.SkuInfoService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

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
    
    @PostMapping("skuInfoList")
    public List<SkuInfo> querySkuInfoList(@RequestBody List<Long> skuIdList) {
        List<SkuInfo> skuInfos = skuInfoService.listByIds(skuIdList);
        return skuInfos;
    }
    
    @GetMapping("{keyword}")
    public List<SkuInfo> querySkuInfoByKeyword(@PathVariable String keyword) {
        List<SkuInfo> skuInfos = skuInfoService.querySkuInfoByKeyword(keyword);
        return skuInfos;
    }

    @PostMapping("categoryList")
    public List<Category> queryCategoryList(@RequestBody List<Long> skuIdList) {
        List<Category> categoryList = categoryService.listByIds(skuIdList);
        return categoryList;
    }
    
    @GetMapping(value = "category/all")
    public List<Category> queryAllCategory() {
        List<Category> categoryList = categoryService.list();
        return categoryList;
    }
    
    @GetMapping("new-exclusive/list")
    public List<SkuInfo> queryNewExclusiveSkuInfoList() {
        List<SkuInfo> skuInfos = skuInfoService.queryNewExclusiveSkuInfos();
        return skuInfos;
    }
}
