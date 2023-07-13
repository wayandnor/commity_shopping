package com.nor.cs.product.service.api;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nor.cs.model.product.SkuPoster;

import java.util.List;

/**
 * <p>
 * 商品海报表 服务类
 * </p>
 *
 * @author north
 * @since 2023-06-28
 */
public interface SkuPosterService extends IService<SkuPoster> {

    List<SkuPoster> getPosterListById(Long id);
}
