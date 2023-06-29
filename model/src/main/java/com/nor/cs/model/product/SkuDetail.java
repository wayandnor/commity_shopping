package com.nor.cs.model.product;

import com.baomidou.mybatisplus.annotation.TableField;
import com.nor.cs.model.base.BaseEntity;
import lombok.Data;

@Data
public class SkuDetail extends BaseEntity {
    
    
    @TableField("sku_id")
    private Long skuId;
    
    @TableField("detail_html")
    private String detailHtml;
}
