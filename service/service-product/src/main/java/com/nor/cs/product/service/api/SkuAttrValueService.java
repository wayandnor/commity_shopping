package com.nor.cs.product.service.api;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nor.cs.model.product.SkuAttrValue;

import java.util.List;

/**
 * <p>
 * spu属性值 服务类
 * </p>
 *
 * @author north
 * @since 2023-06-28
 */
public interface SkuAttrValueService extends IService<SkuAttrValue> {

    List<SkuAttrValue> getAttrValueById(Long id);
}
