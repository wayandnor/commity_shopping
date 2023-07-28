package com.nor.cs.activity.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nor.cs.model.activity.CouponInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 优惠券信息 Mapper 接口
 * </p>
 *
 * @author north
 * @since 2023-07-13
 */
public interface CouponInfoMapper extends BaseMapper<CouponInfo> {

    List<CouponInfo> selectCouponInfoList(@Param("skuId")Long skuId, @Param("categoryId") Long categoryId, @Param("userId") Long userId);
}
