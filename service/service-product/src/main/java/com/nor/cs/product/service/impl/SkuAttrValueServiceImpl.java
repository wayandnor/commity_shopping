package com.nor.cs.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.nor.cs.model.product.SkuAttrValue;
import com.nor.cs.product.mapper.SkuAttrValueMapper;
import com.nor.cs.product.service.api.SkuAttrValueService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * spu属性值 服务实现类
 * </p>
 *
 * @author north
 * @since 2023-06-28
 */
@Service
public class SkuAttrValueServiceImpl extends ServiceImpl<SkuAttrValueMapper, SkuAttrValue> implements SkuAttrValueService {

    @Override
    public List<SkuAttrValue> getAttrValueById(Long id) {
        LambdaQueryWrapper<SkuAttrValue> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SkuAttrValue::getSkuId,id);
        return baseMapper.selectList(wrapper);
    }
}
