package com.nor.cs.activity.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nor.cs.activity.mapper.ActivityInfoMapper;
import com.nor.cs.activity.service.api.ActivityInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nor.cs.model.activity.ActivityInfo;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public IPage<ActivityInfo> queryActivityPage(Page<ActivityInfo> pageParam) {
        Page<ActivityInfo> activityInfoPage = baseMapper.selectPage(pageParam,null);
        List<ActivityInfo> records = activityInfoPage.getRecords();
        records.forEach(activityInfo -> activityInfo.setActivityTypeString(activityInfo.getActivityType().getComment()));
        return activityInfoPage;
    }
}
