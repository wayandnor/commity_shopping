package com.nor.cs.activity.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nor.cs.activity.mapper.ActivityInfoMapper;
import com.nor.cs.activity.mapper.ActivityRuleMapper;
import com.nor.cs.activity.mapper.ActivitySkuMapper;
import com.nor.cs.activity.service.api.ActivityInfoService;
import com.nor.cs.activity.service.api.CouponInfoService;
import com.nor.cs.client.product.ProductFeignClient;
import com.nor.cs.enums.ActivityType;
import com.nor.cs.model.activity.ActivityInfo;
import com.nor.cs.model.activity.ActivityRule;
import com.nor.cs.model.activity.ActivitySku;
import com.nor.cs.model.activity.CouponInfo;
import com.nor.cs.model.order.CartInfo;
import com.nor.cs.model.order.CartInfoVo;
import com.nor.cs.model.order.OrderConfirmVo;
import com.nor.cs.model.product.SkuInfo;
import com.nor.cs.model.vo.activity.ActivityRuleVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 活动表 服务实现类
 * </p>
 *
 * @author north
 * @since 2023-07-13
 */
@Service
public class ActivityInfoServiceImpl extends ServiceImpl<ActivityInfoMapper, ActivityInfo> implements ActivityInfoService {
    @Resource
    private ActivityRuleMapper activityRuleMapper;

    @Resource
    private ActivitySkuMapper activitySkuMapper;

    @Resource
    private ProductFeignClient productFeignClient;

    @Resource
    private CouponInfoService couponInfoService;

    @Override
    public IPage<ActivityInfo> queryActivityPage(Page<ActivityInfo> pageParam) {
        Page<ActivityInfo> activityInfoPage = baseMapper.selectPage(pageParam, null);
        List<ActivityInfo> records = activityInfoPage.getRecords();
        records.forEach(activityInfo -> activityInfo.setActivityTypeString(activityInfo.getActivityType().getComment()));
        return activityInfoPage;
    }

    @Override
    public Map<String, Object> queryActivityRules(Long activityId) {
        Map<String, Object> result = new HashMap<>();
        LambdaQueryWrapper<ActivityRule> activityRuleWrapper = new LambdaQueryWrapper<>();
        activityRuleWrapper.eq(ActivityRule::getActivityId, activityId);
        List<ActivityRule> activityRules = activityRuleMapper.selectList(activityRuleWrapper);
        result.put("activityRuleList", activityRules);

        LambdaQueryWrapper<ActivitySku> activitySkuWrapper = new LambdaQueryWrapper<>();
        activitySkuWrapper.eq(ActivitySku::getActivityId, activityId);
        List<ActivitySku> activitySkus = activitySkuMapper.selectList(activitySkuWrapper);

        List<Long> skuIdList = activitySkus.stream().map(ActivitySku::getSkuId).collect(Collectors.toList());
        if (skuIdList.size() == 0) {
            result.put("skuInfoList", new ArrayList<>());
        } else {
            List<SkuInfo> skuInfos = productFeignClient.querySkuInfoList(skuIdList);
            result.put("skuInfoList", skuInfos);
        }
        return result;
    }

    @Override
    @Transactional
    public void saveActivityRules(ActivityRuleVo activityRuleVo) {
        Long activityId = activityRuleVo.getActivityId();
        activityRuleMapper.delete(
                new LambdaQueryWrapper<ActivityRule>().eq(ActivityRule::getActivityId, activityId)
        );

        activitySkuMapper.delete(
                new LambdaQueryWrapper<ActivitySku>().eq(ActivitySku::getActivityId, activityId)
        );

        ActivityInfo activityInfo = baseMapper.selectById(activityId);
        ActivityType activityType = activityInfo.getActivityType();
        List<ActivityRule> activityRuleList = activityRuleVo.getActivityRuleList();
        activityRuleList.stream().forEach(activityRule -> {
            activityRule.setActivityId(activityId);
            activityRule.setActivityType(activityType);
            activityRuleMapper.insert(activityRule);
        });

        List<ActivitySku> activitySkuList = activityRuleVo.getActivitySkuList();
        activitySkuList.forEach(activitySku -> {
            activitySku.setActivityId(activityId);
            activitySkuMapper.insert(activitySku);
        });
    }

    @Override
    public List<SkuInfo> findSkuInfoByKeyword(String keyword) {
        List<SkuInfo> skuInfos = productFeignClient.querySkuInfoByKeyword(keyword);
        List<Long> keywordSearchedIdList = skuInfos.stream().map(SkuInfo::getId).collect(Collectors.toList());
        if (keywordSearchedIdList.size() == 0) {
            return new ArrayList<>();
        }
        List<Long> idsInActivity = baseMapper.selectSkuIdInActivity(keywordSearchedIdList);
        List<SkuInfo> resultList = new ArrayList<>();
        for (SkuInfo skuInfo : skuInfos) {
            if (!idsInActivity.contains(skuInfo.getId())) {
                resultList.add(skuInfo);
            }
        }
        return resultList;
    }

    @Override
    public Map<Long, List<String>> getActitvity(List<Long> skuIdList) {
        Map<Long, List<String>> result = new HashMap<>();
        skuIdList.forEach(skuId -> {
            List<ActivityRule> activityRuleList = baseMapper.selectActivityRule(skuId);
            if (!CollectionUtils.isEmpty(activityRuleList)) {
                activityRuleList.forEach(rule -> {
                    rule.setRuleDesc(this.getRuleDesc(rule));
                });
                List<String> ruleString = activityRuleList.stream().map(ActivityRule::getRuleDesc).collect(Collectors.toList());
                result.put(skuId, ruleString);
            }
        });
        return result;
    }

    @Override
    public Map<String, Object> findActivityAndCoupon(Long skuId, Long userId) {

        List<ActivityRule> activityRuleBySkuId = this.findActivityRuleBySkuId(skuId);
        List<CouponInfo> couponInfos = couponInfoService.getCouponInfoList(skuId, userId);
        Map<String, Object> map = new HashMap<>();
        map.put("couponInfoList", couponInfos);
        map.put("activityRuleList", activityRuleBySkuId);
        return map;
    }

    @Override
    public List<ActivityRule> findActivityRuleBySkuId(Long skuId) {
        List<ActivityRule> activityRuleList = baseMapper.selectActivityRule(skuId);
        for (ActivityRule activityRule : activityRuleList) {
            String ruleDesc = this.getRuleDesc(activityRule);
            activityRule.setRuleDesc(ruleDesc);
        }
        return activityRuleList;
    }

    @Override
    public List<CartInfoVo> findCartActivityList(List<CartInfo> cartInfoList) {
        List<CartInfoVo> cartInfoVoList = new ArrayList<>();
        List<Long> skuIdList = cartInfoList.stream().map(CartInfo::getSkuId).collect(Collectors.toList());
        List<ActivitySku> activitySkuList = baseMapper.selectCartActivity(skuIdList);
        Map<Long, Set<Long>> activityIdToSkuIdListMap = activitySkuList.stream().collect(
                Collectors.groupingBy(
                        ActivitySku::getActivityId,
                        Collectors.mapping(ActivitySku::getSkuId, Collectors.toSet())
                )
        );
        Map<Long,List<ActivityRule>> activityIdToActivityRulesMap = new HashMap<>();
        Set<Long> activityIds = activitySkuList.stream().map(ActivitySku::getActivityId).collect(Collectors.toSet());
        if (!CollectionUtils.isEmpty(activityIds)) {
            LambdaQueryWrapper<ActivityRule> wrapper = new LambdaQueryWrapper<>();
            wrapper.in(ActivityRule::getActivityId,activityIds);
            wrapper.orderByDesc(ActivityRule::getConditionAmount,ActivityRule::getConditionNum);
            List<ActivityRule> activityRules = activityRuleMapper.selectList(wrapper);
            activityIdToActivityRulesMap = activityRules.stream().collect(
                    Collectors.groupingBy(ActivityRule::getActivityId)
            );
        }
        Set<Long> activitySkuIdSet = new HashSet<>();
        if (!CollectionUtils.isEmpty(activityIdToSkuIdListMap)) {
            Iterator<Map.Entry<Long, Set<Long>>> iterator = activityIdToSkuIdListMap.entrySet().iterator();
            while (iterator.hasNext()){
                Map.Entry<Long, Set<Long>> next = iterator.next();
                Long activityId = next.getKey();
                Set<Long> activitySkuIdList = next.getValue();
                List<CartInfo> thisCartInfos = cartInfoList.stream().filter(cartInfo -> 
                    activitySkuIdList.contains(cartInfo.getSkuId())
                ).collect(Collectors.toList());
                BigDecimal activityTotalAmount = this.computeTotalAmount(thisCartInfos);
                int activityTotalNum = this.computeCartNum(thisCartInfos);
                List<ActivityRule> currentActivityRuleList = activityIdToActivityRulesMap.get(activityId);
                ActivityType activityType = currentActivityRuleList.get(0).getActivityType();
                ActivityRule activityRule = null;
                if (activityType == ActivityType.FULL_DISCOUNT){
                    activityRule = computeFullReduction(activityTotalAmount,currentActivityRuleList);
                }else {
                    activityRule = computeFullDiscount(activityTotalNum,activityTotalAmount,currentActivityRuleList);
                }

                //CartInfoVo封装
                CartInfoVo cartInfoVo = new CartInfoVo();
                cartInfoVo.setActivityRule(activityRule);
                cartInfoVo.setCartInfoList(thisCartInfos);
                cartInfoVoList.add(cartInfoVo);

                //记录哪些购物项参与活动
                activitySkuIdSet.addAll(activitySkuIdList);
            }
        }

        skuIdList.removeAll(activitySkuIdSet);
        if(!CollectionUtils.isEmpty(skuIdList)) {
            //skuId对应购物项
            Map<Long, CartInfo> skuIdCartInfoMap = cartInfoList.stream().collect(
                    Collectors.toMap(CartInfo::getSkuId, CartInfo -> CartInfo)
            );
            for(Long skuId  : skuIdList) {
                CartInfoVo cartInfoVo = new CartInfoVo();
                cartInfoVo.setActivityRule(null);//没有活动

                List<CartInfo> cartInfos = new ArrayList<>();
                cartInfos.add(skuIdCartInfoMap.get(skuId));
                cartInfoVo.setCartInfoList(cartInfos);

                cartInfoVoList.add(cartInfoVo);
            }
        }

        return cartInfoVoList;
    }

    private ActivityRule computeFullDiscount(Integer totalNum, BigDecimal totalAmount, List<ActivityRule> activityRuleList) {
        ActivityRule optimalActivityRule = null;
        //该活动规则skuActivityRuleList数据，已经按照优惠金额从大到小排序了
        for (ActivityRule activityRule : activityRuleList) {
            //如果订单项购买个数大于等于满减件数，则优化打折
            if (totalNum.intValue() >= activityRule.getConditionNum()) {
                BigDecimal skuDiscountTotalAmount = totalAmount.multiply(activityRule.getBenefitDiscount().divide(new BigDecimal("10")));
                BigDecimal reduceAmount = totalAmount.subtract(skuDiscountTotalAmount);
                activityRule.setReduceAmount(reduceAmount);
                optimalActivityRule = activityRule;
                break;
            }
        }
        if(null == optimalActivityRule) {
            //如果没有满足条件的取最小满足条件的一项
            optimalActivityRule = activityRuleList.get(activityRuleList.size()-1);
            optimalActivityRule.setReduceAmount(new BigDecimal("0"));
            optimalActivityRule.setSelectType(1);

            StringBuffer ruleDesc = new StringBuffer()
                    .append("满")
                    .append(optimalActivityRule.getConditionNum())
                    .append("元打")
                    .append(optimalActivityRule.getBenefitDiscount())
                    .append("折，还差")
                    .append(totalNum-optimalActivityRule.getConditionNum())
                    .append("件");
            optimalActivityRule.setRuleDesc(ruleDesc.toString());
        } else {
            StringBuffer ruleDesc = new StringBuffer()
                    .append("满")
                    .append(optimalActivityRule.getConditionNum())
                    .append("元打")
                    .append(optimalActivityRule.getBenefitDiscount())
                    .append("折，已减")
                    .append(optimalActivityRule.getReduceAmount())
                    .append("元");
            optimalActivityRule.setRuleDesc(ruleDesc.toString());
            optimalActivityRule.setSelectType(2);
        }
        return optimalActivityRule;
    }

    private ActivityRule computeFullReduction(BigDecimal totalAmount, List<ActivityRule> activityRuleList) {
        ActivityRule optimalActivityRule = null;
        //该活动规则skuActivityRuleList数据，已经按照优惠金额从大到小排序了
        for (ActivityRule activityRule : activityRuleList) {
            //如果订单项金额大于等于满减金额，则优惠金额
            if (totalAmount.compareTo(activityRule.getConditionAmount()) > -1) {
                //优惠后减少金额
                activityRule.setReduceAmount(activityRule.getBenefitAmount());
                optimalActivityRule = activityRule;
                break;
            }
        }
        if(null == optimalActivityRule) {
            //如果没有满足条件的取最小满足条件的一项
            optimalActivityRule = activityRuleList.get(activityRuleList.size()-1);
            optimalActivityRule.setReduceAmount(new BigDecimal("0"));
            optimalActivityRule.setSelectType(1);

            StringBuffer ruleDesc = new StringBuffer()
                    .append("满")
                    .append(optimalActivityRule.getConditionAmount())
                    .append("元减")
                    .append(optimalActivityRule.getBenefitAmount())
                    .append("元，还差")
                    .append(totalAmount.subtract(optimalActivityRule.getConditionAmount()))
                    .append("元");
            optimalActivityRule.setRuleDesc(ruleDesc.toString());
        } else {
            StringBuffer ruleDesc = new StringBuffer()
                    .append("满")
                    .append(optimalActivityRule.getConditionAmount())
                    .append("元减")
                    .append(optimalActivityRule.getBenefitAmount())
                    .append("元，已减")
                    .append(optimalActivityRule.getReduceAmount())
                    .append("元");
            optimalActivityRule.setRuleDesc(ruleDesc.toString());
            optimalActivityRule.setSelectType(2);
        }
        return optimalActivityRule;
    }

    private BigDecimal computeTotalAmount(List<CartInfo> cartInfoList) {
        BigDecimal total = new BigDecimal("0");
        for (CartInfo cartInfo : cartInfoList) {
            //是否选中
            if(cartInfo.getIsChecked().intValue() == 1) {
                BigDecimal itemTotal = cartInfo.getCartPrice().multiply(new BigDecimal(cartInfo.getSkuNum()));
                total = total.add(itemTotal);
            }
        }
        return total;
    }
    private int computeCartNum(List<CartInfo> cartInfoList) {
        int total = 0;
        for (CartInfo cartInfo : cartInfoList) {
            //是否选中
            if(cartInfo.getIsChecked().intValue() == 1) {
                total += cartInfo.getSkuNum();
            }
        }
        return total;
    }
    @Override
    public OrderConfirmVo findCartActivityAndCoupon(List<CartInfo> cartInfoList, Long userId) {

        return null;
    }

    private String getRuleDesc(ActivityRule activityRule) {
        ActivityType activityType = activityRule.getActivityType();
        StringBuffer ruleDesc = new StringBuffer();
        if (activityType == ActivityType.FULL_REDUCTION) {
            ruleDesc
                    .append("满")
                    .append(activityRule.getConditionAmount())
                    .append("元减")
                    .append(activityRule.getBenefitAmount())
                    .append("元");
        } else {
            ruleDesc
                    .append("满")
                    .append(activityRule.getConditionNum())
                    .append("元打")
                    .append(activityRule.getBenefitDiscount())
                    .append("折");
        }
        return ruleDesc.toString();
    }
}
