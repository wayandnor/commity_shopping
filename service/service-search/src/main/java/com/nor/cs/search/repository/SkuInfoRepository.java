package com.nor.cs.search.repository;

import com.nor.cs.model.search.SkuEs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @version 1.0
 * @author: Nor Way
 * @description:
 * @date 2023/7/8 17:37
 */
public interface SkuInfoRepository extends ElasticsearchRepository<SkuEs,Long> {
    Page<SkuEs> findByOrderByHotScoreDesc(Pageable pageable);

    Page<SkuEs> findByCategoryIdAndWareId(Long categoryId, Long wareId, Pageable pageable);

    Page<SkuEs> findByKeywordAndWareId(String keyword, Long wareId, Pageable pageable);
}
