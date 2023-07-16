package com.nor.cs.product.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nor.cs.common.result.Result;
import com.nor.cs.model.product.Category;
import com.nor.cs.model.vo.product.CategoryQueryVo;
import com.nor.cs.product.service.api.CategoryService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 商品三级分类 前端控制器
 * </p>
 *
 * @author north
 * @since 2023-06-28
 */
@RestController
@RequestMapping("/admin/product/category")
//@CrossOrigin
public class CategoryController {
    @Resource
    private CategoryService categoryService;
    @GetMapping("{page_num}/{page_size}")
    public Result<IPage<Category>> getCategoryPage(@PathVariable Integer page_num,
                                                   @PathVariable Integer page_size,
                                                   CategoryQueryVo categoryQueryVo) {
        Page<Category> pageParam = new Page<>(page_num,page_size);
        IPage<Category> page = categoryService.pageByKeyword(pageParam,categoryQueryVo);
        return Result.successWithData(page);
    }
    
    @GetMapping("{id}") 
    public Result<Category> getCategoryById(@PathVariable Long id) {
        Category category = categoryService.getById(id);
        return Result.successWithData(category);
    }
    
    @PostMapping
    public Result saveCategory(@RequestBody Category category) {
        categoryService.save(category);
        return Result.successWithOutData();
    }
    
    @PutMapping
    public Result updateCategoryById(@RequestBody Category category){
        categoryService.updateById(category);
        return Result.successWithOutData();
    }
    
    @DeleteMapping("{id}")
    public Result deleteCategoryById(@PathVariable Long id) {
        categoryService.removeById(id);
        return Result.successWithOutData();
    }
    
    @DeleteMapping("batchRemove")
    public Result batchDeleteCategory(@RequestBody List<Long> ids) {
        categoryService.removeByIds(ids);
        return Result.successWithOutData();
    }
    @GetMapping("all")
    public Result<List<Category>> listAllCategory() {
        List<Category> categoryList = categoryService.list();
        return Result.successWithData(categoryList);
    }
    
    

}

