package com.nor.cs.service.impl;

import com.nor.cs.client.product.ProductFeignClient;
import com.nor.cs.client.user.UserFeignClient;
import com.nor.cs.model.product.Category;
import com.nor.cs.model.vo.user.LeaderAddressVo;
import com.nor.cs.service.api.HomeService;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

public class HomeServiceImpl implements HomeService {
    @Resource
    private UserFeignClient userFeignClient;
    @Resource
    private ProductFeignClient productFeignClient;
    @Override
    public Map<String, Object> indexPageData(Long userId) {
        LeaderAddressVo leaderAddressVo = userFeignClient.getUserAddressByUserId(userId);
        List<Category> categoryList = productFeignClient.queryAllCategory();
        return null;
    }
}
