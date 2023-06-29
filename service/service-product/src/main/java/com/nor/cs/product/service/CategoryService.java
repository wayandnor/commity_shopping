package com.nor.cs.product.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nor.cs.model.product.Category;
import com.nor.cs.model.vo.product.CategoryQueryVo;

/**
 * <p>
 * 商品三级分类 服务类
 * </p>
 *
 * @author north
 * @since 2023-06-28
 */
public interface CategoryService extends IService<Category> {

    IPage<Category> pageByKeyword(Page<Category> pageParam, CategoryQueryVo categoryQueryVo);
}
