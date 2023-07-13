package com.nor.cs.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.nor.cs.model.product.SkuImage;
import com.nor.cs.product.mapper.SkuImageMapper;
import com.nor.cs.product.service.api.SkuImageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 商品图片 服务实现类
 * </p>
 *
 * @author north
 * @since 2023-06-28
 */
@Service
public class SkuImageServiceImpl extends ServiceImpl<SkuImageMapper, SkuImage> implements SkuImageService {

    @Override
    public List<SkuImage> getImageListById(Long id) {
        LambdaQueryWrapper<SkuImage> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SkuImage::getSkuId,id);
        return baseMapper.selectList(wrapper);
    }
}
