package com.nor.cs.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nor.cs.model.sys.Region;

import java.util.List;

/**
 * <p>
 * 地区表 服务类
 * </p>
 *
 * @author north
 * @since 2023-06-26
 */
public interface RegionService extends IService<Region> {

    List<Region> getByKeyword(String keyword);
}
