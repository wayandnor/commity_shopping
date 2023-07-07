package com.nor.cs.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nor.cs.model.product.SkuImage;

import java.util.List;

/**
 * <p>
 * 商品图片 服务类
 * </p>
 *
 * @author north
 * @since 2023-06-28
 */
public interface SkuImageService extends IService<SkuImage> {

    List<SkuImage> getImageListById(Long id);
}
