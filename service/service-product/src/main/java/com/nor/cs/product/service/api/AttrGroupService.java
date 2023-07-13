package com.nor.cs.product.service.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nor.cs.model.product.AttrGroup;
import com.nor.cs.model.vo.product.AttrGroupQueryVo;

import java.util.List;

/**
 * <p>
 * 属性分组 服务类
 * </p>
 *
 * @author north
 * @since 2023-06-28
 */
public interface AttrGroupService extends IService<AttrGroup> {

    IPage<AttrGroup> getPageByFilter(Page<AttrGroup> pageParam, AttrGroupQueryVo attrGroupQueryVo);

    List<AttrGroup> listOrderById();
}
