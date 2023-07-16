package com.nor.cs.product.service.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nor.cs.model.product.SkuInfo;
import com.nor.cs.model.vo.product.SkuInfoQueryVo;
import com.nor.cs.model.vo.product.SkuInfoVo;

import java.util.List;

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

    SkuInfoVo getSkuInfoVoById(Long id);

    void updateSkuInfo(SkuInfoVo skuInfoVo);

    boolean updateSkuPublishStatus(Long id, Integer status);

    void updateCheckSkuStatus(Long id, Integer status);

    void updateSkuNewExclusive(Long id, Integer status);

    List<SkuInfo> querySkuInfoByKeyword(String keyword);
}
