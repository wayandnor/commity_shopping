package com.nor.cs.search.service.api;

import com.nor.cs.model.search.SkuEs;
import com.nor.cs.model.vo.search.SkuEsQueryVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @version 1.0
 * @author: Nor Way
 * @description:
 * @date 2023/7/8 17:34
 */
public interface SkuService {
    void addSkuInfo(Long skuId);

    void removeSkuInfo(Long skuId);

    List<SkuEs> getHotSku();

    Page<SkuEs> search(Pageable pageable, SkuEsQueryVo skuEsQueryVo);

    void incrHotScore(Long skuId);
}
