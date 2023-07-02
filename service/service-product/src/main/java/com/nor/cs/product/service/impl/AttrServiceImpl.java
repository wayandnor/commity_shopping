package com.nor.cs.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.nor.cs.model.product.Attr;
import com.nor.cs.product.mapper.AttrMapper;
import com.nor.cs.product.service.AttrService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 商品属性 服务实现类
 * </p>
 *
 * @author north
 * @since 2023-06-28
 */
@Service
public class AttrServiceImpl extends ServiceImpl<AttrMapper, Attr> implements AttrService {

    @Override
    public List<Attr> listByGroupId(Long groupId) {
        LambdaQueryWrapper<Attr> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Attr::getAttrGroupId,groupId);
        return baseMapper.selectList(wrapper);
    }
}
