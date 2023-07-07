package com.nor.cs.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.nor.cs.model.product.SkuPoster;
import com.nor.cs.product.mapper.SkuPosterMapper;
import com.nor.cs.product.service.SkuPosterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 商品海报表 服务实现类
 * </p>
 *
 * @author north
 * @since 2023-06-28
 */
@Service
public class SkuPosterServiceImpl extends ServiceImpl<SkuPosterMapper, SkuPoster> implements SkuPosterService {

    @Override
    public List<SkuPoster> getPosterListById(Long id) {
        LambdaQueryWrapper<SkuPoster> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SkuPoster::getSkuId,id);
        return baseMapper.selectList(wrapper);
    }
}
