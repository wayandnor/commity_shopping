package com.nor.cs.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nor.cs.model.product.Category;
import com.nor.cs.model.vo.product.CategoryQueryVo;
import com.nor.cs.product.mapper.CategoryMapper;
import com.nor.cs.product.service.api.CategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商品三级分类 服务实现类
 * </p>
 *
 * @author north
 * @since 2023-06-28
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Override
    public IPage<Category> pageByKeyword(Page<Category> pageParam, CategoryQueryVo categoryQueryVo) {
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        if (categoryQueryVo.getKeyword() != null) {
            wrapper.like(Category::getName, categoryQueryVo.getKeyword());
        }
        IPage<Category> categoryPage = baseMapper.selectPage(pageParam, wrapper);
        return categoryPage;
    }
}
