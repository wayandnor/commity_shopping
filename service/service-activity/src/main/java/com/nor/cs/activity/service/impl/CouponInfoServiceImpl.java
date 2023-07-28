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
import com.nor.cs.model.product.Category;
import com.nor.cs.model.product.SkuInfo;
import com.nor.cs.model.vo.activity.CouponRuleVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        
        Map<String,Object> result = new HashMap<>();
        if (!CollectionUtils.isEmpty(rangeIds)) {
            switch (rangeType) {
                case SKU:
                    List<SkuInfo> skuInfos = productFeignClient.querySkuInfoList(rangeIds);
                    result.put("skuInfoList",skuInfos);
                case CATEGORY:
                    List<Category> categoryList = productFeignClient.queryCategoryList(rangeIds);
                    result.put("categoryList",categoryList);
            }
        }
        return result;
    }

    @Override
    @Transactional
    public void saveRules(CouponRuleVo couponRuleVo) {
        Long couponId = couponRuleVo.getCouponId();
        couponRangeMapper.delete(
                new LambdaQueryWrapper<CouponRange>().eq(CouponRange::getCouponId,couponId)
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
                new LambdaQueryWrapper<CouponRange>().eq(CouponRange::getCouponId,id)
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
}
