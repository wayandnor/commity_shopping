package com.nor.cs.activity.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nor.cs.activity.mapper.ActivityInfoMapper;
import com.nor.cs.activity.mapper.ActivityRuleMapper;
import com.nor.cs.activity.mapper.ActivitySkuMapper;
import com.nor.cs.activity.service.api.ActivityInfoService;
import com.nor.cs.client.product.ProductFeignClient;
import com.nor.cs.enums.ActivityType;
import com.nor.cs.model.activity.ActivityInfo;
import com.nor.cs.model.activity.ActivityRule;
import com.nor.cs.model.activity.ActivitySku;
import com.nor.cs.model.product.SkuInfo;
import com.nor.cs.model.vo.activity.ActivityRuleVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
