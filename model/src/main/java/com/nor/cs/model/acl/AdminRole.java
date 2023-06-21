package com.nor.cs.model.acl;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.nor.cs.model.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @version 1.0
 * @author: Nor Way
 * @description:
 * @date 2023/6/21 22:33
 */
@Data
@TableName("admin_role")
public class AdminRole extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "角色id")
    @TableField("role_id")
    private Long roleId;

    @ApiModelProperty(value = "用户id")
    @TableField("admin_id")
    private Long adminId;
    
    @TableField("is_deleted")
    private Integer isDeleted;
}
