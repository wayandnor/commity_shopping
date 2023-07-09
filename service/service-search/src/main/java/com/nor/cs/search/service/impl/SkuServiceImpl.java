package com.nor.cs.search.service.impl;

import com.nor.cs.client.product.ProductFeignClient;
import com.nor.cs.enums.SkuType;
import com.nor.cs.model.product.Category;
import com.nor.cs.model.product.SkuInfo;
import com.nor.cs.model.search.SkuEs;
import com.nor.cs.search.repository.SkuInfoRepository;
import com.nor.cs.search.service.api.SkuService;

import javax.annotation.Resource;

/**
 * @version 1.0
 * @author: Nor Way
 * @description:
 * @date 2023/7/8 17:35
 */
public class SkuServiceImpl implements SkuService {
    @Resource
    private SkuInfoRepository skuInfoRepository;
    
    @Resource
    private ProductFeignClient productFeignClient;
    @Override
    public void addSkuInfo(Long skuId) {
        SkuInfo skuInfo = productFeignClient.getSkuInfoBySkuId(skuId);
        if (skuInfo == null) {
            return;
        }
        Long categoryId = skuInfo.getCategoryId();
        Category category = productFeignClient.getCategoryByCategoryId(categoryId);
        SkuEs skuEs = new SkuEs();
        if (category != null) {
            skuEs.setCategoryId(categoryId);
            skuEs.setCategoryName(category.getName());
        }
        
        skuEs.setId(skuId);
        skuEs.setKeyword(skuInfo.getSkuName() + "," + skuEs.getCategoryName());
        skuEs.setWareId(skuInfo.getWareId());
        skuEs.setIsNewPerson(skuInfo.getIsNewPerson());
        skuEs.setImgUrl(skuInfo.getImgUrl());
        skuEs.setTitle(skuInfo.getSkuName());
        if (skuInfo.getSkuType() == SkuType.COMMON.getCode()) {
            skuEs.setSkuType(SkuType.COMMON.getCode());
            skuEs.setPrice(skuInfo.getPrice().doubleValue());
            skuEs.setStock(skuInfo.getStock());
            skuEs.setSale(skuInfo.getSale());
            skuEs.setPerLimit(skuInfo.getPerLimit());
        }
        skuInfoRepository.save(skuEs);
    }

    @Override
    public void removeSkuInfo(Long skuId) {
        skuInfoRepository.deleteById(skuId);
    }
}
