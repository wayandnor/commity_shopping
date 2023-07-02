package com.nor.cs.model.vo.product;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @version 1.0
 * @author: Nor Way
 * @description:
 * @date 2023/7/2 17:10
 */
@Data
public class SkuInfoQueryVo {
    @ApiModelProperty(value = "分类id")
    private Long categoryId;

    @ApiModelProperty(value = "商品类型：0->普通商品 1->秒杀商品")
    private String skuType;

    @ApiModelProperty(value = "spu名称")
    private String keyword;
}
