package com.nor.cs.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.nor.cs.model.sys.Region;
import com.nor.cs.sys.mapper.RegionMapper;
import com.nor.cs.sys.service.RegionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 地区表 服务实现类
 * </p>
 *
 * @author north
 * @since 2023-06-26
 */
@Service
public class RegionServiceImpl extends ServiceImpl<RegionMapper, Region> implements RegionService {

    @Override
    public List<Region> getByKeyword(String keyword) {
        LambdaQueryWrapper<Region> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(Region::getName,keyword);
        List<Region> regionList = baseMapper.selectList(wrapper);
        return regionList;
    }
}
