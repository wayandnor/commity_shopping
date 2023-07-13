package com.nor.cs.activity.service.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nor.cs.model.activity.ActivityInfo;

import java.util.List;

/**
 * <p>
 * 活动表 服务类
 * </p>
 *
 * @author north
 * @since 2023-07-13
 */
public interface ActivityInfoService extends IService<ActivityInfo> {

    IPage<ActivityInfo> queryActivityPage(Page<ActivityInfo> pageParam);
}
