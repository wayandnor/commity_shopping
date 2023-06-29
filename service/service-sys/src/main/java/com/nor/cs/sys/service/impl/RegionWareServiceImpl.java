package com.nor.cs.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nor.cs.common.exception.CsException;
import com.nor.cs.common.result.ResultCodeEnum;
import com.nor.cs.model.sys.RegionWare;
import com.nor.cs.model.vo.sys.RegionWareQueryVo;
import com.nor.cs.sys.mapper.RegionWareMapper;
import com.nor.cs.sys.service.RegionWareService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 城市仓库关联表 服务实现类
 * </p>
 *
 * @author north
 * @since 2023-06-26
 */
@Service
public class RegionWareServiceImpl extends ServiceImpl<RegionWareMapper, RegionWare> implements RegionWareService {

    @Override
    public IPage<RegionWare> pageByFilter(IPage<RegionWare> pageParam, RegionWareQueryVo regionWareQueryVo) {
        LambdaQueryWrapper<RegionWare> wrapper = new LambdaQueryWrapper<>();
        String keyword = regionWareQueryVo.getKeyword();
        if (keyword != null) {
            wrapper.like(RegionWare::getRegionName,keyword).or().like(RegionWare::getWareName,keyword);
        }
        IPage<RegionWare> page = baseMapper.selectPage(pageParam, wrapper);
        return page;
    }

    @Override
    public void saveRegionWare(RegionWare regionWare) {
        LambdaQueryWrapper<RegionWare> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RegionWare::getRegionId,regionWare.getRegionId());
        Integer count = baseMapper.selectCount(wrapper);
        if ( count > 0) {
            throw new CsException(ResultCodeEnum.REGION_WARE_ALREADY_EXIST);
        }
        baseMapper.insert(regionWare);
    }

    @Override
    public void updateStatusById(Long id, Integer status) {
        RegionWare regionWare = baseMapper.selectById(id);
        regionWare.setStatus(status);
        baseMapper.updateById(regionWare);
    }
}
