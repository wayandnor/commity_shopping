package com.nor.cs.service.impl;

import com.nor.cs.client.product.ProductFeignClient;
import com.nor.cs.common.constant.RedisConst;
import com.nor.cs.common.exception.CsException;
import com.nor.cs.common.result.ResultCodeEnum;
import com.nor.cs.enums.SkuType;
import com.nor.cs.model.base.BaseEntity;
import com.nor.cs.model.order.CartInfo;
import com.nor.cs.model.product.SkuInfo;
import com.nor.cs.service.api.CartInfoService;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Service
public class CartInfoServiceImpl implements CartInfoService {
    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private ProductFeignClient productFeignClient;

    @Override
    public void addToCart(Long userId, Long skuId, Integer skuCount) {
        String cartKey = this.getCartKey(userId);
        BoundHashOperations<String, String, CartInfo> operations = redisTemplate.boundHashOps(cartKey);
        CartInfo cartInfo = null;
        if (operations.hasKey(skuId.toString())) {
            cartInfo = operations.get(skuId.toString());
            Integer currentSkuNum = cartInfo.getSkuNum() + skuCount;
            if(currentSkuNum < 1) {
                return;
            }

            cartInfo.setSkuNum(currentSkuNum);
            cartInfo.setCurrentBuyNum(currentSkuNum);

            Integer perLimit = cartInfo.getPerLimit();
            if(currentSkuNum > perLimit) {
                throw new CsException(ResultCodeEnum.REGION_WARE_ALREADY_EXIST);
            }

            cartInfo.setIsChecked(1);
            cartInfo.setUpdateTime(new Date());
        }else {
            skuCount = 1;

            SkuInfo skuInfo = productFeignClient.getSkuInfoBySkuId(skuId);
            if(skuInfo == null) {
                throw new CsException(ResultCodeEnum.DATA_ERROR);
            }

            //封装cartInfo对象
            cartInfo = new CartInfo();
            cartInfo.setSkuId(skuId);
            cartInfo.setCategoryId(skuInfo.getCategoryId());
            cartInfo.setSkuType(skuInfo.getSkuType());
            cartInfo.setIsNewPerson(skuInfo.getIsNewPerson());
            cartInfo.setUserId(userId);
            cartInfo.setCartPrice(skuInfo.getPrice());
            cartInfo.setSkuNum(skuCount);
            cartInfo.setCurrentBuyNum(skuCount);
            cartInfo.setSkuType(SkuType.COMMON.getCode());
            cartInfo.setPerLimit(skuInfo.getPerLimit());
            cartInfo.setImgUrl(skuInfo.getImgUrl());
            cartInfo.setSkuName(skuInfo.getSkuName());
            cartInfo.setWareId(skuInfo.getWareId());
            cartInfo.setIsChecked(1);
            cartInfo.setStatus(1);
            cartInfo.setCreateTime(new Date());
            cartInfo.setUpdateTime(new Date());
        }
    }

    @Override
    public void deleteCart(Long skuId, Long userId) {
        BoundHashOperations<String,String,CartInfo> hashOperations =
                redisTemplate.boundHashOps(this.getCartKey(userId));
        if(hashOperations.hasKey(skuId.toString())) {
            hashOperations.delete(skuId.toString());
        }
    }

    @Override
    public void deleteAllCart(Long userId) {
        String cartKey = this.getCartKey(userId);
        BoundHashOperations<String,String,CartInfo> hashOperations =
                redisTemplate.boundHashOps(cartKey);
        List<CartInfo> cartInfoList = hashOperations.values();
        for (CartInfo cartInfo:cartInfoList) {
            hashOperations.delete(cartInfo.getSkuId().toString());
        }
    }

    @Override
    public void batchDeleteCart(List<Long> skuIdList, Long userId) {
        String cartKey = this.getCartKey(userId);
        BoundHashOperations<String,String,CartInfo> hashOperations =
                redisTemplate.boundHashOps(cartKey);
        skuIdList.forEach(skuId -> {
            hashOperations.delete(skuId.toString());
        });
    }

    @Override
    public List<CartInfo> getCartList(Long userId) {
        List<CartInfo> result = new ArrayList<>();
        if (StringUtils.isEmpty(userId.toString())){
            return result;
        }
        String cartKey = this.getCartKey(userId);
        BoundHashOperations<String, String, CartInfo> hashOperations = redisTemplate.boundHashOps(cartKey);
        result = hashOperations.values();
        if (!CollectionUtils.isEmpty(result)) {
            result.sort(Comparator.comparing(BaseEntity::getCreateTime));
        }
        return result;
    }

    private String getCartKey(Long userId) {
        return RedisConst.USER_KEY_PREFIX + userId + RedisConst.USER_CART_KEY_SUFFIX;
    }
}
