package com.nor.cs.sys.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nor.cs.model.sys.RegionWare;
import com.nor.cs.model.vo.sys.RegionWareQueryVo;
import io.swagger.models.auth.In;

/**
 * <p>
 * 城市仓库关联表 服务类
 * </p>
 *
 * @author north
 * @since 2023-06-26
 */
public interface RegionWareService extends IService<RegionWare> {

    IPage<RegionWare> pageByFilter(IPage<RegionWare> pageParam, RegionWareQueryVo regionWareQueryVo);

    void saveRegionWare(RegionWare regionWare);

    void updateStatusById(Long id, Integer status);
}
