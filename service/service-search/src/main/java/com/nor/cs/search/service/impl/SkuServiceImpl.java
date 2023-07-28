package com.nor.cs.search.service.impl;

import com.nor.cs.activity.client.ActivityFeignClient;
import com.nor.cs.client.product.ProductFeignClient;
import com.nor.cs.common.auth.AuthContextHolder;
import com.nor.cs.enums.SkuType;
import com.nor.cs.model.product.Category;
import com.nor.cs.model.product.SkuInfo;
import com.nor.cs.model.search.SkuEs;
import com.nor.cs.model.vo.search.SkuEsQueryVo;
import com.nor.cs.search.repository.SkuInfoRepository;
import com.nor.cs.search.service.api.SkuService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @version 1.0
 * @author: Nor Way
 * @description:
 * @date 2023/7/8 17:35
 */
@Service
public class SkuServiceImpl implements SkuService {
    @Resource
    private SkuInfoRepository skuInfoRepository;

    @Resource
    private ProductFeignClient productFeignClient;
    
    @Resource
    private ActivityFeignClient activityFeignClient;
    
    @Resource
    private RedisTemplate redisTemplate;

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

    @Override
    public List<SkuEs> getHotSku() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<SkuEs> pageModel = skuInfoRepository.findByOrderByHotScoreDesc(pageable);
        List<SkuEs> skuEsList = pageModel.getContent();
        return skuEsList;
    }

    @Override
    public Page<SkuEs> search(Pageable pageable, SkuEsQueryVo skuEsQueryVo) {
        Long wareId = AuthContextHolder.getWareId();
        skuEsQueryVo.setWareId(wareId);
        Page<SkuEs> pageModel = null;
        String keyword = skuEsQueryVo.getKeyword();
        if (StringUtils.isEmpty(keyword)) {
            pageModel = skuInfoRepository.findByCategoryIdAndWareId(
                    skuEsQueryVo.getCategoryId(),
                    skuEsQueryVo.getWareId(),
                    pageable);
        } else {
            pageModel = skuInfoRepository.findByKeywordAndWareId(
                    skuEsQueryVo.getKeyword(),
                    skuEsQueryVo.getWareId(),
                    pageable);
        }
        List<SkuEs> skuEsList = pageModel.getContent();
        if (!CollectionUtils.isEmpty(skuEsList)) {
            List<Long> skuIdList = skuEsList.stream().map(SkuEs::getId).collect(Collectors.toList());
            Map<Long, List<String>> skuIdRuleListMap = activityFeignClient.getActivityList(skuIdList);
            if (skuIdRuleListMap != null) {
                skuEsList.forEach(skuEs -> {
                    skuEs.setRuleList(skuIdRuleListMap.get(skuEs.getId()));
                });
            }
        }
        return pageModel;
    }

    @Override
    public void incrHotScore(Long skuId) {
        String key = "hotScore";
        Double hotScore = redisTemplate.opsForZSet().incrementScore(key, "skuId:" + skuId, 1);
        //规则
        if(hotScore != null && hotScore%10==0) {
            //更新es
            Optional<SkuEs> optional = skuInfoRepository.findById(skuId);
            SkuEs skuEs = optional.get();
            skuEs.setHotScore(Math.round(hotScore));
            skuInfoRepository.save(skuEs);
        }
    }
}
