package com.nor.cs.service.impl;

import com.nor.cs.client.product.ProductFeignClient;
import com.nor.cs.client.search.SkuFeignClient;
import com.nor.cs.client.user.UserFeignClient;
import com.nor.cs.model.product.Category;
import com.nor.cs.model.product.SkuInfo;
import com.nor.cs.model.search.SkuEs;
import com.nor.cs.model.vo.user.LeaderAddressVo;
import com.nor.cs.service.api.HomeService;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeServiceImpl implements HomeService {
    @Resource
    private UserFeignClient userFeignClient;
    @Resource
    private ProductFeignClient productFeignClient;
    
    @Resource
    private SkuFeignClient skuFeignClient;
    @Override
    public Map<String, Object> indexPageData(Long userId) {
        LeaderAddressVo leaderAddressVo = userFeignClient.getUserAddressByUserId(userId);
        List<Category> categoryList = productFeignClient.queryAllCategory();
        List<SkuInfo> newExclusiveSkuInfoList = productFeignClient.queryNewExclusiveSkuInfoList();
        List<SkuEs> skuEs = skuFeignClient.queryHotSkuList();
        Map<String, Object> result = new HashMap<>();
        result.put("leaderAddressVo", leaderAddressVo);
        result.put("categoryList",categoryList);
        result.put("newPersonSkuInfoList",newExclusiveSkuInfoList);
        result.put("hotSkuList", skuEs);
        return result;
    }
}
