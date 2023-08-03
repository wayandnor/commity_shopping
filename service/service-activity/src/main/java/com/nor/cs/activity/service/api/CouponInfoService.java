package com.nor.cs.activity.service.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nor.cs.model.activity.CouponInfo;
import com.nor.cs.model.order.CartInfo;
import com.nor.cs.model.vo.activity.CouponRuleVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 优惠券信息 服务类
 * </p>
 *
 * @author north
 * @since 2023-07-13
 */
public interface CouponInfoService extends IService<CouponInfo> {

    IPage<CouponInfo> queryCouponInfoByPage(Page<CouponInfo> pageParam);

    CouponInfo queryCouponInfoById(Long id);

    Map<String, Object> queryCouponRules(Long id);

    void saveRules(CouponRuleVo couponRuleVo);

    void deleteCouponById(Long id);

    void batchRemoveByIds(List<Long> ids);

    List<CouponInfo> queryCouponByKeyword(String keyword);

    List<CouponInfo> getCouponInfoList(Long skuId, Long userId);

    List<CouponInfo> findCartCouponInfo(List<CartInfo> cartInfoList, Long userId);
}
