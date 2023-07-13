package com.nor.cs.activity.service.impl;

import com.nor.cs.activity.mapper.ActivitySkuMapper;
import com.nor.cs.activity.service.api.ActivitySkuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nor.cs.model.activity.ActivitySku;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 活动参与商品 服务实现类
 * </p>
 *
 * @author north
 * @since 2023-07-13
 */
@Service
public class ActivitySkuServiceImpl extends ServiceImpl<ActivitySkuMapper, ActivitySku> implements ActivitySkuService {

}
