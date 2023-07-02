package com.nor.cs.model.vo.product;

import com.nor.cs.model.product.SkuAttrValue;
import com.nor.cs.model.product.SkuImage;
import com.nor.cs.model.product.SkuInfo;
import com.nor.cs.model.product.SkuPoster;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @version 1.0
 * @author: Nor Way
 * @description:
 * @date 2023/7/2 17:56
 */
@Data
public class SkuInfoVo extends SkuInfo {
    private List<SkuPoster> skuPosterList;

    private List<SkuAttrValue> skuAttrValueList;

    private List<SkuImage> skuImagesList;
}
