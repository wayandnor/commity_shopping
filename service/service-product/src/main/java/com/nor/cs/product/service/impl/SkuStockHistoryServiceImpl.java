package com.nor.cs.product.service.impl;

import com.nor.cs.model.product.SkuStockHistory;
import com.nor.cs.product.mapper.SkuStockHistoryMapper;
import com.nor.cs.product.service.SkuStockHistoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * sku的库存历史记录 服务实现类
 * </p>
 *
 * @author north
 * @since 2023-06-28
 */
@Service
public class SkuStockHistoryServiceImpl extends ServiceImpl<SkuStockHistoryMapper, SkuStockHistory> implements SkuStockHistoryService {

}
