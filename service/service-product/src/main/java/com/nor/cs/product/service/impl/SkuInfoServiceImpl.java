package com.nor.cs.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nor.cs.model.product.SkuAttrValue;
import com.nor.cs.model.product.SkuImage;
import com.nor.cs.model.product.SkuInfo;
import com.nor.cs.model.product.SkuPoster;
import com.nor.cs.model.vo.product.SkuInfoQueryVo;
import com.nor.cs.model.vo.product.SkuInfoVo;
import com.nor.cs.product.mapper.SkuInfoMapper;
import com.nor.cs.product.service.SkuAttrValueService;
import com.nor.cs.product.service.SkuImageService;
import com.nor.cs.product.service.SkuInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nor.cs.product.service.SkuPosterService;
import jodd.util.CollectionUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 * sku信息 服务实现类
 * </p>
 *
 * @author north
 * @since 2023-06-28
 */
@Service
public class SkuInfoServiceImpl extends ServiceImpl<SkuInfoMapper, SkuInfo> implements SkuInfoService {
    @Resource
    private SkuImageService skuImageService;
    @Resource
    private SkuPosterService skuPosterService;
    @Resource
    private SkuAttrValueService skuAttrValueService;

    @Override
    public IPage<SkuInfo> listByPageAndFilter(Page<SkuInfo> pageParam, SkuInfoQueryVo skuInfoQueryVo) {
        Long categoryId = skuInfoQueryVo.getCategoryId();
        String skuType = skuInfoQueryVo.getSkuType();
        String spuName = skuInfoQueryVo.getKeyword();
        LambdaQueryWrapper<SkuInfo> wrapper = new LambdaQueryWrapper<>();
        if (categoryId != null) {
            wrapper.eq(SkuInfo::getCategoryId, categoryId);
        }
        if (skuType != null) {
            wrapper.eq(SkuInfo::getSkuType, skuType);
        }
        if (spuName != null) {
            wrapper.like(SkuInfo::getSkuName, spuName);
        }
        Page<SkuInfo> skuInfoPage = baseMapper.selectPage(pageParam, wrapper);
        return skuInfoPage;
    }

    @Override
    @Transactional
    public void saveDetailSkuInfo(SkuInfoVo skuInfoVo) {
        SkuInfo skuInfo = new SkuInfo();
        BeanUtils.copyProperties(skuInfoVo, skuInfo);
        baseMapper.insert(skuInfo);

        List<SkuPoster> skuPosterList = skuInfoVo.getSkuPosterList();
        if (!CollectionUtils.isEmpty(skuPosterList)) {
            for (SkuPoster skuPoster : skuPosterList) {
                skuPoster.setSkuId(skuInfo.getId());
            }
            skuPosterService.saveBatch(skuPosterList);
        }

        List<SkuImage> skuImagesList = skuInfoVo.getSkuImagesList();
        if (!CollectionUtils.isEmpty(skuImagesList)) {
            for (SkuImage skuImage : skuImagesList) {
                skuImage.setSkuId(skuInfo.getId());
            }
            skuImageService.saveBatch(skuImagesList);
        }

        List<SkuAttrValue> skuAttrValueList = skuInfoVo.getSkuAttrValueList();
        if (!CollectionUtils.isEmpty(skuAttrValueList)) {
            for (SkuAttrValue skuAttrValue : skuAttrValueList) {
                skuAttrValue.setSkuId(skuInfo.getId());
            }
            skuAttrValueService.saveBatch(skuAttrValueList);
        }

    }
}
