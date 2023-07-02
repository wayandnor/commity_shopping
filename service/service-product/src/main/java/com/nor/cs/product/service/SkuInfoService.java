package com.nor.cs.product.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nor.cs.model.product.SkuInfo;
import com.nor.cs.model.vo.product.SkuInfoQueryVo;
import com.nor.cs.model.vo.product.SkuInfoVo;

/**
 * <p>
 * sku信息 服务类
 * </p>
 *
 * @author north
 * @since 2023-06-28
 */
public interface SkuInfoService extends IService<SkuInfo> {

    IPage<SkuInfo> listByPageAndFilter(Page<SkuInfo> pageParam, SkuInfoQueryVo skuInfoQueryVo);

    void saveDetailSkuInfo(SkuInfoVo skuInfoVo);
}
