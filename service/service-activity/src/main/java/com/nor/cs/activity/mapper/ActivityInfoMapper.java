package com.nor.cs.activity.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nor.cs.model.activity.ActivityInfo;
import com.nor.cs.model.activity.ActivityRule;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 活动表 Mapper 接口
 * </p>
 *
 * @author north
 * @since 2023-07-13
 */
public interface ActivityInfoMapper extends BaseMapper<ActivityInfo> {

    List<Long> selectSkuIdInActivity(@Param("skuIdList") List<Long> skuIdList);

    List<ActivityRule> selectActivityRule(@Param("skuId") Long skuId);
}
