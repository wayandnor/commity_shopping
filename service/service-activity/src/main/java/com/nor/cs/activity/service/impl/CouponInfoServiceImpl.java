package com.nor.cs.activity.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nor.cs.activity.mapper.CouponInfoMapper;
import com.nor.cs.activity.mapper.CouponRangeMapper;
import com.nor.cs.activity.service.api.CouponInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nor.cs.client.product.ProductFeignClient;
import com.nor.cs.enums.CouponRangeType;
import com.nor.cs.model.activity.CouponInfo;
import com.nor.cs.model.activity.CouponRange;
import com.nor.cs.model.order.CartInfo;
import com.nor.cs.model.product.Category;
import com.nor.cs.model.product.SkuInfo;
import com.nor.cs.model.vo.activity.CouponRuleVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 优惠券信息 服务实现类
 * </p>
 *
 * @author north
 * @since 2023-07-13
 */
@Service
public class CouponInfoServiceImpl extends ServiceImpl<CouponInfoMapper, CouponInfo> implements CouponInfoService {
    @Resource
    private CouponRangeMapper couponRangeMapper;

    @Resource
    private ProductFeignClient productFeignClient;

    @Override
    public IPage<CouponInfo> queryCouponInfoByPage(Page<CouponInfo> pageParam) {
        IPage<CouponInfo> couponInfoPage = baseMapper.selectPage(pageParam, null);
        List<CouponInfo> records = couponInfoPage.getRecords();
        records.forEach(couponInfo -> {
            couponInfo.setCouponTypeString(couponInfo.getCouponType().getComment());
            CouponRangeType rangeType = couponInfo.getRangeType();
            if (rangeType != null) {
                couponInfo.setCouponTypeString(rangeType.getComment());
            }
        });
        return couponInfoPage;
    }

    @Override
    public CouponInfo queryCouponInfoById(Long id) {
        CouponInfo couponInfo = baseMapper.selectById(id);
        if (couponInfo != null) {
            couponInfo.setCouponTypeString(couponInfo.getCouponType().getComment());
            CouponRangeType couponRangeType = couponInfo.getRangeType();
            if (couponRangeType != null) {
                couponInfo.setRangeTypeString(couponRangeType.getComment());
            }
        }
        return couponInfo;
    }

    @Override
    public Map<String, Object> queryCouponRules(Long id) {
        CouponInfo couponInfo = baseMapper.selectById(id);
        List<CouponRange> couponRanges = couponRangeMapper.selectList(
                new LambdaQueryWrapper<CouponRange>().eq(CouponRange::getCouponId, id)
        );
        List<Long> rangeIds = couponRanges.stream().map(CouponRange::getRangeId).collect(Collectors.toList());
        CouponRangeType rangeType = couponInfo.getRangeType();

        Map<String, Object> result = new HashMap<>();
        if (!CollectionUtils.isEmpty(rangeIds)) {
            switch (rangeType) {
                case SKU:
                    List<SkuInfo> skuInfos = productFeignClient.querySkuInfoList(rangeIds);
                    result.put("skuInfoList", skuInfos);
                case CATEGORY:
                    List<Category> categoryList = productFeignClient.queryCategoryList(rangeIds);
                    result.put("categoryList", categoryList);
            }
        }
        return result;
    }

    @Override
    @Transactional
    public void saveRules(CouponRuleVo couponRuleVo) {
        Long couponId = couponRuleVo.getCouponId();
        couponRangeMapper.delete(
                new LambdaQueryWrapper<CouponRange>().eq(CouponRange::getCouponId, couponId)
        );

        CouponInfo couponInfo = baseMapper.selectById(couponId);
        couponInfo.setRangeType(couponRuleVo.getRangeType());
        couponInfo.setConditionAmount(couponRuleVo.getConditionAmount());
        couponInfo.setAmount(couponInfo.getAmount());
        couponInfo.setRangeDesc(couponRuleVo.getRangeDesc());
        baseMapper.updateById(couponInfo);

        List<CouponRange> couponRangeList = couponRuleVo.getCouponRangeList();
        couponRangeList.forEach(couponRange -> {
            couponRange.setCouponId(couponId);
            couponRangeMapper.insert(couponRange);
        });
    }

    @Override
    @Transactional
    public void deleteCouponById(Long id) {
        couponRangeMapper.delete(
                new LambdaQueryWrapper<CouponRange>().eq(CouponRange::getCouponId, id)
        );
        baseMapper.deleteById(id);
    }

    @Override
    public void batchRemoveByIds(List<Long> ids) {
        couponRangeMapper.delete(
                new QueryWrapper<CouponRange>().in("couponId", ids)
        );
        baseMapper.deleteBatchIds(ids);
    }

    @Override
    public List<CouponInfo> queryCouponByKeyword(String keyword) {
        List<CouponInfo> couponInfos = baseMapper.selectList(
                new LambdaQueryWrapper<CouponInfo>().like(CouponInfo::getCouponName, keyword)
        );
        if (couponInfos.size() == 0) {
            return couponInfos;
        }
        couponInfos.forEach(couponInfo -> {
            couponInfo.setCouponTypeString(couponInfo.getCouponType().getComment());
            CouponRangeType couponRangeType = couponInfo.getRangeType();
            if (couponRangeType != null) {
                couponInfo.setRangeTypeString(couponRangeType.getComment());
            }
        });
        return couponInfos;
    }

    @Override
    public List<CouponInfo> getCouponInfoList(Long skuId, Long userId) {
        SkuInfo skuInfo = productFeignClient.getSkuInfoBySkuId(skuId);
        List<CouponInfo> couponInfos = baseMapper.selectCouponInfoList(skuId, skuInfo.getCategoryId(), userId);
        return couponInfos;
    }

    private Map<Long, List<Long>> findCouponIdToSkuIdMap(List<CartInfo> cartInfoList,
                                                         List<CouponRange> couponRangeList) {
        Map<Long, List<Long>> couponIdToSkuIdMap = new HashMap<>();

        //couponRangeList数据处理，根据优惠卷id分组
        Map<Long, List<CouponRange>> couponRangeToRangeListMap = couponRangeList.stream()
                .collect(
                        Collectors.groupingBy(couponRange -> couponRange.getCouponId())
                );

        //遍历map集合
        Iterator<Map.Entry<Long, List<CouponRange>>> iterator =
                couponRangeToRangeListMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Long, List<CouponRange>> entry = iterator.next();
            Long couponId = entry.getKey();
            List<CouponRange> rangeList = entry.getValue();

            //创建集合 set
            Set<Long> skuIdSet = new HashSet<>();
            for (CartInfo cartInfo : cartInfoList) {
                for (CouponRange couponRange : rangeList) {
                    //判断
                    if (couponRange.getRangeType() == CouponRangeType.SKU
                            && couponRange.getRangeId().longValue() == cartInfo.getSkuId().longValue()) {
                        skuIdSet.add(cartInfo.getSkuId());
                    } else if (couponRange.getRangeType() == CouponRangeType.CATEGORY
                            && couponRange.getRangeId().longValue() == cartInfo.getCategoryId().longValue()) {
                        skuIdSet.add(cartInfo.getSkuId());
                    } else {

                    }
                }
            }
            couponIdToSkuIdMap.put(couponId, new ArrayList<>(skuIdSet));
        }
        return couponIdToSkuIdMap;
    }

    @Override
    public List<CouponInfo> findCartCouponInfo(List<CartInfo> cartInfoList, Long userId) {
        List<CouponInfo> userAllCouponInfoList =
                baseMapper.selectCartCouponInfoList(userId);
        if (CollectionUtils.isEmpty(userAllCouponInfoList)) {
            return new ArrayList<CouponInfo>();
        }

        //2 从第一步返回list集合中，获取所有优惠卷id列表
        List<Long> couponIdList = userAllCouponInfoList.stream().map(couponInfo -> couponInfo.getId())
                .collect(Collectors.toList());

        //3 查询优惠卷对应的范围  coupon_range
        //couponRangeList
        LambdaQueryWrapper<CouponRange> wrapper = new LambdaQueryWrapper<>();
        // id in (1,2,3)
        wrapper.in(CouponRange::getCouponId, couponIdList);
        List<CouponRange> couponRangeList = couponRangeMapper.selectList(wrapper);

        //4 获取优惠卷id 对应skuId列表
        //优惠卷id进行分组，得到map集合
        //     Map<Long,List<Long>>
        Map<Long, List<Long>> couponIdToSkuIdMap =
                this.findCouponIdToSkuIdMap(cartInfoList, couponRangeList);

        //5 遍历全部优惠卷集合，判断优惠卷类型
        //全场通用  sku和分类
        BigDecimal reduceAmount = new BigDecimal(0);
        CouponInfo optimalCouponInfo = null;
        for (CouponInfo couponInfo : userAllCouponInfoList) {
            //全场通用
            if (CouponRangeType.ALL == couponInfo.getRangeType()) {
                //全场通用
                //判断是否满足优惠使用门槛
                //计算购物车商品的总价
                BigDecimal totalAmount = computeTotalAmount(cartInfoList);
                if (totalAmount.subtract(couponInfo.getConditionAmount()).doubleValue() >= 0) {
                    couponInfo.setIsSelect(1);
                }
            } else {
                //优惠卷id获取对应skuId列表
                List<Long> skuIdList
                        = couponIdToSkuIdMap.get(couponInfo.getId());
                //满足使用范围购物项
                List<CartInfo> currentCartInfoList = cartInfoList.stream()
                        .filter(cartInfo -> skuIdList.contains(cartInfo.getSkuId()))
                        .collect(Collectors.toList());
                BigDecimal totalAmount = computeTotalAmount(currentCartInfoList);
                if (totalAmount.subtract(couponInfo.getConditionAmount()).doubleValue() >= 0) {
                    couponInfo.setIsSelect(1);
                }
            }
            if (couponInfo.getIsSelect().intValue() == 1 && couponInfo.getAmount().subtract(reduceAmount).doubleValue() > 0) {
                reduceAmount = couponInfo.getAmount();
                optimalCouponInfo = couponInfo;
            }

        }
        //6 返回List<CouponInfo>
        if (null != optimalCouponInfo) {
            optimalCouponInfo.setIsOptimal(1);
        }
        return userAllCouponInfoList;
    }

    private BigDecimal computeTotalAmount(List<CartInfo> cartInfoList) {
        BigDecimal total = new BigDecimal("0");
        for (CartInfo cartInfo : cartInfoList) {
            //是否选中
            if (cartInfo.getIsChecked().intValue() == 1) {
                BigDecimal itemTotal = cartInfo.getCartPrice().multiply(new BigDecimal(cartInfo.getSkuNum()));
                total = total.add(itemTotal);
            }
        }
        return total;
    }
}
