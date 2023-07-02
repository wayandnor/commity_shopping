package com.nor.cs.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nor.cs.model.product.Attr;

import java.util.List;

/**
 * <p>
 * 商品属性 服务类
 * </p>
 *
 * @author north
 * @since 2023-06-28
 */
public interface AttrService extends IService<Attr> {

    List<Attr> listByGroupId(Long groupId);
}
