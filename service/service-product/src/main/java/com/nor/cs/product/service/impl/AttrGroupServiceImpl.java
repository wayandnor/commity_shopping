package com.nor.cs.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nor.cs.model.product.AttrGroup;
import com.nor.cs.model.vo.product.AttrGroupQueryVo;
import com.nor.cs.product.mapper.AttrGroupMapper;
import com.nor.cs.product.service.api.AttrGroupService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 属性分组 服务实现类
 * </p>
 *
 * @author north
 * @since 2023-06-28
 */
@Service
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupMapper, AttrGroup> implements AttrGroupService {

    @Override
    public IPage<AttrGroup> getPageByFilter(Page<AttrGroup> pageParam, AttrGroupQueryVo attrGroupQueryVo) {
        LambdaQueryWrapper<AttrGroup> wrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isEmpty(attrGroupQueryVo.getName())){
            wrapper.like(AttrGroup::getName,attrGroupQueryVo.getName());
        }
        Page<AttrGroup> page = baseMapper.selectPage(pageParam, wrapper);
        return page;
    }

    @Override
    public List<AttrGroup> listOrderById() {
        QueryWrapper<AttrGroup> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("id");
        List<AttrGroup> list = baseMapper.selectList(queryWrapper);
        return list;
    }
}
