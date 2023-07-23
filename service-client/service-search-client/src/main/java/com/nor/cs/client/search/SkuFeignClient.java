package com.nor.cs.client.search;

import com.nor.cs.model.search.SkuEs;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @version 1.0
 * @author: Nor Way
 * @description:
 * @date 2023/7/22 13:33
 */
@FeignClient("service-search")
@RequestMapping("api/sku-search-info")
public interface SkuFeignClient {
    @GetMapping("inner/hot-sku-list")
    List<SkuEs> queryHotSkuList();
}
