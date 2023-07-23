package com.nor.cs.controller;

import com.nor.cs.client.product.ProductFeignClient;
import com.nor.cs.common.result.Result;
import com.nor.cs.model.product.Category;
import com.nor.cs.model.product.SkuInfo;
import com.nor.cs.model.vo.search.SkuEsQueryVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @version 1.0
 * @author: Nor Way
 * @description:
 * @date 2023/7/22 13:51
 */
@RestController
@RequestMapping("api/home")
public class CategoryApiController {
    @Resource
    private ProductFeignClient productFeignClient;
    
    @GetMapping("category")
    public Result<List<Category>> queryCategoryList() {
        List<Category> categoryList = productFeignClient.queryAllCategory();
        return Result.successWithData(categoryList);
    }
    
}
